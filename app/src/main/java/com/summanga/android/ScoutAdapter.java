package com.summanga.android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
                        20);
            }
        });
        setAnimation(holder.itemView, position);
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        if(position>SuMStaticVs.lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
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
        //holder.setIsRecyclable(true);
        return new ScoutHolder(view);
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
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
        private final TextView txtName;
        private final ImageView imgCover;
        private final View viewColor;
        private final View ViewPaddingTop;
        private final View ViewPaddingBottom;
        //private ConstraintLayout myCardView;
        private final RelativeLayout idItemBG;
        private final View SuMItem_BorderCliper_VF_HS_ELM;
        public ScoutHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.idMangaTitle);
            imgCover = itemView.findViewById(R.id.idMangaCover);
            viewColor = itemView.findViewById(R.id.idMangaTitle_Co);
            ViewPaddingTop = itemView.findViewById(R.id.idCardPaddingTop);
            ViewPaddingBottom = itemView.findViewById(R.id.idCardPaddingBottom);
            //myCardView = itemView.findViewById(R.id.myCardViewElm);
            idItemBG = itemView.findViewById(R.id.idItemBG);
            SuMItem_BorderCliper_VF_HS_ELM = itemView.findViewById(R.id.SuMItem_BorderCliper_VF_HS);
        }

        public void setDetails(Scout scout,int index) {
            if(SuMStaticVs.RV_ItemsInRowCount_LASTREALINDEX < index) {
                final int r = Integer.parseInt(scout.getKillCount().split(",")[0]);
                final int g = Integer.parseInt(scout.getKillCount().split(",")[1]);
                final int b = Integer.parseInt(scout.getKillCount().split(",")[2]);
                final int hex = Color.parseColor(String.format("#%02X%02X%02X", r, g, b));
                txtName.setText(scout.getName());
                viewColor.setBackgroundColor(hex);
                String Image_URL = "https://sum-manga.azurewebsites.net" + scout.getRank();
                RequestOptions myOptions = new RequestOptions()
                        .fitCenter();
                Glide.with(context)
                        .load(Image_URL)
                        .apply(myOptions)
                        .override(340, 510)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .addListener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                Bitmap a = ((BitmapDrawable) resource).getBitmap();
                                //a = Bitmap.createScaledBitmap(a, a.getWidth()/8, a.getHeight()/8, false);
                                int rh = (int) (a.getHeight() * 0.38);
                                a = blurDark(context, Bitmap.createBitmap(a, 0, a.getHeight() - rh, a.getWidth(), rh), 1.0f, 0.02f, r, g, b, 192);
                                viewColor.setBackground(new BitmapDrawable(context.getResources(), a));
                                return false;
                            }
                        })
                        .apply(new RequestOptions().placeholder(R.drawable.bg_tr_br0dp_c22dp_).error(R.drawable.bg_tr_br0dp_c22dp_))
                        .into(imgCover);
                idItemBG.setBackgroundColor(hex);
                if (android.os.Build.VERSION.SDK_INT < 31) {
                    SuMItem_BorderCliper_VF_HS_ELM.setClipToOutline(true);
                }

                if (index % 2 == 0) {
                    ((ViewManager) ViewPaddingBottom.getParent()).removeView(ViewPaddingBottom);
                } else {
                    ((ViewManager) ViewPaddingTop.getParent()).removeView(ViewPaddingTop);
                }

                final String MangaGernsToPross = scout.getGernString().toLowerCase(Locale.ROOT).replace(" ", "").replace("-", "");
                if (!MangaGernsToPross.contains("action")) {
                    View VTR = itemView.findViewById(R.id.idSuMExploreIndo_Gern_Action);
                    ((ViewManager) VTR.getParent()).removeView(VTR);
                }
                if (!MangaGernsToPross.contains("drama")) {
                    View VTR = itemView.findViewById(R.id.idSuMExploreIndo_Gern_Drama);
                    ((ViewManager) VTR.getParent()).removeView(VTR);
                }
                if (!MangaGernsToPross.contains("fantasy")) {
                    View VTR = itemView.findViewById(R.id.idSuMExploreIndo_Gern_Fantasy);
                    ((ViewManager) VTR.getParent()).removeView(VTR);
                }
                if (!MangaGernsToPross.contains("comedy")) {
                    View VTR = itemView.findViewById(R.id.idSuMExploreIndo_Gern_Comedy);
                    ((ViewManager) VTR.getParent()).removeView(VTR);
                }
                if (!MangaGernsToPross.contains("sliceoflife")) {
                    View VTR = itemView.findViewById(R.id.idSuMExploreIndo_Gern_SliceofLife);
                    ((ViewManager) VTR.getParent()).removeView(VTR);
                }
                if (!MangaGernsToPross.contains("scifi")) {
                    View VTR = itemView.findViewById(R.id.idSuMExploreIndo_Gern_SciFi);
                    ((ViewManager) VTR.getParent()).removeView(VTR);
                }
                if (!MangaGernsToPross.contains("supernatural")) {
                    View VTR = itemView.findViewById(R.id.idSuMExploreIndo_Gern_Supernatural);
                    ((ViewManager) VTR.getParent()).removeView(VTR);
                }
                if (!MangaGernsToPross.contains("mystery")) {
                    View VTR = itemView.findViewById(R.id.idSuMExploreIndo_Gern_Mystery);
                    ((ViewManager) VTR.getParent()).removeView(VTR);
                }
                SuMStaticVs.RV_ItemsInRowCount_LASTREALINDEX = index;
            }

        }
    }
    public static Bitmap blurDark(Context context, Bitmap image,float BLUR_RADIUS,float BITMAP_SCALE,int r,int g,int b,int a) {
        int width = Math.round(image.getWidth() * BITMAP_SCALE);
        int height = Math.round(image.getHeight() * BITMAP_SCALE);

        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        theIntrinsic.setRadius(BLUR_RADIUS);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);

        //return  outputBitmap;
        Bitmap bm = outputBitmap;

        Canvas canvas = new Canvas(bm);
        canvas.drawARGB(a,r,g,b);
        canvas.drawBitmap(bm, new Matrix(), new Paint());
        return bm;

    }
}
