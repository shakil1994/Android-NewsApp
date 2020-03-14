package com.example.shakil.kotlinnewsapp.Common

import com.example.shakil.kotlinnewsapp.Interface.NewsService
import com.example.shakil.kotlinnewsapp.Remote.RetrofitClient

object Common {
    val BASE_URL = "http://newsapi.org/"

    val newsService: NewsService
    get() = RetrofitClient.getClient(BASE_URL).create(NewsService::class.java)
}