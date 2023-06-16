package com.example.rentakucapstone.dashboard.ui.setLokasi

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.example.rentakucapstone.R
import com.example.rentakucapstone.dashboard.ui.home.DetailActivity
import com.example.rentakucapstone.databinding.ActivityDurasiSewaBinding
import com.example.rentakucapstone.view.profile.Pembayaran1Activity
import com.example.rentakucapstone.view.profile.PembayaranActivity
import com.example.rentakucapstone.view.welcome.BuatPasswordActivity
import com.example.rentakucapstone.view.welcome.WelcomeActivity

class DurasiSewaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDurasiSewaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDurasiSewaBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setTitle("Sewa Motor")

        supportActionBar?.hide()

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
    }

    private fun setupAction() {
        binding.backSewaMotor.setOnClickListener {
            startActivity(Intent(this, DetailActivity::class.java))
        }

        binding.btnPesanSekarangInDurasi.setOnClickListener {
            startActivity(Intent(this, Pembayaran1Activity::class.java))
        }
    }
}