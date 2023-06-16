package com.example.rentakucapstone.pricePrediction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rentakucapstone.api.ApiConfig
import com.example.rentakucapstone.api.CarApiConfig
import com.example.rentakucapstone.api.CarPredictionResponse
import com.example.rentakucapstone.api.PredictionResponse
import com.example.rentakucapstone.dataKendaraan.MobilData
import com.example.rentakucapstone.dataKendaraan.MotorData
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PredictionRepository {
    private val apiService = ApiConfig.getApiService()
    private val carApiService = CarApiConfig.getApiService()

    fun predictMotorPrice(motorData: MotorData): LiveData<com.example.rentakucapstone.Result<List<List<Double>>>> {
        val resultLiveData = MutableLiveData<com.example.rentakucapstone.Result<List<List<Double>>>>()

        val jsonBody = JsonObject().apply {
            addProperty("feature1", motorData.mileage)
            addProperty("feature2", motorData.merkMotor.value)
            addProperty("feature3", motorData.modelMotor.value)
            addProperty("feature4", motorData.tahunMotor.value)
            addProperty("feature5", motorData.besarCcMotor.value)
        }
        val call = apiService.motor(
            jsonBody
        )

        call.enqueue(object : Callback<PredictionResponse> {
            override fun onResponse(
                call: Call<PredictionResponse>,
                response: Response<PredictionResponse>
            ) {
                if (response.isSuccessful) {
                    val prediction = response.body()?.prediction
                    resultLiveData.postValue(com.example.rentakucapstone.Result.Success(prediction))
                } else {
                    val error = response.errorBody()?.toString()
                    resultLiveData.postValue(com.example.rentakucapstone.Result.Failure(Throwable(error ?: "Unknown error")))
                }
            }

            override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
                resultLiveData.postValue(com.example.rentakucapstone.Result.Failure(Throwable(t.message ?: "Unknown error")))
            }
        })

        return resultLiveData
    }

    fun predictCarPrice(mobilData: MobilData): LiveData<com.example.rentakucapstone.Result<List<List<Double>>>> {
        val carResultLiveData = MutableLiveData<com.example.rentakucapstone.Result<List<List<Double>>>>()

        val jsonBody = JsonObject().apply {
            addProperty("feature1", mobilData.mileage)
            addProperty("feature2", mobilData.merkMobil.value)
            addProperty("feature3", mobilData.modelMobil.value)
            addProperty("feature4", mobilData.kategoriMobil.value)
            addProperty("feature5", mobilData.tahunMotor.value)
            addProperty("feature6", mobilData.transmisi?.value)
        }
        val request = carApiService.mobil(
            jsonBody
        )

        request.enqueue(object : Callback<CarPredictionResponse> {
            override fun onResponse(
                call: Call<CarPredictionResponse>,
                response: Response<CarPredictionResponse>
            ) {
                if (response.isSuccessful) {
                    val prediction = response.body()?.prediction
                    carResultLiveData.postValue(com.example.rentakucapstone.Result.Success(prediction))
                } else {
                    val error = response.errorBody()?.toString()
                    carResultLiveData.postValue(com.example.rentakucapstone.Result.Failure(Throwable(error ?: "Unknown error")))
                }
            }
            override fun onFailure(call: Call<CarPredictionResponse>, t: Throwable) {
                carResultLiveData.postValue(com.example.rentakucapstone.Result.Failure(Throwable(t.message ?: "Unknown error")))
            }
        })

        return carResultLiveData
    }
}