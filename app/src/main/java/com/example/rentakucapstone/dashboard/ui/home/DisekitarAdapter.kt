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

class DisekitarAdapter(
    private val context: Context, private val list: List<VehiclesModel>
) :
    RecyclerView.Adapter<SekitarViewHolder>() {



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SekitarViewHolder {

        return SekitarViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rekomendasi_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SekitarViewHolder, position: Int) {
        val vehicle = list[position]
        holder.platNo.text = vehicle.no_plat
        holder.jenisKendaraan.text = vehicle.jenis_kendaraan

        Glide.with(context)
            .load(vehicle.imageUrl)
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class SekitarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val platNo: TextView = view.findViewById(R.id.tvRekomendasi)
    val jenisKendaraan: TextView = view.findViewById(R.id.tvRekomendasiCC)
    val image: ImageView = view.findViewById(R.id.image)
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
//class DisekitarAdapter(
//    private val list: List<String>
//) :
//    RecyclerView.Adapter<DisekitarAdapter.MyViewHolder>() {
//
//    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val tvDisekitar: TextView = view.findViewById(R.id.tvDisekitar)
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
//                R.layout.item_disekitar,
//                parent,
//                false
//            )
//        return MyViewHolder(itemView)
//    }
//
//    override fun onBindViewHolder(holder: DisekitarAdapter.MyViewHolder, position: Int) {
//        holder.tvDisekitar.text = list[position]
//    }
//
//    override fun getItemCount(): Int {
//        return list.size
//    }
//}