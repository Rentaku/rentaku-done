package com.example.rentakucapstone.view.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rentakucapstone.R
import com.example.rentakucapstone.databinding.ActivityDurasiSewaBinding
import com.example.rentakucapstone.databinding.ActivityPembayaran1Binding

class Pembayaran1Activity : AppCompatActivity() {

    private lateinit var binding: ActivityPembayaran1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPembayaran1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.bayarSekarang.setOnClickListener {
            startActivity(Intent(this, Pembayaran2Activity::class.java))
        }
    }
}