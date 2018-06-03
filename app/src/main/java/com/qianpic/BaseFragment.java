package com.qianpic;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qianpic.adapter.CategoryImgAdapter;
import com.qianpic.db.CategoryImg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/4/1.
 */

public abstract class BaseFragment extends Fragment  {

    protected Context mContent;
    protected List<CategoryImg> imgList=new ArrayList<CategoryImg>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContent = getContext();//上下文。
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.category,container,false);
        imgList.clear();
        iniData1();

        if( imgList!=null) {

            RecyclerView recyclerView = (RecyclerView)  view.findViewById(R.id.recycle_view);
            StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            CategoryImgAdapter adapter = new CategoryImgAdapter(getContext(),imgList,true);
            recyclerView.setAdapter(adapter);
        }

        return view;//初始化布局。
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData();//初始化数据。
    }

    protected void loadData() {

    }

    protected View initView() {
        return null;
    }

    protected  abstract  void iniData1();
}