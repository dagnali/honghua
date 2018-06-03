package com.qianpic.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qianpic.MainActivity;
import com.qianpic.R;
import com.qianpic.db.CategoryImg;

import java.util.List;

/**
 * Created by admin on 2018/4/5.
 */

public class CategoryImgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<CategoryImg> imgList;
    private Context mContext;

    private int normalType = 0;
    private int footType = 1;
    private boolean hasMore = true;
    private boolean fadeTips = false;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    public CategoryImgAdapter(Context mContext, List<CategoryImg> imgList,boolean hasMore){

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

        holder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                CategoryImg categoryImg=imgList.get(position);
                Intent intent=new Intent(mContext, MainActivity.class);
                intent.putExtra("category",categoryImg.getCate());
                mContext.startActivity(intent);
            }
        });
            return holder;
        }
        else {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.footview,parent,false);
              holder=new FootHolder(view);
            return holder;
        }


    }



    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NormalHolder) {
            CategoryImg img = imgList.get(position);
            ((NormalHolder) holder).textView.setText(img.getName());
            Glide.with(mContext).load(img.getLink()).into(((NormalHolder) holder).imageView);
        }else {
            ((FootHolder) holder).tips.setVisibility(View.VISIBLE);
            if (hasMore == true) {
                fadeTips = false;
                if (imgList.size() > 0) {
                    ((FootHolder) holder).tips.setText("正在加载更多...");
                }
            } else {
                if (imgList.size() > 0) {
                    ((FootHolder) holder).tips.setText("没有更多数据了");
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ((FootHolder) holder).tips.setVisibility(View.GONE);
                            fadeTips = true;
                            hasMore = true;
                        }
                    }, 500);
                }
            }
        }
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
}
