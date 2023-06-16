package com.example.rentakucapstone.pricePrediction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rentakucapstone.dataKendaraan.MobilData
import com.example.rentakucapstone.dataKendaraan.MotorData

class PredictionViewModel : ViewModel() {
    private val repository = PredictionRepository()
    private val _predictionResult = MutableLiveData<com.example.rentakucapstone.Result<List<List<Double>>>>()
    val predictionResult: LiveData<com.example.rentakucapstone.Result<List<List<Double>>>> get() = _predictionResult

    private val _carPredictionResult = MutableLiveData<com.example.rentakucapstone.Result<List<List<Double>>>>()
    val carPredictionResult: LiveData<com.example.rentakucapstone.Result<List<List<Double>>>> get() = _carPredictionResult

    fun predictMotorPrice(motorData: MotorData) {
        val resultLiveData = repository.predictMotorPrice(motorData)
        resultLiveData.observeForever { result ->
            _predictionResult.value = result
        }
    }

    fun predictMobilPrice(mobilData: MobilData) {
        val resultLiveData = repository.predictCarPrice(mobilData)
        resultLiveData.observeForever { result ->
            _carPredictionResult.value = result
        }
    }
}