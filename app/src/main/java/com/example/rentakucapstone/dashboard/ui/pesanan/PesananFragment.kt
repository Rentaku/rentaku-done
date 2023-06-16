package com.example.rentakucapstone.dashboard.ui.pesanan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rentakucapstone.dashboard.ui.home.DisekitarAdapter
import com.example.rentakucapstone.dashboard.ui.home.HomeViewModel
import com.example.rentakucapstone.dashboard.ui.home.RekomendasiAdapter
import com.example.rentakucapstone.databinding.FragmentPesananBinding

class PesananFragment : Fragment() {

    private var _binding: FragmentPesananBinding? = null
    private lateinit var pesananAdapter: PesananAdapter
    private val pesananNames = listOf("Kawasaki ZX250R", "Kawasaki ZX250R", "Kawasaki ZX250R")

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPesananBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pesananAdapter = PesananAdapter(pesananNames)

        var recyclerView: RecyclerView
        binding.rvPesanan.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvPesanan.setHasFixedSize(true)
        binding.rvPesanan.adapter = pesananAdapter

        val verticalLayoutPesanan = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.rvPesanan.layoutManager = verticalLayoutPesanan
        binding.rvPesanan.layoutManager

//        val pesananViewModel = ViewModelProvider(this).get(PesananViewModel::class.java)
//
//        //val textView: TextView = binding.textDashboard
//        pesananViewModel.text.observe(viewLifecycleOwner) {
//            //textView.text = it
    }
}