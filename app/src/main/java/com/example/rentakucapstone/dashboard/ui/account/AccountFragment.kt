package com.example.rentakucapstone.dashboard.ui.account

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.rentakucapstone.R
import com.example.rentakucapstone.databinding.FragmentAccountBinding
import com.example.rentakucapstone.kendaraan.TambahKendaraanActivity
import com.example.rentakucapstone.view.garasi.TambahGarasiActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

@Suppress("DEPRECATION")
class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private var name: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            name = it.getString("name")
        }

        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        val navController = requireActivity().findNavController(R.id.nav_host_fragment_activity_dashboard)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    navController.navigate(R.id.navigation_home)
                    true
                }
                R.id.navigation_favourite -> {
                    navController.navigate(R.id.navigation_favourite)
                    true
                }
                R.id.navigation_pesanan -> {
                    navController.navigate(R.id.navigation_pesanan)
                    true
                }
                R.id.navigation_account -> {
                    // Jika sudah berada di halaman Account, tidak perlu navigasi ulang
                    requireActivity().supportFragmentManager.popBackStack()
                    true
                }
                else -> false
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        val greeting = getString(R.string.hi)
        val formattedGreeting = String.format(greeting, name)
        val view = binding?.root
        val nameTextView: TextView = view!!.findViewById(R.id.greeting)
        nameTextView.text = formattedGreeting

        binding.bukaGarasiBtn.setOnClickListener {
            val intent = Intent(requireContext(), TambahGarasiActivity::class.java)
            startActivity(intent)
        }

        binding.tambahKendaraanBtn.setOnClickListener {
            val intent = Intent(requireContext(), TambahKendaraanActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


