package com.dicoding.ourstory.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.ourstory.data.Repository
import com.dicoding.ourstory.data.remote.story.DetailStoryResponse
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: Repository) : ViewModel() {

    private val _storyState = MutableLiveData<StoryDetailState>()
    val storyState: LiveData<StoryDetailState> = _storyState

    sealed class StoryDetailState {
        data object Loading : StoryDetailState()
        data class Success(val detailResponse: DetailStoryResponse) : StoryDetailState()
        data class Error(val exception: Exception) : StoryDetailState()
    }

    fun getStoryDetail(storyId: String, token: String) {
        viewModelScope.launch {
            _storyState.value = StoryDetailState.Loading
            try {
                val successResponse = repository.getStoryDetail(storyId, "Bearer $token")
                _storyState.value = StoryDetailState.Success(successResponse)
                Log.d("Get Story", "$successResponse")
            } catch (e: Exception) {
                _storyState.value = StoryDetailState.Error(e)
                Log.e("Get Story", e.toString())
            }
            Log.d("Check Token", "$storyId, $token")
        }
    }
}