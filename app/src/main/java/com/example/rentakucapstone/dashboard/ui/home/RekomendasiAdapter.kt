package com.example.rentakucapstone.dashboard.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rentakucapstone.R

class RekomendasiAdapter(
    private val context: Context, private val list: List<VehiclesModel>
) :
    RecyclerView.Adapter<MyViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    interface OnItemClickCallback {
        fun onItemClicked(position: Int)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rekomendasi_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val vehicle = list[position]
        holder.platNo.text = vehicle.no_plat
        holder.jenisKendaraan.text = vehicle.jenis_kendaraan
        holder.itemView.setOnClickListener {
            onItemClickCallback?.onItemClicked(position)
        }

        Glide.with(context)
            .load(vehicle.imageUrl)
            .into(holder.image)
    }


    override fun getItemCount(): Int {
        return list.size
    }

    fun setOnItemClickedCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}

class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val platNo: TextView = view.findViewById(R.id.tvRekomendasi)
    val jenisKendaraan: TextView = view.findViewById(R.id.tvRekomendasiCC)
    val image: ImageView = view.findViewById(R.id.image)
//    val tvRekomendasi: TextView = itemView.findViewById(R.id.tvRekomendasi)
}

//package com.example.rentakucapstone.dashboard.ui.home
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.example.rentakucapstone.R
//
//class RekomendasiAdapter(
//    private val list: List<String>
//) :
//    RecyclerView.Adapter<RekomendasiAdapter.MyViewHolder>() {
//
//
//    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val tvRekomendasi: TextView = view.findViewById(R.id.tvRekomendasi)
//    }
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): MyViewHolder {
//
//        val itemView: View = LayoutInflater
//            .from(parent.context)
//            .inflate(
//                R.layout.rekomendasi_item,
//                parent,
//                false
//            )
//        return MyViewHolder(itemView)
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        holder.tvRekomendasi.text = list[position]
//    }
//
//
//    override fun getItemCount(): Int {
//        return list.size
//    }
//}