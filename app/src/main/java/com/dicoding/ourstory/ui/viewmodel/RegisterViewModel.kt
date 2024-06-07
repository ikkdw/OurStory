package com.dicoding.ourstory.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.dicoding.ourstory.data.Repository
import com.dicoding.ourstory.data.remote.auth.RegisterResponse
import com.google.gson.Gson
import retrofit2.HttpException

class RegisterViewModel (private val repository: Repository): ViewModel() {

    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        return try {
            val response = repository.register(name, email, password)
            Log.d("Login Success!", response.toString())
            response
        }
        catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)
            Log.d("Login Failed!", errorResponse.toString())
            errorResponse
        }
    }
}