package com.example.rentakucapstone.kendaraan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rentakucapstone.R
import com.example.rentakucapstone.dashboard.ui.setLokasi.DurasiSewaActivity
import com.example.rentakucapstone.databinding.ActivityTambahDetailKendaraanBinding

class DetailKendaraanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTambahDetailKendaraanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_kendaraan)

        binding = ActivityTambahDetailKendaraanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this, DurasiSewaActivity::class.java)
        startActivity(intent)
    }
}