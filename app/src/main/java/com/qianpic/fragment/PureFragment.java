package com.qianpic.fragment;

import com.qianpic.BaseFragment;
import com.qianpic.db.CategoryImg;

/**
 * Created by admin on 2018/4/1.
 */

public class PureFragment extends BaseFragment {


    @Override
    protected void iniData1() {
        CategoryImg img1=new CategoryImg("校花美女","http://www.mtyoyo.com/d/file/qingchunmein/xiaohuamn/2017-09-21/6a9a1abd329bd9a5277ad7c75b08a750.jpg", "/qingchunmein/xiaohuamn/");
        imgList.add(img1);
        CategoryImg img2=new CategoryImg("萝莉美女","http://www.mtyoyo.com/d/file/qingchunmein/luolimn/2017-08-31/6c459e90342741605922249bff546c00.jpg", "/qingchunmein/luolimn/");
        imgList.add(img2);
        CategoryImg img3=new CategoryImg("小清新","http://www.mtyoyo.com/d/file/qingchunmein/xiaoqingx/2017-09-23/f041517fc17cbbe3352d376af84cc188.jpg", "/qingchunmein/xiaoqingx/");
        imgList.add(img3);
        CategoryImg img4=new CategoryImg("青春美女","http://www.mtyoyo.com/d/file/bigpic/2017/04/01/bfncopq0j2l.jpg", "/qingchunmein/qingchunmn/");
        imgList.add(img4);
        CategoryImg img5=new CategoryImg("cosplay","http://www.mtyoyo.com/d/file/xingganmot/cosplay/2017-08-03/742e57ddd3ea245e885be5ff48e24309.jpg", "/qingchunmein/cosplay/");
        imgList.add(img5);
    }
}
