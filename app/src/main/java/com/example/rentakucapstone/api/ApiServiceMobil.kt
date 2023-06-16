package com.example.rentakucapstone.api

import com.example.rentakucapstone.BuildConfig
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiServiceMobil {

    @Headers("Content-Type: application/json")
    @POST("/")
    fun mobil(
        @Body body: JsonObject
    ): Call<CarPredictionResponse>
}

data class CarPredictionResponse (
    val prediction: List<List<Double>>
)

class CarApiConfig {
    companion object {
        fun getApiService(): ApiServiceMobil {
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
                .baseUrl("https://prcpredictcar-aeo76gp23q-et.a.run.app")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiServiceMobil::class.java)
        }
    }
}