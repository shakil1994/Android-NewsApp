package com.example.shakil.kotlinnewsapp.Common

import com.example.shakil.kotlinnewsapp.Interface.NewsService
import com.example.shakil.kotlinnewsapp.Remote.RetrofitClient
import java.lang.StringBuilder
import javax.xml.transform.Source

object Common {
    val BASE_URL = "https://newsapi.org/"
    val API_KEY = "7cc6f9c8dfa44fe993467007ada10a9e"

    val newsService: NewsService
    get() = RetrofitClient.getClient(BASE_URL).create(NewsService::class.java)

    /*http://newsapi.org/v2/top-headlines?sources=bbc-news&apiKey=7cc6f9c8dfa44fe993467007ada10a9e*/
    fun getNewsAPI(source: String): String {
        val apiUrl = StringBuilder("https://newsapi.org/v2/top-headlines?sources=")
            .append(source)
            .append("&apiKey=")
            .append(API_KEY)
            .toString()

        return apiUrl
    }
}