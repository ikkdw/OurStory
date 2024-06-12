package com.dicoding.ourstory.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.ourstory.data.Repository
import com.dicoding.ourstory.data.model.UserModel
import com.dicoding.ourstory.data.remote.story.ListStoryItem
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    private var _userToken: List<String> = emptyList()
    val userToken: String = _userToken.toString()

//    val story: LiveData<PagingData<ListStoryItem>> = repository.getStory(userToken).cachedIn(viewModelScope)

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getStory(token: String) : LiveData<PagingData<ListStoryItem>> {
        val story: LiveData<PagingData<ListStoryItem>> = repository.getStory(token).cachedIn(viewModelScope)
        Log.d("MainViewModel", "token : $token")
        return story
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}