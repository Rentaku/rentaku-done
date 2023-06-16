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
import com.example.rentakucapstone.databinding.ActivityBuatPasswordBinding

class BuatPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBuatPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuatPasswordBinding.inflate(layoutInflater)
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
        binding.btnResetPassword.setOnClickListener {
            /*
            startActivity(Intent(this, WelcomeActivity::class.java))

             */
            Toast.makeText(
                this@BuatPasswordActivity,
                "Password kamu berhasil diganti",
                Toast.LENGTH_SHORT
            ).show()
        }
        binding.masukWelcome.setOnClickListener {
            startActivity(Intent(this, WelcomeActivity::class.java))
        }
        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
    }

    private fun playAnimation() {

        val titleBuatPassword = ObjectAnimator.ofFloat(binding.titleBuatPassword, View.ALPHA, 1f).setDuration(500)
        val titleTextBuatPassword = ObjectAnimator.ofFloat(binding.titleTextBuatPassword, View.ALPHA, 1f).setDuration(500)
        val changeTextPassword = ObjectAnimator.ofFloat(binding.changePasswordEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val confirmPassword = ObjectAnimator.ofFloat(binding.confirmPasswordEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val btnResetPassword = ObjectAnimator.ofFloat(binding.btnResetPassword, View.ALPHA, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(btnResetPassword)
        }

        AnimatorSet().apply {
            playSequentially(titleBuatPassword, titleTextBuatPassword, changeTextPassword, confirmPassword, together)
            start()
        }
    }
}