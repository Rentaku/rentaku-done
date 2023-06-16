package com.example.rentakucapstone.view.profile

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.rentakucapstone.R
import com.example.rentakucapstone.databinding.ActivityLengkapiProfilBinding
import com.example.rentakucapstone.view.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class LengkapiProfilActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLengkapiProfilBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLengkapiProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name") ?: ""
        val welcomeMessage = getString(R.string.welcome, name)
        binding.welcome.text = welcomeMessage

        val auth = FirebaseAuth.getInstance()
        val currentUser  = auth.currentUser
        val email = currentUser?.email
//        val docRef = FirebaseFirestore.getInstance().collection("users").document(email.toString())

        binding.nextTextView.setOnClickListener {

            if (email != null) {
                val query = FirebaseFirestore.getInstance().collection("users")
                    .whereEqualTo("email", email)
                    .limit(1)

                query.get()
                    .addOnSuccessListener { doc ->
                        if (!doc.isEmpty) {
                            val documentSnapshot = doc.documents[0]
                            val userId = documentSnapshot.id

                            val alamat = binding.alamatEditText.text.toString()
                            val tgLahir = binding.birthDate
                            val day = tgLahir.dayOfMonth
                            val month = tgLahir.month
                            val year = tgLahir.year

                            val calendar = Calendar.getInstance()
                            calendar.set(year, month, day)

                            val dateFormat = SimpleDateFormat("dd, MMMM yyyy", Locale.getDefault())

                            val formattedDate = dateFormat.format(calendar.time)
                            val noTelp = binding.numberEditText.text.toString()
                            val noKtp = binding.noKtpEditText.text.toString()
                            val noSim = binding.noSimEditText.text.toString()
                            val noPaspor = binding.noPasporEditText.text.toString()

                            val userMap = hashMapOf(
                                "alamat" to alamat,
                                "nomor_telepon_darurat" to noTelp,
                                "tgl_lahir" to formattedDate,
                                "no_ktp" to noKtp,
                                "no_sim" to noSim,
                                "no_paspor" to noPaspor
                            )

                            val docRef = FirebaseFirestore.getInstance().collection("users").document(userId)

                            docRef.set(userMap, SetOptions.merge())
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Detail data berhasil ditambahkan >.<", Toast.LENGTH_SHORT).show()
                                    binding.alamatEditText.text?.clear()
                                    binding.birthDate
                                    binding.numberEditText.text?.clear()
                                    binding.noKtpEditText.text?.clear()
                                    binding.noSimEditText.text?.clear()
                                    binding.noPasporEditText.text?.clear()

                                    val intent = Intent(this, LengkapiProfilActivity2::class.java)
                                    startActivity(intent)
                                }
                                .addOnFailureListener {
                                    Toast.makeText(this, "Akun gagal dibuat! -_-''", Toast.LENGTH_SHORT).show()
                                }
                        } else {
                            Log.d(TAG,"Email gaada yang cocok")
                        }
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(this, "Gagal mendapatkan detail data pengguna", Toast.LENGTH_SHORT).show()
                        Log.e(TAG, "Error getting document", exception)
                    }
            }

        }

        }
    }

//    fun onNextClick(view: View) {
//
//    }
