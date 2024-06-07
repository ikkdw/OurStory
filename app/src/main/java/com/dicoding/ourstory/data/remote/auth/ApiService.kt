package com.dicoding.ourstory.data.remote.auth

import com.dicoding.ourstory.data.remote.story.DetailStoryResponse
import com.dicoding.ourstory.data.remote.story.GetStoryResponse
import com.dicoding.ourstory.data.remote.story.AddStoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("stories")
    suspend fun getStories(
        @Header("Authorization") token: String,
    ): GetStoryResponse

    @GET("stories/{id}")
    suspend fun getStoryDetail(
        @Path("id") storyId: String,
        @Header("Authorization") token: String
    ): DetailStoryResponse

    @Multipart
    @POST("stories")
    suspend fun uploadStory(
        @Part photo: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Header("Authorization") token: String,
    ): AddStoryResponse
}
