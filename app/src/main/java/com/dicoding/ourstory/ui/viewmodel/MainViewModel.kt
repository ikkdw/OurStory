package com.dicoding.ourstory.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.dicoding.ourstory.data.Repository
import com.dicoding.ourstory.data.StoryPagingSource
import com.dicoding.ourstory.data.model.UserModel
import com.dicoding.ourstory.data.remote.auth.ApiService
import com.dicoding.ourstory.data.remote.story.GetStoryResponse
import com.dicoding.ourstory.data.remote.story.ListStoryItem
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository, private val apiService: ApiService) : ViewModel() {
    private val _listStory = MutableLiveData<GetStoryResponse>()
    val listStory: MutableLiveData<GetStoryResponse> = _listStory

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getStory(token: String): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService, token)
            }
        ).liveData
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
    fun getAllListStory(token: String) {
        viewModelScope.launch {
            try {
                Log.d("MainViewModel", "Token: $token")
                val successResponse = repository.getAllStory("Bearer $token")
                _listStory.postValue(successResponse)
                Log.d("Get Story", "$successResponse")
            }
            catch (e: Exception) {
                Log.e("Get Story", e.toString())
            }
        }
    }
}