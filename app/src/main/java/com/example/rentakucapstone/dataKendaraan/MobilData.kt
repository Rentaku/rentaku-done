package com.example.rentakucapstone.dataKendaraan

data class MobilData(
    val mileage: String,
    val merkMobil: MerkMobil,
    val modelMobil: ModelMobil,
    val kategoriMobil: KategoriMobil,
    val tahunMotor: TahunMotor,
    val transmisi: TipeTransmisi?
)
