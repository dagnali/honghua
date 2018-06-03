package com.qianpic.layout;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.qianpic.R;
import com.qianpic.adapter.NaviAapter;
import com.qianpic.db.Navi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/3/24.
 */

public class NavigationLayout  extends LinearLayout{


    public NavigationLayout(Context context, AttributeSet attrs) {
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.navigation,this);

        initNavis();
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycle_navi);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        NaviAapter adapter=new NaviAapter(naviList);
        recyclerView.setAdapter(adapter);
    }

    private List<Navi> naviList=new ArrayList<>();

    private void initNavis(){
        Navi navi1=new Navi("明星");
        naviList.add(navi1);
        Navi navi2=new Navi("青纯美女");
        naviList.add(navi2);
        Navi navi3=new Navi("高清美女");
        naviList.add(navi3);
        Navi navi4=new Navi("丝袜美女");
        naviList.add(navi4);
        Navi navi5=new Navi("性感美女");
        naviList.add(navi5);
        Navi navi6=new Navi("电影海报");
        naviList.add(navi6);
    }
}
