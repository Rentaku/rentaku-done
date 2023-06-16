package com.example.rentakucapstone.kendaraan

import android.content.ContentValues
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.example.rentakucapstone.R
import com.example.rentakucapstone.createCustomTempFile
import com.example.rentakucapstone.databinding.ActivityTambahKendaraanBinding
import com.example.rentakucapstone.uriToFile
import com.example.rentakucapstone.view.garasi.TambahGarasiViewModel
import com.google.firebase.auth.FirebaseAuth
import java.io.File

@Suppress("DEPRECATION")
class TambahKendaraanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTambahKendaraanBinding
    private lateinit var jenisKendaraan: AutoCompleteTextView
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var viewModel: TambahKendaraanViewModel
    private var selectedImageFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTambahKendaraanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setTitle("Tambah Kendaraan")

        viewModel = ViewModelProvider(this)[TambahKendaraanViewModel::class.java]

        val auth = FirebaseAuth.getInstance()
        val currentUser  = auth.currentUser
        val email = currentUser?.email

        binding.cameraButton.setOnClickListener { startTakePhoto() }
        binding.galleryButton.setOnClickListener { startGallery() }

        val kendaraanOption = arrayOf("Motor", "Mobil")
        jenisKendaraan = findViewById<AutoCompleteTextView>(R.id.jenisKendaraan)
        adapter = ArrayAdapter(this, R.layout.list_gender, kendaraanOption)
        jenisKendaraan.setAdapter(adapter)

        binding.nextTextView.setOnClickListener {
            val selectedOption = jenisKendaraan.text.toString()

            if (email != null) {
                viewModel.getUserDocumentByEmail(email)
                    .addOnSuccessListener { querySnapshot ->
                        if (!querySnapshot.isEmpty) {
                            val documentSnapshot = querySnapshot.documents[0]
                            val userId = documentSnapshot.id

                            val jenisKendaraan = jenisKendaraan.text.toString()
                            val noPlat = binding.noPlatEditText.text.toString()

                            val dataGarasi = hashMapOf<String, Any>(
                                "jenis_kendaraan" to jenisKendaraan,
                                "no_plat" to noPlat,
                                "userId" to userId
                            )

                            if (selectedImageFile != null) {
                                viewModel.saveDataMotor(userId, dataGarasi, selectedImageFile!!)
                                    .addOnSuccessListener {
                                        Toast.makeText(this, "Detail data berhasil ditambahkan >.<", Toast.LENGTH_SHORT).show()

                                        // Kode untuk membersihkan input pengguna
                                        binding.noPlatEditText.text?.clear()

                                        if (selectedOption == "Motor") {
                                            val intent = Intent(this, TambahDetailKendaraanActivity::class.java)
                                            startActivity(intent)
                                        } else if (selectedOption == "Mobil") {
                                            val intent = Intent(this, TambahDetailKendaraanMobilActivity::class.java)
                                            startActivity(intent)
                                        }
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(this, "Akun gagal dibuat! -_-''", Toast.LENGTH_SHORT).show()
                                    }
                            } else {
                                Toast.makeText(this, "Tidak ada file yang dipilih", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Log.d(ContentValues.TAG,"Email tidak ditemukan")
                        }
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(this, "Gagal mendapatkan detail data pengguna", Toast.LENGTH_SHORT).show()
                        Log.e(ContentValues.TAG, "Error getting document", exception)
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
                val myFile = uriToFile(uri, this@TambahKendaraanActivity)
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
                this@TambahKendaraanActivity,
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
        binding.previewImageView.setImageBitmap(BitmapFactory.decodeFile(file.path))
    }
}