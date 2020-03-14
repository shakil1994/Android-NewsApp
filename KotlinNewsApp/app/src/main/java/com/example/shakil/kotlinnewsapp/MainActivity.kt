package com.example.shakil.kotlinnewsapp

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shakil.kotlinnewsapp.Adapter.ListSourceAdapter
import com.example.shakil.kotlinnewsapp.Common.Common
import com.example.shakil.kotlinnewsapp.Interface.NewsService
import com.example.shakil.kotlinnewsapp.Model.WebSite
import com.google.gson.Gson
import dmax.dialog.SpotsDialog
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var layoutManager: LinearLayoutManager
    lateinit var mService: NewsService
    lateinit var adapter: ListSourceAdapter
    lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*Init cache DB*/
        Paper.init(this)

        /*Init Service*/
        mService = Common.newsService

        /*Init View*/
        swipe_to_refresh.setOnRefreshListener {
            loadWebSiteService(true)
        }

        recycler_view_source_news.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recycler_view_source_news.layoutManager = layoutManager

        dialog = SpotsDialog.Builder().setContext(this).setCancelable(false).build()

        loadWebSiteService(false)
    }

    private fun loadWebSiteService(isRefresh: Boolean) {
        if (!isRefresh) {
            val cache = Paper.book().read<String>("cache")
            if (cache != null && !cache.isBlank() && cache != null) {
                /*Read cache*/
                val webSite = Gson().fromJson<WebSite>(cache, WebSite::class.java)
                adapter = ListSourceAdapter(baseContext, webSite)
                adapter.notifyDataSetChanged()
                recycler_view_source_news.adapter = adapter
            }
            else {
                /*Load website and write cache*/
                dialog.show()

                /*Fetch new Data*/
                mService.sources.enqueue(object : Callback<WebSite> {
                    override fun onFailure(call: Call<WebSite>, t: Throwable) {
                        Toast.makeText(baseContext, "Failed", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<WebSite>, response: Response<WebSite>) {
                        adapter = ListSourceAdapter(baseContext, response.body()!!)
                        adapter.notifyDataSetChanged()
                        recycler_view_source_news.adapter = adapter

                        /*Save to cache*/
                        Paper.book().write("cache", Gson().toJson(response.body()))

                        dialog.dismiss()
                    }

                })
            }
        }
        else {
            swipe_to_refresh.isRefreshing = true
            /*Fetch new Data*/
            mService.sources.enqueue(object : Callback<WebSite> {
                override fun onFailure(call: Call<WebSite>, t: Throwable) {
                    Toast.makeText(baseContext, "Failed", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<WebSite>, response: Response<WebSite>) {
                    adapter = ListSourceAdapter(baseContext, response.body()!!)
                    adapter.notifyDataSetChanged()
                    recycler_view_source_news.adapter = adapter

                    /*Save to cache*/
                    Paper.book().write("cache", Gson().toJson(response.body()))

                    swipe_to_refresh.isRefreshing = false
                }

            })
        }
    }
}
