package com.example.rentakucapstone.view.main

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.rentakucapstone.R
import com.example.rentakucapstone.databinding.ActivityMainBinding
import com.example.rentakucapstone.view.login.LoginActivity
import com.example.rentakucapstone.view.welcome.WelcomeActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handler = Handler()
        handler.postDelayed({
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000L)

        setupView()
        setupViewModel()
        /*
        setupAction()

         */
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

    private fun setupViewModel() {
//        mainViewModel = ViewModelProvider(
//            this,
//
//        )[MainViewModel::class.java]
//        Log.d("", "")
//
//        mainViewModel.getUser().observe(this, { user ->
//            if (user.isLogin){
//                binding.rentaku.text = getString(R.string.greeting, user.name)
//            } else {
//                startActivity(Intent(this, WelcomeActivity::class.java))
//                finish()
//            }
//        })
    }

    /*
    private fun setupAction() {
        binding.logoutButton.setOnClickListener {
            mainViewModel.logout()
        }
    }

     */

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.gambar, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val name = ObjectAnimator.ofFloat(binding.rentaku, View.ALPHA, 1f).setDuration(500)
        val message = ObjectAnimator.ofFloat(binding.sewaTransportasi, View.ALPHA, 1f).setDuration(500)
        /*
        val logout = ObjectAnimator.ofFloat(binding.logoutButton, View.ALPHA, 1f).setDuration(500)
        */

        AnimatorSet().apply {
            playSequentially(name, message)
            startDelay = 500
        }.start()
    }

}