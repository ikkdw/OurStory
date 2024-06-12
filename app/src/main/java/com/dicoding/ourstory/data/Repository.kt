package com.dicoding.ourstory.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dicoding.ourstory.data.model.UserModel
import com.dicoding.ourstory.data.model.UserPreferenceModel
import com.dicoding.ourstory.data.remote.auth.ApiService
import com.dicoding.ourstory.data.remote.auth.ApiConfig
import com.dicoding.ourstory.data.remote.auth.LoginResponse
import com.dicoding.ourstory.data.remote.auth.RegisterResponse
import com.dicoding.ourstory.data.remote.story.GetStoryResponse
import com.dicoding.ourstory.data.remote.story.DetailStoryResponse
import com.dicoding.ourstory.data.remote.story.ListStoryItem
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

open class Repository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreferenceModel,
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        return apiService.register(name, email, password)
    }

    suspend fun login(email: String, password: String): LoginResponse {
        return apiService.login(email, password)
    }

    suspend fun getAllStory(token: String): GetStoryResponse {
        val apiService = ApiConfig.getApiService()
        return apiService.getStories(token)
    }

    suspend fun getStoryDetail(storyId: String, token: String): DetailStoryResponse {
        val apiService = ApiConfig.getApiService()
        return apiService.getStoryDetail(storyId, token)
    }

    suspend fun getStoryLocation(token: String): GetStoryResponse {
        val apiService = ApiConfig.getApiService()
        return apiService.getStoriesWithLocation(token)
    }

    fun getStory(token: String): LiveData<PagingData<ListStoryItem>> {
        val pagingSourceFactory = {
            StoryPagingSource(apiService, token)
        }
        Log.d("Repository", "token : $token")
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = pagingSourceFactory
        ).liveData
    }

//    fun getStory(): LiveData<PagingData<ListStoryItem>> {
//        @OptIn(ExperimentalPagingApi::class)
//        return Pager(
//            config = PagingConfig(
//                pageSize = 5
//            ),
//            remoteMediator = StoryRemoteMediator(apiService),
//            pagingSourceFactory = {
//                StoryPagingSource(apiService)
//                storyDatabase.storyDao().getAllStory()
//            }
//        ).liveData
//    }

    suspend fun uploadStory(photo: MultipartBody.Part, description: RequestBody, token: String): Boolean {
        try {
            val response = apiService.uploadStory(photo, description, "Bearer $token")
            return response != null && response.error != true
        } catch (e: Exception) {

            return false
        }
    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreferenceModel
        ): Repository =
            instance ?: synchronized(this) {
//                instance ?: Repository(apiService, userPreference, storyDatabase)
                instance ?: Repository(apiService, userPreference)
            }.also { instance = it }
    }
}
//StoryDatabase