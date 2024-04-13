package com.example.unsplashapi.api

import com.example.unsplashapi.data.UnsplashResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashApi {

    companion object{
        const val BASE_URL = "https://api.unsplash.com/"
     //   const val CLIENT_ID=BuildConfig.UNSPLASH_ACCESS_KEY//
        const val CLIENT_ID="UkcAjo4Zas_JBgcx0FSKaDJ79LXc3z45flSw0-_xUBk"
    }

    @Headers("Accept-Version: v1", "Authorization: Client-ID $CLIENT_ID")
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query")query: String,
        @Query("page")page: Int,
       @Query("per_page")per_page: Int,

    ):UnsplashResponse
}