package com.example.rentakucapstone.dashboard.ui.home

data class VehiclesModel(
    val imageUrl: String,
    val no_plat: String,
    val jenis_kendaraan: String
) {
    constructor() : this(
        "",
        "",
        ""
    )
}
