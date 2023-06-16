package com.example.rentakucapstone.view.garasi

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.example.rentakucapstone.createCustomTempFile
import com.example.rentakucapstone.dashboard.DashboardActivity
import com.example.rentakucapstone.dashboard.ui.home.DetailActivity
import com.example.rentakucapstone.databinding.ActivityTambahGarasiBinding
import com.example.rentakucapstone.kendaraan.KendaraanRenterActivity
import com.example.rentakucapstone.uriToFile
import com.example.rentakucapstone.view.profile.LengkapiProfilActivity
import com.example.rentakucapstone.view.profile.ListKendaraanRenterActivity
import com.google.firebase.auth.FirebaseAuth
import java.io.File

@Suppress("DEPRECATION")
class TambahGarasiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTambahGarasiBinding
    private lateinit var viewModel: TambahGarasiViewModel
    private var selectedImageFile: File? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahGarasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[TambahGarasiViewModel::class.java]

        val auth = FirebaseAuth.getInstance()
        val currentUser  = auth.currentUser
        val email = currentUser?.email

        binding.cameraButton.setOnClickListener { startTakePhoto() }
        binding.galleryButton.setOnClickListener { startGallery() }

        binding.nextTextView.setOnClickListener {


            if (email != null) {
                viewModel.getUserDocumentByEmail(email)
                    .addOnSuccessListener { querySnapshot ->
                        if (!querySnapshot.isEmpty) {
                            val documentSnapshot = querySnapshot.documents[0]
                            val userId = documentSnapshot.id

                            val namaGarasi = binding.nameGarasiEditText.text.toString()
                            val namaPemilik = binding.nameEditText.text.toString()
                            val deskripsi = binding.deskripsiEditText.text.toString()
                            val telepon = binding.teleponEditText.text.toString()
                            val alamat = binding.alamatEditText.text.toString()

                            val dataGarasi = hashMapOf<String, Any>(
                                "nama_garasi" to namaGarasi,
                                "nama_pemilik" to namaPemilik,
                                "deskripsi" to deskripsi,
                                "telepon" to telepon,
                                "alamat" to alamat,
                                "userId" to userId
                            )

                            if (selectedImageFile != null) {
                                viewModel.saveDataGarasi(userId, dataGarasi, selectedImageFile!!)
                                    .addOnSuccessListener {
                                        Toast.makeText(this, "Detail data berhasil ditambahkan >.<", Toast.LENGTH_SHORT).show()

                                        // Kode untuk membersihkan input pengguna
                                        binding.nameGarasiEditText.text?.clear()
                                        binding.nameEditText.text?.clear()
                                        binding.deskripsiEditText.text?.clear()
                                        binding.teleponEditText.text?.clear()
                                        binding.alamatEditText.text?.clear()

                                        val intent = Intent(this, KendaraanRenterActivity::class.java)
                                        Log.d(TAG, "Pemanggilan startActivity")
                                        startActivity(intent)
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(this, "Akun gagal dibuat! -_-''", Toast.LENGTH_SHORT).show()
                                    }
                            } else {
                                Toast.makeText(this, "Tidak ada file yang dipilih", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Log.d(TAG,"Email tidak ditemukan")
                        }
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(this, "Gagal mendapatkan detail data pengguna", Toast.LENGTH_SHORT).show()
                        Log.e(TAG, "Error getting document", exception)
                    }
            }
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@TambahGarasiActivity)
                showPreview(myFile)
                selectedImageFile = myFile

                val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                mediaScanIntent.data = uri
                sendBroadcast(mediaScanIntent)

            }
        }
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@TambahGarasiActivity,
                "com.example.rentakucapstone",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
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
        binding.profileImage.setImageBitmap(BitmapFactory.decodeFile(file.path))
    }

}