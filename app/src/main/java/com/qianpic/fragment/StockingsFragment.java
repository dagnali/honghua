package com.qianpic.fragment;

import com.qianpic.BaseFragment;
import com.qianpic.db.CategoryImg;

/**
 * Created by admin on 2018/4/1.
 */

public class StockingsFragment extends BaseFragment {

    @Override
    protected void iniData1() {
        CategoryImg img1=new CategoryImg("丝袜美腿","http://www.mtyoyo.com/d/file/bigpic/2017/09/04/fclgplvnlnr.jpg", "/siwameit/siwamein/");
        imgList.add(img1);
        CategoryImg img2=new CategoryImg("Beautyleg","http://www.mtyoyo.com/d/file/bigpic/2017/07/10/jjzgp4f4m1e.jpg", "/siwameit/Beautyleg/");
        imgList.add(img2);
        CategoryImg img3=new CategoryImg("丽柜","http://www.mtyoyo.com/d/file/bigpic/2017/04/10/gxxid01o0e5.jpg", "/siwameit/ligui/");
        imgList.add(img3);
        CategoryImg img4=new CategoryImg("ROSI写真","http://www.mtyoyo.com/d/file/bigpic/2017/04/10/c1mwaxp5ym1.jpg", "/siwameit/ROSI/");
        imgList.add(img4);
        CategoryImg img5=new CategoryImg("AISS写真","http://www.mtyoyo.com/d/file/bigpic/2017/04/10/vhmjvaodtya.jpg", "/siwameit/AISS/");
        imgList.add(img5);
        CategoryImg img6=new CategoryImg("美腿宝贝","http://www.mtyoyo.com/d/file/bigpic/2017/07/28/q0wira2qebs.jpg", "/siwameit/meituibb/");
        imgList.add(img6);
    }
}
