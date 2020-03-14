package com.example.shakil.newsapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
    }

    @Override
    public int getItemCount() {
        return webSite.getSources().size();
    }

    public class ListSourceViewHolder extends RecyclerView.ViewHolder {

        TextView source_title;
        CircleImageView source_image;

        public ListSourceViewHolder(@NonNull View itemView) {
            super(itemView);

            source_title = itemView.findViewById(R.id.source_name);
            source_image = itemView.findViewById(R.id.source_image);
        }
    }
}
