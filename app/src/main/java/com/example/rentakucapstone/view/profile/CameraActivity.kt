package com.example.rentakucapstone.view.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.core.content.ContextCompat
import com.example.rentakucapstone.R
import com.example.rentakucapstone.databinding.ActivityCameraBinding
import java.nio.file.Files.createFile

class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private var imageCapture: ImageCapture? = null

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityCameraBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.captureImage.setOnClickListener { takePhoto() }
//        binding.switchCamera.setOnClickListener {
//            if (cameraIdList?.size ?: 0 < 2) {
//                // Jika hanya ada satu kamera, tidak ada kamera belakang yang dapat diganti
//                Toast.makeText(this, "Tidak ada kamera belakang yang tersedia", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            cameraSelector = if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
//                CameraSelector.DEFAULT_FRONT_CAMERA
//            } else {
//                CameraSelector.DEFAULT_BACK_CAMERA
//            }
//
//            // Mulai ulang penggunaan use case camera dengan kamera yang baru dipilih
//            startCamera()
//        }
//    }
//
//    private fun takePhoto() {
//        val imageCapture = imageCapture ?: return
//
//        private fun takePhoto() {
//            val imageCapture = imageCapture ?: return
//
//            val photoFile = createFile(application)
//
//            val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
//            imageCapture.takePicture(
//                outputOptions,
//                ContextCompat.getMainExecutor(this),
//                object : ImageCapture.OnImageSavedCallback {
//                    override fun onError(exc: ImageCaptureException) {
//                        Toast.makeText(
//                            this@CameraActivity,
//                            "Gagal mengambil gambar.",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//
//                    override fun onImageSaved(output: ImageCapture.OutputFileResults) {
//                        val intent = Intent()
//                        intent.putExtra("picture", photoFile)
//                        intent.putExtra(
//                            "isBackCamera",
//                            cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA
//                        )
//                        setResult(AddStoryActivity.CAMERA_X_RESULT, intent)
//                        finish()
//                    }
//                }
//            )
//        }
//    }
}