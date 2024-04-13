package com.example.unsplashapi.data

data class UnsplashResponse(
    val results:List<UnsplashPhoto>,
    val total_pages:Int?
)
