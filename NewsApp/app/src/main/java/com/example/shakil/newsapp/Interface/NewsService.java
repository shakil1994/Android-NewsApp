package com.example.shakil.newsapp.Interface;

import com.example.shakil.newsapp.Common.Common;
import com.example.shakil.newsapp.Model.News;
import com.example.shakil.newsapp.Model.WebSite;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface NewsService {
    @GET("v2/sources?apiKey=" + Common.API_KEY)
    Call<WebSite> getSources();

    @GET
    Call<News> getNewestArticles(@Url String url);
}
