package com.dicoding.ourstory.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.ourstory.data.Repository
import com.dicoding.ourstory.data.remote.story.ListStoryItem
import kotlinx.coroutines.launch

class MapsViewModel(private val repository: Repository) : ViewModel() {
    private val _listStory = MutableLiveData<List<ListStoryItem>?>()
    val listStory: LiveData<List<ListStoryItem>?> = _listStory

    fun getStoryLocation(token: String) {
        viewModelScope.launch {
            try {
                val response = repository.getStoryLocation("Bearer $token")
                Log.d("MapsViewModel", "Response: $response")
                _listStory.value = response.listStory
            } catch (e: Exception) {
            }
        }
    }
}
