package com.example.shakil.newsapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shakil.newsapp.Interface.ItemClickListener;
import com.example.shakil.newsapp.ListNews;
import com.example.shakil.newsapp.Model.WebSite;
import com.example.shakil.newsapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListSourceAdapter extends RecyclerView.Adapter<ListSourceAdapter.ListSourceViewHolder> {

    private Context context;
    private WebSite webSite;

    public ListSourceAdapter(Context context, WebSite webSite) {
        this.context = context;
        this.webSite = webSite;
    }

    @NonNull
    @Override
    public ListSourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListSourceViewHolder(LayoutInflater.from(context).inflate(R.layout.source_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListSourceViewHolder holder, int position) {
        holder.source_title.setText(webSite.getSources().get(position).getName());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(context, ListNews.class);
                intent.putExtra("source", webSite.getSources().get(position).getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return webSite.getSources().size();
    }

    public class ListSourceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView source_title;
        CircleImageView source_image;

        ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public ListSourceViewHolder(@NonNull View itemView) {
            super(itemView);

            source_title = itemView.findViewById(R.id.source_name);
            source_image = itemView.findViewById(R.id.source_image);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition());
        }
    }
}
