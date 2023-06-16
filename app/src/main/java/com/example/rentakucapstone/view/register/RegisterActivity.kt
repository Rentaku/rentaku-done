package com.example.rentakucapstone.view.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.example.rentakucapstone.R
import com.example.rentakucapstone.databinding.ActivityRegisterBinding
import com.example.rentakucapstone.view.login.LoginActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var gender: AutoCompleteTextView
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var auth: FirebaseAuth
    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val genderOption = arrayOf("Laki-laki", "Perempuan")
        gender = findViewById<AutoCompleteTextView>(R.id.spinner_gender)
        adapter = ArrayAdapter(this, R.layout.list_gender, genderOption)
        gender.setAdapter(adapter)

        binding.signupButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isEmpty()) {
                binding.emailEditText.error = "Email harus diisi"
                binding.emailEditText.requestFocus()
                return@setOnClickListener
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailEditText.error = "Email tidak valid"
                binding.emailEditText.requestFocus()
                return@setOnClickListener
            } else if (password.isEmpty()) {
                binding.passwordEditText.error = "Password harus diisi"
                binding.passwordEditText.requestFocus()
                return@setOnClickListener
            } else if (password.length < 6) {
                binding.passwordEditText.error = "Password minimal 6 karakter"
                binding.passwordEditText.requestFocus()
                return@setOnClickListener
            } else {
                registerUser(email, password)
            }

        }

        setupView()
        playAnimation()

    }

    private fun registerUser(email: String, password: String) {
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        val userId = auth.currentUser!!.uid

        val name = binding.nameEditText.text.toString()
        val phoneNumber = binding.numberEditText.text.toString()
        val selectedGender = gender.text.toString()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val userMap = hashMapOf(
                    "name" to name,
                    "phone_number" to phoneNumber,
                    "gender" to selectedGender,
                    "email" to email,
                    "password" to password
                )

                if (userId != null) {
                    db.collection("users").add(userMap)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Akun berhasil dibuat!!!", Toast.LENGTH_SHORT).show()
                            binding.nameEditText.text?.clear()
                            binding.numberEditText.text?.clear()
                            binding.spinnerGender.text?.clear()
                            binding.emailEditText.text?.clear()
                            binding.passwordEditText.text?.clear()

                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Akun gagal dibuat! -_-''", Toast.LENGTH_SHORT).show()
                        }
                }
            }
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

    private fun playAnimation() {

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)
        val nameTextView = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(500)
        val nameEditTextLayout = ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val genderTextView = ObjectAnimator.ofFloat(binding.genderTextView, View.ALPHA, 1f).setDuration(500)
        val genderEditTextLayout = ObjectAnimator.ofFloat(binding.spinnerEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val numberTextView = ObjectAnimator.ofFloat(binding.numberTextView, View.ALPHA, 1f).setDuration(500)
        val numberEditTextLayout = ObjectAnimator.ofFloat(binding.numberEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val emailTextView = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val passwordTextView = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val signup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(500)


        AnimatorSet().apply {
            playSequentially(
                title,
                nameTextView,
                nameEditTextLayout,
                genderTextView,
                genderEditTextLayout,
                numberTextView,
                numberEditTextLayout,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                signup
            )
            startDelay = 500
        }.start()
    }
}