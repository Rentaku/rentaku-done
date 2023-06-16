package com.example.rentakucapstone.api

import com.example.rentakucapstone.BuildConfig
import com.example.rentakucapstone.dataKendaraan.BesarCcMotor
import com.example.rentakucapstone.dataKendaraan.MerkMotor
import com.example.rentakucapstone.dataKendaraan.TahunMotor
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("/")
    fun motor(
        @Body body: JsonObject
    ): Call<PredictionResponse>
}

data class PredictionResponse (
    val prediction: List<List<Double>>
        )

class ApiConfig {
    companion object {
        fun getApiService(): ApiService {
            val loggingInterceptor =
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                } else {
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
                }
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://prcpredictmoto-aeo76gp23q-et.a.run.app")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}


