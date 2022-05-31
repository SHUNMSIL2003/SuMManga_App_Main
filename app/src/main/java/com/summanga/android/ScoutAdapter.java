package com.summanga.android;

import static androidx.core.graphics.drawable.DrawableCompat.setTint;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ScoutAdapter extends RecyclerView.Adapter<ScoutAdapter.ScoutHolder> {
    private Context context;
    private ArrayList<Scout> scouts;
    MainActivity mainActivity;
    private static MainActivity.RecyclerViewClickListener itemListener;

    public ScoutAdapter(Context context, ArrayList<Scout> scouts,MainActivity ma) {
        this.context = context;
        this.scouts = scouts;
        this.mainActivity = ma;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
         //... ...
        public ItemViewHolder(View convertView) {
            super(convertView);
             //... ...
            convertView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemListener.recyclerViewListClicked(v, this.getPosition());

        }
    }
    // Return the size of your dataset (invoked by the layout manager)
    public boolean IsEmpty() {
        return scouts.isEmpty();
    }
    @Override
    public void onBindViewHolder(@NonNull ScoutHolder holder, @SuppressLint("RecyclerView") int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.card_click);
                holder.itemView.startAnimation(animation);
                new android.os.Handler(Looper.getMainLooper()).postDelayed(
                        new Runnable() {
                            public void run() {
                                try {
                                    mainActivity.SuMExploreInfoStart_Native(scouts.get(position).getSuMExploreURL(), scouts.get(position).getKillCount(), scouts.get(position).getName(), scouts.get(position).getSuMExploreAuthor(), scouts.get(position).getSuMExploreGerns(), scouts.get(position).getMangaAgeRating(), scouts.get(position).getRank());
                                } catch (IOException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        365);
            }
        });
        setAnimation(holder.itemView, position);
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        /*if (position > SuMStaticVs.lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_up);
            viewToAnimate.startAnimation(animation);
        } else {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_down);
            viewToAnimate.startAnimation(animation);
        }
        SuMStaticVs.lastPosition = position;*/
        //viewToAnimate.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        if(position>SuMStaticVs.lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.card_pop);
            viewToAnimate.startAnimation(animation);
            SuMStaticVs.lastPosition = position;
        }
    }



    @SuppressLint("NotifyDataSetChanged")
    public void clearData() {
        scouts.clear();
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ScoutHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item,parent,false);
        return new ScoutHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoutHolder holder, int position) {
        Scout scout = scouts.get(position);
        holder.setDetails(scout,position);
    }


    @Override
    public int getItemCount() {
        return scouts.size();
    }

    public class ScoutHolder extends RecyclerView.ViewHolder {
        private TextView txtName;
        private ImageView imgCover;
        private View viewColor;
        private View ViewPaddingTop;
        private View ViewPaddingBottom;
        private CardView myCardView;
        private RelativeLayout idItemBG;
        public ScoutHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.idMangaTitle);
            imgCover = itemView.findViewById(R.id.idMangaCover);
            viewColor = itemView.findViewById(R.id.idMangaTitle_Co);
            ViewPaddingTop = itemView.findViewById(R.id.idCardPaddingTop);
            ViewPaddingBottom = itemView.findViewById(R.id.idCardPaddingBottom);
            myCardView = itemView.findViewById(R.id.myCardViewElm);
            idItemBG = itemView.findViewById(R.id.idItemBG);
        }

        public void setDetails(Scout scout,int index) {
            final int r = Integer.parseInt(scout.getKillCount().split(",")[0]);
            final int g = Integer.parseInt(scout.getKillCount().split(",")[1]);
            final int b = Integer.parseInt(scout.getKillCount().split(",")[2]);
            final int hex =Color.parseColor(String.format("#%02X%02X%02X", r, g, b));
            txtName.setText(scout.getName());
            viewColor.setBackgroundColor(hex);
            String Image_URL = "https://sum-manga.azurewebsites.net" + scout.getRank();
            RequestOptions myOptions = new RequestOptions()
                    .fitCenter(); // or centerCrop
                    //.override(450, 450);
            Glide.with(context)
                    .load(Image_URL)
                    .apply(myOptions)
                    .override(SuMStaticVs.imageWidthPixels, SuMStaticVs.imageHeightPixels)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .addListener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }
                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            Bitmap a = ((BitmapDrawable)resource).getBitmap();
                            a = Bitmap.createScaledBitmap(a, a.getWidth()/8, a.getHeight()/8, false);
                            int rh = (int) (a.getHeight()*0.38);
                            a = MainActivity.blurDark(context,Bitmap.createBitmap(a, 0, a.getHeight()-rh, a.getWidth(), rh),2.0f,r,g,b,192);
                            //Bitmap resizedBmp = Bitmap.createBitmap(((BitmapDrawable)resource).getBitmap(), 0, 0, 180, 86);
                            viewColor.setBackground(new BitmapDrawable(context.getResources(), a));
                            return false;
                        }
                    })
                    .apply(new RequestOptions().placeholder(R.drawable.bg_tr_br0dp_c22dp_).error(R.drawable.bg_tr_br0dp_c22dp_))
                    .into(imgCover);
            myCardView.setCardBackgroundColor(Color.TRANSPARENT);
            idItemBG.setBackgroundColor(hex);
            if(index % 2 == 0)
            {
                //System.out.println("The given number "+index+" is Even ");
                //namebar = ViewPaddingTop;
                itemView.findViewById(R.id.idCardPaddingTop).setVisibility(View.GONE);
                itemView.findViewById(R.id.idCardPaddingBottom).setVisibility(View.VISIBLE);
            }
            else
            {
                //System.out.println("The given number "+index+" is Odd ");
                //namebar = ViewPaddingBottom;
                itemView.findViewById(R.id.idCardPaddingBottom).setVisibility(View.GONE);
                itemView.findViewById(R.id.idCardPaddingTop).setVisibility(View.VISIBLE);
            }
            //((ViewGroup) namebar.getParent()).removeView(namebar);

            final String MangaGernsToPross = scout.getGernString().toLowerCase(Locale.ROOT).replace(" ","").replace("-","");
            if(!MangaGernsToPross.contains("action")){
                //((ViewGroup) itemView.findViewById(R.id.idSuMExploreIndo_Gern_Action).getParent()).removeView(itemView.findViewById(R.id.idSuMExploreIndo_Gern_Action));
                itemView.findViewById(R.id.idSuMExploreIndo_Gern_Action).setVisibility(View.GONE);
            } else itemView.findViewById(R.id.idSuMExploreIndo_Gern_Action).setVisibility(View.VISIBLE);
            if(!MangaGernsToPross.contains("drama")){
                //((ViewGroup) itemView.findViewById(R.id.idSuMExploreIndo_Gern_Drama).getParent()).removeView(itemView.findViewById(R.id.idSuMExploreIndo_Gern_Drama));
                itemView.findViewById(R.id.idSuMExploreIndo_Gern_Drama).setVisibility(View.GONE);
            } else itemView.findViewById(R.id.idSuMExploreIndo_Gern_Drama).setVisibility(View.VISIBLE);
            if(!MangaGernsToPross.contains("fantasy")){
                //((ViewGroup) itemView.findViewById(R.id.idSuMExploreIndo_Gern_Fantasy).getParent()).removeView(itemView.findViewById(R.id.idSuMExploreIndo_Gern_Fantasy));
                itemView.findViewById(R.id.idSuMExploreIndo_Gern_Fantasy).setVisibility(View.GONE);
            } else itemView.findViewById(R.id.idSuMExploreIndo_Gern_Fantasy).setVisibility(View.VISIBLE);
            if(!MangaGernsToPross.contains("comedy")){
                //((ViewGroup) itemView.findViewById(R.id.idSuMExploreIndo_Gern_Comedy).getParent()).removeView(itemView.findViewById(R.id.idSuMExploreIndo_Gern_Comedy));
                itemView.findViewById(R.id.idSuMExploreIndo_Gern_Comedy).setVisibility(View.GONE);
            } else itemView.findViewById(R.id.idSuMExploreIndo_Gern_Comedy).setVisibility(View.VISIBLE);
            if(!MangaGernsToPross.contains("sliceoflife")) {
                //((ViewGroup) itemView.findViewById(R.id.idSuMExploreIndo_Gern_SliceofLife).getParent()).removeView(itemView.findViewById(R.id.idSuMExploreIndo_Gern_SliceofLife));
                itemView.findViewById(R.id.idSuMExploreIndo_Gern_SliceofLife).setVisibility(View.GONE);
            } else itemView.findViewById(R.id.idSuMExploreIndo_Gern_SliceofLife).setVisibility(View.VISIBLE);
            if(!MangaGernsToPross.contains("scifi")){
                //((ViewGroup) itemView.findViewById(R.id.idSuMExploreIndo_Gern_SciFi).getParent()).removeView(itemView.findViewById(R.id.idSuMExploreIndo_Gern_SciFi));
                itemView.findViewById(R.id.idSuMExploreIndo_Gern_SciFi).setVisibility(View.GONE);
            } else itemView.findViewById(R.id.idSuMExploreIndo_Gern_SciFi).setVisibility(View.VISIBLE);
            if(!MangaGernsToPross.contains("supernatural")){
                //((ViewGroup) itemView.findViewById(R.id.idSuMExploreIndo_Gern_Supernatural).getParent()).removeView(itemView.findViewById(R.id.idSuMExploreIndo_Gern_Supernatural));
                itemView.findViewById(R.id.idSuMExploreIndo_Gern_Supernatural).setVisibility(View.GONE);
            } else itemView.findViewById(R.id.idSuMExploreIndo_Gern_Supernatural).setVisibility(View.VISIBLE);
            if(!MangaGernsToPross.contains("mystery")){
                //((ViewGroup) itemView.findViewById(R.id.idSuMExploreIndo_Gern_Mystery).getParent()).removeView(itemView.findViewById(R.id.idSuMExploreIndo_Gern_Mystery));
                itemView.findViewById(R.id.idSuMExploreIndo_Gern_Mystery).setVisibility(View.GONE);
            } else itemView.findViewById(R.id.idSuMExploreIndo_Gern_Mystery).setVisibility(View.VISIBLE);

        }
    }
}
