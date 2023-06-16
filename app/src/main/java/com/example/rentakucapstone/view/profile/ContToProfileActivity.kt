package com.example.rentakucapstone.view.profile

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
import com.example.rentakucapstone.databinding.ActivityContToProfileBinding

class ContToProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContToProfileBinding
    private lateinit var name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContToProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        playAnimation()

        name = intent.getStringExtra("name") ?: ""

        binding.nextTextView.setOnClickListener {
            val intent = Intent(this, LengkapiProfilActivity::class.java)
            intent.putExtra("name", name)
            startActivity(intent)
            Toast.makeText(this, name, Toast.LENGTH_SHORT).show()
            // Selesaikan aktivitas penghubung
            finish()
        }
    }
    private fun playAnimation() {

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)
        val sub_title = ObjectAnimator.ofFloat(binding.descSubtitle, View.ALPHA, 1f).setDuration(500)
        val next = ObjectAnimator.ofFloat(binding.nextTextView, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(
                title,
                sub_title,
                next
            )
            startDelay = 500
        }.start()

        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
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