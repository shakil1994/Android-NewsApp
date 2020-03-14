package com.example.shakil.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.os.Bundle;

import com.example.shakil.newsapp.Adapter.ListSourceAdapter;
import com.example.shakil.newsapp.Common.Common;
import com.example.shakil.newsapp.Interface.NewsService;
import com.example.shakil.newsapp.Model.WebSite;
import com.google.gson.Gson;

import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView listWebSite;
    RecyclerView.LayoutManager layoutManager;
    NewsService mService;
    ListSourceAdapter adapter;
    AlertDialog dialog;
    SwipeRefreshLayout swipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Paper init*/
        Paper.init(this);

        /*Init Service*/
        mService = Common.getNewsService();

        /*Init View*/
        swipeLayout = findViewById(R.id.swipeRefresh);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadWebsiteService(true);
            }
        });

        listWebSite = findViewById(R.id.list_source);
        listWebSite.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        listWebSite.setLayoutManager(layoutManager);

        dialog = new SpotsDialog.Builder().setContext(this).setCancelable(false).build();

        loadWebsiteService(false);
    }

    private void loadWebsiteService(boolean isRefreshed) {
        if (!isRefreshed){
            String cache = Paper.book().read("cache");

            /*If have cache*/
            if (cache != null && !cache.isEmpty() && !cache.equals("null")) {
                /*Convert cache from Json to Object*/
                WebSite webSite = new Gson().fromJson(cache, WebSite.class);

                adapter = new ListSourceAdapter(getBaseContext(), webSite);
                adapter.notifyDataSetChanged();
                listWebSite.setAdapter(adapter);
            }
            /*If not have cache*/
            else {
                dialog.show();

                /*Fetch new Data*/
                mService.getSources().enqueue(new Callback<WebSite>() {
                    @Override
                    public void onResponse(Call<WebSite> call, Response<WebSite> response) {
                        adapter = new ListSourceAdapter(getBaseContext(), response.body());
                        adapter.notifyDataSetChanged();
                        listWebSite.setAdapter(adapter);

                        /*Save to cache*/
                        Paper.book().write("cache", new Gson().toJson(response.body()));

                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<WebSite> call, Throwable t) {

                    }
                });
            }
        }
        /*If from Swipe to refresh*/
        else {
            swipeLayout.setRefreshing(true);
            /*Fetch new Data*/
            mService.getSources().enqueue(new Callback<WebSite>() {
                @Override
                public void onResponse(Call<WebSite> call, Response<WebSite> response) {
                    adapter = new ListSourceAdapter(getBaseContext(), response.body());
                    adapter.notifyDataSetChanged();
                    listWebSite.setAdapter(adapter);

                    /*Save to cache*/
                    Paper.book().write("cache", new Gson().toJson(response.body()));

                    swipeLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(Call<WebSite> call, Throwable t) {

                }
            });
        }
    }
}
