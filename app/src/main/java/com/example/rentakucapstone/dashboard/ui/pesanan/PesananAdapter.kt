package com.example.rentakucapstone.dashboard.ui.pesanan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rentakucapstone.R

class PesananAdapter(
    private val list: List<String>
) :
    RecyclerView.Adapter<PesananAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvPesanan: TextView = view.findViewById(R.id.tvPesanan)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {

        val itemView: View = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_pesanan,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PesananAdapter.MyViewHolder, position: Int) {
        holder.tvPesanan.text = list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }
}