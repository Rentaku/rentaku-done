package com.example.rentakucapstone

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: List<List<Double>>?) : Result<T>()
    data class Failure(val exception: Throwable) : Result<Nothing>()
}
