package com.example.rentakucapstone.view.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.example.rentakucapstone.R
import com.example.rentakucapstone.dashboard.DashboardActivity
import com.example.rentakucapstone.dashboard.ui.pesanan.PesananFragment
import com.example.rentakucapstone.databinding.ActivityKonfirmasiPesananBinding
import com.example.rentakucapstone.databinding.ActivityPembayaran2Binding

class KonfirmasiPesananActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKonfirmasiPesananBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_konfirmasi_pesanan)

        binding = ActivityKonfirmasiPesananBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

//        val navController = findNavController(R.id.navigation_home)

        binding.btnKonfirmasiPesanan.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.putExtra("fragmentId", R.id.navigation_home)
            startActivity(intent)
        }
    }
}