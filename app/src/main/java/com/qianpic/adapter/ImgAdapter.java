package com.qianpic.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qianpic.ImgActivity;
import com.qianpic.R;
import com.qianpic.db.Img;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/1/16.
 */

public class ImgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Img> imgList;
    private Context mContext;

    private int normalType = 0;
    private int footType = 1;
    private boolean hasMore = true;
    private boolean fadeTips = false;

    private Handler mHandler = new Handler(Looper.getMainLooper());


    public ImgAdapter(Context mContext,List<Img> imgList,boolean hasMore){
        this.mContext=mContext;
        this.imgList=imgList;
        this.hasMore = hasMore;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final RecyclerView.ViewHolder holder;
        if (viewType == normalType) {


            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
            holder=new NormalHolder(view);
            holder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    Img Img = imgList.get(position);
                    Intent intent = new Intent(mContext, ImgActivity.class);
                    intent.putExtra("img", Img.getImglink());
                    Log.d("aa", Img.getImglink());
                    mContext.startActivity(intent);
                }
            });
        }
        else {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.footview,parent,false);
            holder=new FootHolder(view);

        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        Img img=imgList.get(position);
        ((NormalHolder) holder).textView.setText(img.getName());
        Glide.with(mContext).load(img.getLink()).into(((NormalHolder) holder).imageView);

    }

    @Override
    public int getItemCount() {
        return imgList.size();
    }

    class NormalHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        ImageView imageView;
        public NormalHolder(View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.image);
            textView=(TextView)itemView.findViewById(R.id.textview1);
        }
    }

    class FootHolder extends RecyclerView.ViewHolder {
        private TextView tips;

        public FootHolder(View itemView) {
            super(itemView);
            tips = (TextView) itemView.findViewById(R.id.tips);
        }
    }

    public boolean isFadeTips() {
        return fadeTips;
    }

    public int getRealLastPosition() {
        return imgList.size();
    }

    public void updateList(List<Img> newDatas, boolean hasMore) {
        if (newDatas != null) {
            imgList.addAll(newDatas);
        }
        this.hasMore = hasMore;
        notifyDataSetChanged();
    }

    public void resetDatas() {
        imgList = new ArrayList<>();
    }
}
