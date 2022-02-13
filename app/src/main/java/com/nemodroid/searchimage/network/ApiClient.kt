package com.nemodroid.searchimage.network

import com.nemodroid.searchimage.api.DefaultResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {

    @GET("search/photos")
    suspend fun searchPhoto(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): DefaultResponse
}