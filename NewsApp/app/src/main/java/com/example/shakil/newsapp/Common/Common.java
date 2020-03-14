package com.example.shakil.newsapp.Common;

import com.example.shakil.newsapp.Interface.NewsService;
import com.example.shakil.newsapp.Remote.RetrofitClient;

public class Common {
    public static final String API_KEY = "7cc6f9c8dfa44fe993467007ada10a9e";
    public static final String BASE_URL = "http://newsapi.org/";

    public static NewsService getNewsService() {
        return RetrofitClient.getClient(BASE_URL).create(NewsService.class);
    }
}
