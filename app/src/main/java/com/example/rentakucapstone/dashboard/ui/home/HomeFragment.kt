package com.example.rentakucapstone.dashboard.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rentakucapstone.databinding.FragmentHomeBinding
import com.example.rentakucapstone.kendaraan.DetailKendaraanActivity
import com.google.firebase.firestore.FirebaseFirestore
import java.text.FieldPosition


@Suppress("DEPRECATION")
class HomeFragment : Fragment() {

        private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var rekomendasiAdapter : RekomendasiAdapter
    private lateinit var disekitarAdapter : DisekitarAdapter
    private var list: MutableList<VehiclesModel> = mutableListOf()
    private val rekomendasiNames = listOf("ZX 250R", "ZX 250R", "ZX 250R", "ZX 250R")
    private val disekitarNames = listOf("ZX 250R", "ZX 250R", "ZX 250R", "ZX 250R")


    // This property is only valid between onCreateView and
    // onDestroyView.


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
//        val greeting = getString(R.string.hi)
//        val formattedGreeting = String.format(greeting, name)
        val view = binding?.root
//        val nameTextView: TextView = view!!.findViewById(R.id.greeting)
//        nameTextView.text = formattedGreeting

//        binding.bukaGarasiBtn.setOnClickListener {
//            val intent = Intent(requireContext(), TambahGarasiActivity::class.java)
//            startActivity(intent)
//        }
//
//        binding.tambahKendaraanBtn.setOnClickListener {
//            val intent = Intent(requireContext(), TambahKendaraanActivity::class.java)
//            startActivity(intent)
//        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rekomendasiAdapter = RekomendasiAdapter(requireContext(), list)
        disekitarAdapter = DisekitarAdapter(requireContext(), list)

        binding.rvRekomendasi.apply {
            layoutManager = LinearLayoutManager(requireActivity(),
                LinearLayoutManager.HORIZONTAL,
                false)
        }
        fetchData()




        val horizontalLayoutDisekitar = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.rvDisekitarmu.layoutManager = horizontalLayoutDisekitar
        binding.rvDisekitarmu.layoutManager

        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    Toast.makeText(requireContext(), searchView.text, Toast.LENGTH_SHORT).show()
                    false
                }
        }

        //val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            //textView.text = it
        }


    }

//    private fun setUserListData(items: List<VehiclesModel>) {
//
//        val adapter = RekomendasiAdapter(requireContext(), items)
//        binding.rvRekomendasi.adapter = adapter
//
//
//        adapter.setOnItemClickedCallback(object : RekomendasiAdapter.OnItemClickCallback {
//            override fun onItemClicked(item: VehiclesModel) {
//                val intentUserDetail = Intent(requireContext(), DetailKendaraanActivity::class.java)
////                intentUserDetail.putExtra(DETAIL_USER, item.login)
//                startActivity(intentUserDetail)
//
////                showSelectedUser(item)
//            }
//        })}

    private fun fetchData() {
        FirebaseFirestore.getInstance().collection("vehicles")
            .get()
            .addOnSuccessListener { doc ->
                val vehicleList = doc.toObjects(VehiclesModel::class.java)

                rekomendasiAdapter = RekomendasiAdapter(requireContext(), vehicleList)
                disekitarAdapter = DisekitarAdapter(requireContext(), vehicleList)

                rekomendasiAdapter.setOnItemClickedCallback(object : RekomendasiAdapter.OnItemClickCallback {
                    override fun onItemClicked(position: Int) {
                        val intent = Intent(requireActivity(), DetailKendaraanActivity::class.java)
                        startActivity(intent)
                    }
                })

                binding.rvRekomendasi.adapter = rekomendasiAdapter
                binding.rvDisekitarmu.adapter = disekitarAdapter
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Tidak ada data", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

