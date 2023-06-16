package com.example.rentakucapstone.view.profile

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import com.example.rentakucapstone.R
import com.example.rentakucapstone.dashboard.DashboardActivity
import com.example.rentakucapstone.databinding.ActivityDataSavedBinding

class DataSavedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDataSavedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataSavedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
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

    private fun setupAction() {
        binding.lanjutButton.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }
    }
}