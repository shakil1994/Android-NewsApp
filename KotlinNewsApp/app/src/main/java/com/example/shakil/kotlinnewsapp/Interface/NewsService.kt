package com.example.shakil.kotlinnewsapp.Interface

import com.example.shakil.kotlinnewsapp.Model.WebSite
import retrofit2.Call
import retrofit2.http.GET

interface NewsService {
    @get: GET("v2/sources?apiKey=7cc6f9c8dfa44fe993467007ada10a9e")
    val sources: Call<WebSite>
}