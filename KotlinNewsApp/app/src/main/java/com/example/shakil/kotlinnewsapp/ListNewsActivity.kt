package com.example.shakil.kotlinnewsapp

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shakil.kotlinnewsapp.Adapter.ListNewsAdapter
import com.example.shakil.kotlinnewsapp.Common.Common
import com.example.shakil.kotlinnewsapp.Interface.NewsService
import com.example.shakil.kotlinnewsapp.Model.News
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_list_news.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListNewsActivity : AppCompatActivity() {

    var source = ""
    var webHotUrl: String? = ""

    lateinit var dialog: AlertDialog
    lateinit var mService: NewsService
    lateinit var adapter: ListNewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_news)

        /*Init View*/
        mService = Common.newsService

        dialog = SpotsDialog.Builder().setContext(this).setCancelable(false).build()

        swipe_to_refresh.setOnRefreshListener {
            loadNews(source, true)
        }

        diagonallayout.setOnClickListener {
            val detail = Intent(baseContext, NewsDetailsActivity::class.java)
            detail.putExtra("webURL", webHotUrl)
            startActivity(detail)
        }

        list_news.setHasFixedSize(true)
        list_news.layoutManager = LinearLayoutManager(this)

        if (intent != null) {
            source = intent.getStringExtra("source")
            if (!source.isEmpty()) {
                loadNews(source, false)
            }
        }
    }

    private fun loadNews(sources: String?, isRefreshed: Boolean) {
        if (!isRefreshed) {
            dialog.show()

            mService.getNewsFromSource(Common.getNewsAPI(sources!!)).enqueue(object :
                Callback<News> {
                override fun onFailure(call: Call<News>, t: Throwable) {

                }

                override fun onResponse(call: Call<News>, response: Response<News>) {
                    dialog.dismiss()

                    /*Get first article to top news*/
                    Picasso.get().load(response.body()!!.articles!![0].urlToImage).into(top_image)

                    top_title.text = response.body()!!.articles!![0].title
                    top_author.text = response.body()!!.articles!![0].author

                    webHotUrl = response.body()!!.articles!![0].url

                    /*Load all remain articles*/
                    val removeFirstItem = response.body()!!.articles

                    /*Because we get first item to hot news, so we need remove it*/
                    removeFirstItem!!.removeAt(0)

                    adapter = ListNewsAdapter(baseContext, removeFirstItem)
                    adapter.notifyDataSetChanged()
                    list_news.adapter = adapter
                }

            })
        }
        else {
            swipe_to_refresh.isRefreshing = true
            mService.getNewsFromSource(Common.getNewsAPI(sources!!)).enqueue(object :
                Callback<News> {
                override fun onFailure(call: Call<News>, t: Throwable) {

                }

                override fun onResponse(call: Call<News>, response: Response<News>) {
                    swipe_to_refresh.isRefreshing = false

                    /*Get first article to top news*/
                    Picasso.get().load(response.body()!!.articles!![0].urlToImage).into(top_image)

                    top_title.text = response.body()!!.articles!![0].title
                    top_author.text = response.body()!!.articles!![0].author

                    webHotUrl = response.body()!!.articles!![0].url

                    /*Load all remain articles*/
                    val removeFirstItem = response.body()!!.articles

                    /*Because we get first item to hot news, so we need remove it*/
                    removeFirstItem!!.removeAt(0)

                    adapter = ListNewsAdapter(baseContext, removeFirstItem)
                    adapter.notifyDataSetChanged()
                    list_news.adapter = adapter
                }

            })
        }
    }
}
