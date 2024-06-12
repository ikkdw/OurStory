package com.dicoding.ourstory.ui.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.ourstory.data.Repository
import com.dicoding.ourstory.data.di.Injection
import com.dicoding.ourstory.data.remote.auth.ApiConfig
import com.dicoding.ourstory.data.remote.auth.ApiService
import com.dicoding.ourstory.ui.viewmodel.AddStoryViewModel
import com.dicoding.ourstory.ui.viewmodel.DetailViewModel
import com.dicoding.ourstory.ui.viewmodel.LoginViewModel
import com.dicoding.ourstory.ui.viewmodel.MainViewModel
import com.dicoding.ourstory.ui.viewmodel.MapsViewModel
//import com.dicoding.ourstory.ui.viewmodel.MapsViewModel
import com.dicoding.ourstory.ui.viewmodel.RegisterViewModel
import com.dicoding.ourstory.ui.viewmodel.WelcomeViewModel

class ViewModelFactory(private val repository: Repository, private val apiService: ApiService) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AddStoryViewModel::class.java) -> {
                AddStoryViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
                MapsViewModel(repository) as T
            }
            modelClass.isAssignableFrom(WelcomeViewModel::class.java) -> {
                WelcomeViewModel(repository, apiService) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            val repository = Injection.provideRepository(context)
            val apiService = ApiConfig.getApiService()
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(repository, apiService)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}