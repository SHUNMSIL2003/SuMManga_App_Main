package com.summanga.android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

public class ScoutAdapterIMG extends RecyclerView.Adapter<ScoutAdapterIMG.ScoutHolder> {
    private Context context;
    private ArrayList<ScoutIMG> scouts;

    public ScoutAdapterIMG(Context context, ArrayList<ScoutIMG> scouts) {
        this.context = context;
        this.scouts = scouts;
    }

    @Override
    public void onBindViewHolder(@NonNull ScoutHolder holder, @SuppressLint("RecyclerView") int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }


    @NonNull
    @Override
    public ScoutHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item_img,parent,false);
        return new ScoutHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoutHolder holder, int position) {
        ScoutIMG scout = scouts.get(position);
        holder.setDetails(scout,position);
    }

    @Override
    public int getItemCount() {
        return scouts.size();
    }

    public class ScoutHolder extends RecyclerView.ViewHolder {
        private ImageView imgCover;

        public ScoutHolder(@NonNull View itemView) {
            super(itemView);
            imgCover = itemView.findViewById(R.id.idMangaPageX);
        }

        public void setDetails(ScoutIMG scout, int index) {
            RequestOptions myOptions = new RequestOptions()
                    .fitCenter();
            Glide.with(context)
                    .load("https://sum-manga.azurewebsites.net"+scout.getName())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(myOptions)
                    .addListener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }
                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .apply(new RequestOptions().placeholder(R.drawable.bg_tr_br0dp_c22dp_).error(R.drawable.bg_tr_br0dp_c22dp_))
                    .into(imgCover);

        }
    }
}
