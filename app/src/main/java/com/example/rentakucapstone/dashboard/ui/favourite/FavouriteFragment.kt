package com.example.rentakucapstone.dashboard.ui.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rentakucapstone.dashboard.ui.pesanan.PesananAdapter
import com.example.rentakucapstone.dashboard.ui.pesanan.PesananViewModel
import com.example.rentakucapstone.databinding.FragmentFavouriteBinding
class FavouriteFragment : Fragment() {

    private var _binding: FragmentFavouriteBinding? = null
    private lateinit var favouriteAdapter: FavouriteAdapter
    private val favouriteNames =
        listOf("Senayan Park", "Senayan Park", "Senayan Park", "Senayan Park", "Senayan Park", "Senayan Park", "Senayan Park", "Senayan Park")

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //val notificationsViewModel = ViewModelProvider(this).get(FavouriteViewModel::class.java)

        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favouriteAdapter = FavouriteAdapter(favouriteNames)

        var recyclerView: RecyclerView
        binding.rvFav.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFav.setHasFixedSize(true)
        binding.rvFav.adapter = favouriteAdapter

        val verticalLayoutFav = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.rvFav.layoutManager = verticalLayoutFav
        binding.rvFav.layoutManager

        val favouriteViewModel = ViewModelProvider(this).get(FavouriteViewModel::class.java)

        //val textView: TextView = binding.textNotifications
        favouriteViewModel.text.observe(viewLifecycleOwner) {
            //textView.text = it
        }
    }

    /*
    override fun onBindViewHolder(holder: FavouriteAdapter.MyViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)

        val ivBookmark = holder.binding.ivBookmark
        if (news.isBookmarked) {
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.ic_bookmarked_white))
        } else {
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.ic_bookmark_white))
        }
        ivBookmark.setOnClickListener {
            onBookmarkClick(news)
        }
    }

     */

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}