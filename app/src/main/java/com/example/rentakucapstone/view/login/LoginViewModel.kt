package com.example.rentakucapstone.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

//class LoginViewModel(private val pref: UserPreference) : ViewModel() {
//    fun getUser(): LiveData<UserModel> {
//        return pref.getUser().asLiveData()
//    }
//
//    fun login() {
//        viewModelScope.launch {
//            pref.login()
//        }
//    }
//}