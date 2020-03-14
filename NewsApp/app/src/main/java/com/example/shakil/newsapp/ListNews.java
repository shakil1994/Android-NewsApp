package com.example.shakil.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.shakil.newsapp.Adapter.ListNewsAdapter;
import com.example.shakil.newsapp.Common.Common;
import com.example.shakil.newsapp.Interface.NewsService;
import com.example.shakil.newsapp.Model.Article;
import com.example.shakil.newsapp.Model.News;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.florent37.diagonallayout.DiagonalLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListNews extends AppCompatActivity {

    KenBurnsView kbv;
    DiagonalLayout diagonalLayout;
    AlertDialog dialog;
    NewsService mService;
    TextView top_author, top_title;
    SwipeRefreshLayout swipeRefreshLayout;

    String source = "", webHotURL = "";

    ListNewsAdapter adapter;
    RecyclerView lstNews;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_news);

        /*Service*/
        mService = Common.getNewsService();

        dialog = new SpotsDialog.Builder().setContext(this).setCancelable(false).build();

        /*View*/
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNews(source, true);
            }
        });

        diagonalLayout = findViewById(R.id.diagonalLayout);
        diagonalLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail = new Intent(ListNews.this, DetailsArticle.class);
                detail.putExtra("webURL", webHotURL);
                startActivity(detail);
            }
        });

        kbv = findViewById(R.id.top_image);
        top_author = findViewById(R.id.top_author);
        top_title = findViewById(R.id.top_title);

        lstNews = findViewById(R.id.lstNews);
        lstNews.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        lstNews.setLayoutManager(layoutManager);

        /*Intent*/
        if (getIntent() != null) {
            source = getIntent().getStringExtra("source");
            if (!source.isEmpty()) {
                loadNews(source, false);
            }
        }
    }

    private void loadNews(String source, boolean isRefreshed) {
        if (!isRefreshed) {
            dialog.show();

            mService.getNewestArticles(Common.getAPIUrl(source, Common.API_KEY))
                    .enqueue(new Callback<News>() {
                        @Override
                        public void onResponse(Call<News> call, Response<News> response) {
                            dialog.dismiss();

                            /*Get First Article*/
                            Picasso.get().load(response.body().getArticles().get(0).getUrlToImage()).into(kbv);
                            top_title.setText(response.body().getArticles().get(0).getTitle());
                            top_author.setText(response.body().getArticles().get(0).getAuthor());

                            webHotURL = response.body().getArticles().get(0).getUrl();

                            /*Load remain article*/
                            List<Article> removeFirstItem = response.body().getArticles();
                            /*Because we already load first item to show on Diagonal layout*/
                            /*So we need to remove it*/
                            removeFirstItem.remove(0);
                            adapter = new ListNewsAdapter(getBaseContext(), removeFirstItem);
                            adapter.notifyDataSetChanged();
                            lstNews.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure(Call<News> call, Throwable t) {

                        }
                    });
        }
        else {
            dialog.show();
            mService.getNewestArticles(Common.getAPIUrl(source, Common.API_KEY))
                    .enqueue(new Callback<News>() {
                        @Override
                        public void onResponse(Call<News> call, Response<News> response) {
                            dialog.dismiss();

                            /*Get First Article*/
                            Picasso.get().load(response.body().getArticles().get(0).getUrlToImage()).into(kbv);
                            top_title.setText(response.body().getArticles().get(0).getTitle());
                            top_author.setText(response.body().getArticles().get(0).getAuthor());

                            webHotURL = response.body().getArticles().get(0).getUrl();

                            /*Load remain article*/
                            List<Article> removeFirstItem = response.body().getArticles();
                            /*Because we already load first item to show on Diagonal layout*/
                            /*So we need to remove it*/
                            removeFirstItem.remove(0);
                            adapter = new ListNewsAdapter(getBaseContext(), removeFirstItem);
                            adapter.notifyDataSetChanged();
                            lstNews.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure(Call<News> call, Throwable t) {

                        }
                    });

            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
