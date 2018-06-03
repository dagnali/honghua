package com.qianpic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.qianpic.adapter.ImgAdapter;
import com.qianpic.db.Img;
import com.qianpic.util.Links;
import com.qianpic.util.Page;
import com.qianpic.util.PageParserTool;

import org.jsoup.select.Elements;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private final int PAGE_COUNT = 10;
    private GridLayoutManager mLayoutManager;
    //private List<Img> imgList;
    ImageView imgBing;
    ProgressDialog pd1;
    int p=1;
    String category;
    private int lastVisibleItem = 0;
    private ImgAdapter adapter;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=getIntent();
        category=intent.getStringExtra("category");

        findView();
       //DataSupport.deleteAll(Img.class);
        //imgList = DataSupport.findAll(Img.class);
        initRefreshLayout();

       // imgList = DataSupport.findAll(Img.class);
        initRecyclerView();

    }

    private void initRefreshLayout() {
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setOnRefreshListener(this);
    }

    private void initRecyclerView() {
        List<Img> imgList= DataSupport.where("cate = '"+category+"'").find(Img.class);

        if ( imgList==null||imgList.size()==0) {
            pd1 = ProgressDialog.show(this, "提示", "正在加载中");

            crawling(new String[]{"http://www.mtyoyo.com"+category},category,p);

        }
         adapter = new ImgAdapter(getApplicationContext(),imgList,true);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        // LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //
        mLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(mLayoutManager);


       // StaggeredGridLayoutManager mLayoutManager=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
       // recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(adapter);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (adapter.isFadeTips() == false && lastVisibleItem + 1 == adapter.getItemCount()) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView();
                            }
                        }, 500);
                    }

                    if (adapter.isFadeTips() == true && lastVisibleItem + 2 == adapter.getItemCount()) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView();
                            }
                        }, 500);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    public  void refresh(String category) {
       finish();
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.putExtra("category",category);
        startActivity(intent);
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

    /**
     * 使用种子初始化 URL 队列
     *
     * @param seeds 种子 URL
     * @return
     */
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
                    PageParserTool.getImgLists(page,"div.MeinvTuPianBox :eq(0) a",cate,p,MainActivity.this);



                }catch (IOException e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        pd1.dismiss();
                        //refresh(category);
                        List<Img> a=DataSupport.where("cate='"+category+"' and "+"page="+p+"").find(Img.class);
                        if (a.size() > 0) {
                            adapter.updateList(a, true);
                        } else {
                            adapter.updateList(null, false);
                        }
                    }
                });

            }
        }).start();
    }


    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        adapter.resetDatas();
        updateRecyclerView();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        }, 1000);
    }

    private void updateRecyclerView() {

        p=p+1;
        Log.d("aa", p+"");
        List<Img> newDatas = getDatas(p);
        if (newDatas.size() > 0) {
            adapter.updateList(newDatas, true);
        } else {
            adapter.updateList(null, false);
        }
    }

    public List<Img> getDatas(int page) {
        List<Img> a=DataSupport.where("cate='"+category+"' and "+"page="+p+"").find(Img.class);
        if ( a==null||a.size()==0) {
            pd1 = ProgressDialog.show(this, "提示", "正在加载中");

            crawling(new String[]{"http://www.mtyoyo.com"+category+"index_"+page+".html"},category,page);

        }
        return a;
    }


    private void findView() {
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);

    }
}
