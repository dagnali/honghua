package com.qianpic;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

/**
 * Created by admin on 2018/3/9.
 */

public class MyAppliation extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        LitePal.initialize(context);
    }

    public static Context getContext()
    {
        return context;
    }
}
