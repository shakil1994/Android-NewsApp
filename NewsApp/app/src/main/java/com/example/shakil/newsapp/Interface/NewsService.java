package com.example.shakil.newsapp.Interface;

import com.example.shakil.newsapp.Common.Common;
import com.example.shakil.newsapp.Model.WebSite;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsService {
    @GET("v2/sources?apiKey=" + Common.API_KEY)
    Call<WebSite> getSources();
}
