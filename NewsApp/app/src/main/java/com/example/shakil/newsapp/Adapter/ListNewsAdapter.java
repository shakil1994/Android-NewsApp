package com.example.shakil.newsapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shakil.newsapp.Common.ISO8601Parse;
import com.example.shakil.newsapp.DetailsArticle;
import com.example.shakil.newsapp.Interface.ItemClickListener;
import com.example.shakil.newsapp.ListNews;
import com.example.shakil.newsapp.Model.Article;
import com.example.shakil.newsapp.R;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListNewsAdapter extends RecyclerView.Adapter<ListNewsAdapter.ListNewsViewHolder> {

    private Context context;
    private List<Article> articleList;

    public ListNewsAdapter(Context context, List<Article> articleList) {
        this.context = context;
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public ListNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.news_layout, parent, false);
        return new ListNewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListNewsViewHolder holder, int position) {
        Picasso.get().load(articleList.get(position).getUrlToImage()).into(holder.article_image);

        if (articleList.get(position).getTitle().length() > 65) {
            holder.article_title.setText(articleList.get(position).getTitle().substring(0, 65) + "...");
        }
        else {
            holder.article_title.setText(articleList.get(position).getTitle());
        }

        if (articleList.get(position).getPublishedAt() != null){
            Date date = null;
            try {
                date = ISO8601Parse.parse(articleList.get(position).getPublishedAt());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            holder.article_time.setReferenceTime(date.getTime());
        }

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(context, DetailsArticle.class);
                intent.putExtra("webURL", articleList.get(position).getUrl());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class ListNewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView article_title;
        RelativeTimeTextView article_time;
        CircleImageView article_image;

        ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public ListNewsViewHolder(@NonNull View itemView) {
            super(itemView);

            article_title = itemView.findViewById(R.id.article_title);
            article_time = itemView.findViewById(R.id.article_time);
            article_image = itemView.findViewById(R.id.article_image);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition());
        }
    }
}
