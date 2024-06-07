package com.dicoding.ourstory.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.ourstory.data.Repository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel(private val repository: Repository) : ViewModel() {

    private val _uploadSuccess = MutableLiveData<Boolean>()

    fun uploadStory(photo: MultipartBody.Part, description: RequestBody, token: String) {
        viewModelScope.launch {
            try {
                repository.uploadStory(photo, description, token)
                _uploadSuccess.postValue(true)
            } catch (e: Exception) {
                _uploadSuccess.postValue(false)
                Log.e("UploadStory", "Error: ${e.message}", e)
            }
        }
    }
}