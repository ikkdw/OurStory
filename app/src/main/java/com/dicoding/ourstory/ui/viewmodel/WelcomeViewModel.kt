package com.dicoding.ourstory.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.ourstory.data.Repository
import com.dicoding.ourstory.data.model.UserModel
import com.dicoding.ourstory.data.remote.auth.ApiService

class WelcomeViewModel (private val repository: Repository, private val apiService: ApiService) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}