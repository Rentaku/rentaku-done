package com.example.rentakucapstone.dashboard.ui.favourite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rentakucapstone.R


class FavouriteAdapter(
    private val list: List<String>
) :
    RecyclerView.Adapter<FavouriteAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvFav: TextView = view.findViewById(R.id.tvFav)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {

        val itemView: View = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_fav,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FavouriteAdapter.MyViewHolder, position: Int) {
        holder.tvFav.text = list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }
}