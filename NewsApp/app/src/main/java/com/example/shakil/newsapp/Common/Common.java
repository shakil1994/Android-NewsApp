package com.example.shakil.newsapp.Common;

import com.example.shakil.newsapp.Interface.NewsService;
import com.example.shakil.newsapp.Remote.RetrofitClient;

public class Common {
    public static final String API_KEY = "7cc6f9c8dfa44fe993467007ada10a9e";
    public static final String BASE_URL = "https://newsapi.org/";

    public static NewsService getNewsService() {
        return RetrofitClient.getClient(BASE_URL).create(NewsService.class);
    }

    /*http://newsapi.org/v2/top-headlines?sources=bbc-news&apiKey=7cc6f9c8dfa44fe993467007ada10a9e*/

    public static String getAPIUrl(String source, String apiKEY) {
        StringBuilder apiUrl = new StringBuilder("https://newsapi.org/v2/top-headlines?sources=");

        return apiUrl.append(source)
                .append("&apiKey=")
                .append(apiKEY)
                .toString();
    }
}
