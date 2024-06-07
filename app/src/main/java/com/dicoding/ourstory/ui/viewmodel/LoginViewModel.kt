package com.dicoding.ourstory.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.ourstory.data.Repository
import com.dicoding.ourstory.data.model.UserModel
import com.dicoding.ourstory.data.remote.auth.LoginResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(private val repository: Repository) : ViewModel() {
    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
    suspend fun login(email: String, password: String): LoginResponse {
        return try {
            val response = repository.login(email, password)
            Log.d("Login Success!", response.toString())
            response
        }
        catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
            Log.d("Login Failed!", errorResponse.toString())
            errorResponse
        }
    }
}