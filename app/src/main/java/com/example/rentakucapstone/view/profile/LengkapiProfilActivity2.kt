package com.example.rentakucapstone.view.profile

import android.Manifest
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.rentakucapstone.R
import com.example.rentakucapstone.createCustomTempFile
import com.example.rentakucapstone.dashboard.DashboardActivity
import com.example.rentakucapstone.databinding.ActivityLengkapiProfil2Binding
import com.example.rentakucapstone.uriToFile
import com.example.rentakucapstone.view.register.RegisterActivity
import com.example.rentakucapstone.view.welcome.ForgotPasswordActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.util.*

@Suppress("DEPRECATION")
class LengkapiProfilActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityLengkapiProfil2Binding
    private var currentButtonIndex: Int = -1
    private var selectedImageFile: File? = null


    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLengkapiProfil2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding.cameraButton.setOnClickListener { startTakePhoto(0) }
        binding.galleryButton.setOnClickListener { startGallery(0) }
        binding.cameraButton1.setOnClickListener { startTakePhoto(1) }
        binding.galleryButton1.setOnClickListener { startGallery(1) }
        binding.cameraButton2.setOnClickListener { startTakePhoto(2) }
        binding.galleryButton2.setOnClickListener { startGallery(2) }
        binding.cameraButton3.setOnClickListener { startTakePhoto(3) }
        binding.galleryButton3.setOnClickListener { startGallery(3) }
        binding.cameraButton4.setOnClickListener { startTakePhoto(4) }
        binding.galleryButton4.setOnClickListener { startGallery(4) }
        binding.simpan.setOnClickListener {
            val file = selectedImageFile
            val buttonIndex = currentButtonIndex
            if (file != null) {
                uploadImage(file, buttonIndex)
            } else {
                // Tampilkan pesan kesalahan jika file tidak ada
                Toast.makeText(this, "Tidak ada file yang dipilih", Toast.LENGTH_SHORT).show()
            } }

        setupView()

    }

    private fun uploadImage(file: File, buttonIndex: Int) {
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val email = currentUser?.email

        if (email != null) {
            val storageRef = FirebaseStorage.getInstance().reference
            val imageUrlList = mutableListOf<String>()
            for (buttonIndex in 0..4) {
                val folderName = when (buttonIndex) {
                    0 -> "ktp_img"
                    1 -> "selfie_ktp_img"
                    2 -> "sima_img"
                    3 -> "simc_img"
                    4 -> "passport_img"
                    else -> "default_folder"
                }

                val imageRef = storageRef.child("${folderName}/${ UUID.randomUUID()}")
                val uploadTask = imageRef.putFile(Uri.fromFile(file))

                uploadTask.addOnSuccessListener { taskSnapshot ->
                    imageRef.downloadUrl.addOnSuccessListener { uri ->
                        val imageUrl = uri.toString()
                        imageUrlList.add(imageUrl)

                        val userRef = FirebaseFirestore.getInstance().collection("users")
                            .whereEqualTo("email", email)
                            .limit(1)

                        userRef.get().addOnSuccessListener { doc ->
                            if (!doc.isEmpty) {
                                val documentSnapshot = doc.documents[0]
                                val userId = documentSnapshot.id

                                // Cek apakah semua URL gambar telah dikumpulkan
                                if (imageUrlList.size == 5) {
                                    // Proses penyimpanan ke Firestore
                                    val userMap = hashMapOf<String, Any>()

                                    for (index in 0..4) {
                                        val imageUrl = imageUrlList.getOrNull(index)
                                        if (imageUrl != null) {
                                            userMap["imageUrl$index"] = imageUrl
                                        }
                                    }

                                    val docRef = FirebaseFirestore.getInstance().collection("users")
                                        .document(userId)
                                    docRef.set(userMap, SetOptions.merge())
                                        .addOnSuccessListener {
                                            Toast.makeText(
                                                this,
                                                "Gambar berhasil diunggah",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            val intent = Intent(this, DashboardActivity::class.java)
                                            startActivity(intent)
                                        }
                                        .addOnFailureListener { exception ->
                                            Toast.makeText(
                                                this,
                                                "Gagal mengunggah gambar",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            Log.e(TAG, "Error uploading image", exception)
                                        }
                                }
                            } else {
                                Log.d(TAG, "Email pengguna tidak ditemukan")
                            }
                        }
                    }
                }

                    .addOnFailureListener { exception ->
                        Toast.makeText(this, "Gagal mengunggah gambar", Toast.LENGTH_SHORT).show()
                        Log.e(TAG, "Error uploading image", exception)
                    }
            }


        } else {
            Toast.makeText(this, "Email pengguna tidak tersedia", Toast.LENGTH_SHORT).show()
        }

    }

    private fun startTakePhoto(buttonIndex: Int) {
        currentButtonIndex = buttonIndex
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@LengkapiProfilActivity2,
                "com.example.rentakucapstone",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun startGallery(buttonIndex: Int) {
        currentButtonIndex = buttonIndex
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
//        Toast.makeText(this, "Fitur ini belum tersedia", Toast.LENGTH_SHORT).show()
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@LengkapiProfilActivity2)
                showPreview(myFile)
                selectedImageFile = myFile

                val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                mediaScanIntent.data = uri
                sendBroadcast(mediaScanIntent)
            }
        }
    }

    private lateinit var currentPhotoPath: String
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)

            myFile.let { file ->

                showPreview(file)
                selectedImageFile = file
            }

        }
    }

    private fun showPreview(file: File) {
        if (currentButtonIndex == 0) {
            // Tampilan preview untuk tombol pertama
            binding.previewImageView.setImageBitmap(BitmapFactory.decodeFile(file.path))
        } else if (currentButtonIndex == 1) {
            // Tampilan preview untuk tombol kedua
            binding.previewImageView1.setImageBitmap(BitmapFactory.decodeFile(file.path))
        } else if (currentButtonIndex == 2) {
            // Tampilan preview untuk tombol ketiga
            binding.previewImageView2.setImageBitmap(BitmapFactory.decodeFile(file.path))
        } else if (currentButtonIndex == 3) {
            // Tampilan preview untuk tombol keempat
            binding.previewImageView3.setImageBitmap(BitmapFactory.decodeFile(file.path))
        } else if (currentButtonIndex == 4) {
            // Tampilan preview untuk tombol kelima
            binding.previewImageView4.setImageBitmap(BitmapFactory.decodeFile(file.path))
        }
        selectedImageFile = file
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }


}