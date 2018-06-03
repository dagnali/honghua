package com.qianpic.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.qianpic.R;
import com.qianpic.db.Navi;

import java.util.List;

import static com.qianpic.R.id.navi_name;

/**
 * Created by admin on 2018/3/24.
 */

public class NaviAapter extends RecyclerView.Adapter<NaviAapter.ViewHolder> {

    private List<Navi> mNaviList;

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView navigationName;

        public ViewHolder(View view){
            super(view);
            navigationName=(TextView)view.findViewById(navi_name);
        }
    }

    public NaviAapter(List<Navi> naviList){
        mNaviList=naviList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_navi,parent,false);
        final ViewHolder holder=new ViewHolder(view);

        holder.itemView.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                resetTab();
               View textView= v.findViewById(navi_name);
                textView.setBackgroundColor(0xffff00ff);
                int position=holder.getAdapterPosition();

                Navi navi=mNaviList.get(position);
                Toast.makeText(v.getContext(),"you clicked view"+navi.getName(),Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    /**
     * 重置所有tab状态为未选中
     */
    private void resetTab() {
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Navi navi=mNaviList.get(position);
        holder.navigationName.setText(navi.getName());
    }

    @Override
    public int getItemCount() {
        return mNaviList.size();
    }
}
