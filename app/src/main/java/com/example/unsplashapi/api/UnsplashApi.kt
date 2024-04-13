package com.example.unsplashapi.api

import com.example.unsplashapi.data.UnsplashResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashApi {

    companion object{
        const val BASE_URL = "https://api.unsplash.com/"

        //todo add your unsplash api key
        const val CLIENT_ID="your unsplash key"
    }

    @Headers("Accept-Version: v1", "Authorization: Client-ID $CLIENT_ID")
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query")query: String,
        @Query("page")page: Int,
       @Query("per_page")per_page: Int,

    ):UnsplashResponse
}