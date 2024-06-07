package com.dicoding.ourstory.data.di

import android.content.Context
import com.dicoding.ourstory.data.Repository
import com.dicoding.ourstory.data.model.UserPreferenceModel
import com.dicoding.ourstory.data.model.dataStore
import com.dicoding.ourstory.data.remote.auth.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {   fun provideRepository(context: Context): Repository {
    val pref = UserPreferenceModel.getInstance(context.dataStore)
    val user = runBlocking { pref.getSession().first() }
    val apiService = ApiConfig.getApiService()
    return Repository.getInstance(apiService, pref)
    }
}