package com.qianpic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.qianpic.db.ImgBig;
import com.qianpic.util.Links;
import com.qianpic.util.Page;
import com.qianpic.util.PageParserTool;

import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static org.litepal.crud.DataSupport.where;

/**
 * Created by admin on 2018/4/26.
 */

public class ImgActivity extends AppCompatActivity  {

    //手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)
    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;
    ImageView iv;
    ProgressDialog pd1;
    int p=1;
    String category;
    GestureDetector   mGestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img);

        Intent intent = getIntent();
         category = intent.getStringExtra("img");
        Log.d("aa",category);
        iv= (ImageView) findViewById(R.id.image);
        //Glide.with(getApplicationContext()).load("http://www.mtyoyo.com"+category).into(iv);
       // pd1 = ProgressDialog.show(this, "提示", category);
        //DataSupport.deleteAll(ImgBig.class);
        List<ImgBig> imgBigList= where("cate='"+category+"' and "+"page="+p+"").find(ImgBig.class);

        if ( imgBigList==null||imgBigList.size()==0) {
            pd1 = ProgressDialog.show(this, "提示", "正在加载中");

            crawling(new String[]{"http://www.mtyoyo.com"+category},category,p);

        }
        else {

            Glide.with(getApplicationContext()).load(imgBigList.get(0).getLink()).into(iv);
        }
    }

    private void crawling(String[] seeds,String cate,int page ){
        //初始化 URL 队列
        initCrawlerWithSeeds(seeds);

        //循环条件：待抓取的链接不空且抓取的网页不多于 1000
        while (!Links.unVisitedUrlQueueIsEmpty()  ) {

            //先从待访问的序列中取出第一个；
            String visitUrl = (String) Links.removeHeadOfUnVisitedUrlQueue();
            if (visitUrl == null){
                return;
            }

            //根据URL得到page;
            sendRequstAndGetResponse(visitUrl ,cate,page);
        }

    }

    private void initCrawlerWithSeeds(String[] seeds) {
        for (int i = 0; i < seeds.length; i++){
            Links.addUnvisitedUrlQueue(seeds[i]);
        }
    }

    private void sendRequstAndGetResponse(final String url, final String cate,final int p)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] content=null;
                OkHttpClient okHttpClient=new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                Call call  = okHttpClient.newCall(request);
                try{


                    Response response = call.execute();
                    String responseData=response.body().string();
                    content=responseData.getBytes();

                    Page page= new Page(content,url,"");
                    //对page进行处理： 访问DOM的某个标签
                    Elements es = PageParserTool.select(page,"a");


                    //将已经访问过的链接放入已访问的链接中；
                    Links.addVisitedUrlSet(url);

                    //得到超链接
                    // Set<String> links = PageParserTool.getLinks(page,"div.MeinvTuPianBox img");
                    PageParserTool.getImgBigLists(page,".articleV2Body a",cate,p,ImgActivity.this);



                }catch (IOException e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        pd1.dismiss();
                        //refresh(category);
                        List<ImgBig> imgBigList= where("cate='"+category+"' and "+"page="+p+"").find(ImgBig.class);
                        if ( imgBigList!=null&&imgBigList.size()>0) {
                            Glide.with(getApplicationContext()).load(imgBigList.get(0).getLink()).into(iv);
                        }
                        else
                        {
                            Toast.makeText(ImgActivity.this, "无更多内容", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }).start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //继承了Activity的onTouchEvent方法，直接监听点击事件
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            //当手指按下的时候
            x1 = event.getX();
            y1 = event.getY();
        }
        if(event.getAction() == MotionEvent.ACTION_UP) {
            //当手指离开的时候
            x2 = event.getX();
            y2 = event.getY();
            if(y1 - y2 > 50) {
                Toast.makeText(ImgActivity.this, "向上滑", Toast.LENGTH_SHORT).show();
            } else if(y2 - y1 > 50) {
                Toast.makeText(ImgActivity.this, "向下滑", Toast.LENGTH_SHORT).show();
            } else if(x1 - x2 > 50) {
                p=p+1;
                List<ImgBig> imgBigList= where("cate='"+category+"' and "+"page="+p+"").find(ImgBig.class);

                if ( imgBigList==null||imgBigList.size()==0) {
                    pd1 = ProgressDialog.show(this, "提示", "正在加载中");


                    crawling(new String[]{"http://www.mtyoyo.com"+category.split("\\.")[0]+"_"+p+".html"},category,p);

                }
                else {

                    Glide.with(getApplicationContext()).load(imgBigList.get(0).getLink()).into(iv);
                }

            } else if(x2 - x1 > 50) {


                Toast.makeText(ImgActivity.this, "向右滑", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onTouchEvent(event);
    }


}
