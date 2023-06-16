package com.example.rentakucapstone.view.welcome

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.example.rentakucapstone.R
import com.example.rentakucapstone.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        playAnimation()
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
        binding.resetPasswordButton.setOnClickListener {
            startActivity(Intent(this, BuatPasswordActivity::class.java))
            Toast.makeText(
                this@ForgotPasswordActivity,
                "Silahkan cek email yang terdaftar ya.",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.masukWelcome.setOnClickListener {
            startActivity(Intent(this, WelcomeActivity::class.java))
        }
        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, WelcomeActivity::class.java))
        }
    }

    private fun playAnimation() {

        val title = ObjectAnimator.ofFloat(binding.titleForgotPassword, View.ALPHA, 1f).setDuration(500)
        val titleTextEmail = ObjectAnimator.ofFloat(binding.titleTextEmail, View.ALPHA, 1f).setDuration(500)
        val titleEmail = ObjectAnimator.ofFloat(binding.titleEmail, View.ALPHA, 1f).setDuration(500)
        val reset = ObjectAnimator.ofFloat(binding.resetPasswordButton, View.ALPHA, 1f).setDuration(500)
        val emailEditLayout = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(reset)
        }

        AnimatorSet().apply {
            playSequentially(title, titleTextEmail, titleEmail, emailEditLayout, together)
            start()
        }
    }
}