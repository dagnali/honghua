package com.qianpic.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by admin on 2018/3/5.
 */

public class ImageViewUtil {
    public static void matchAll(Context context, ImageView imageView) {
        int width, height;//ImageView调整后的宽高
        //获取屏幕宽高
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        int sWidth = metrics.widthPixels;
        int sHeight = metrics.heightPixels;
        //获取图片宽高
        Drawable drawable = imageView.getDrawable();
        int dWidth = drawable.getIntrinsicWidth();
        int dHeight = drawable.getIntrinsicHeight();

        //屏幕宽高比,一定要先把其中一个转为float
        float sScale = (float) sWidth / sHeight;
        //图片宽高比
        float dScale = (float) dWidth / dHeight;
        /*
        缩放比
        如果sScale>dScale，表示在高相等的情况下，控屏幕比较宽，这时候要适应高度，缩放比就是两则的高之比，图片宽度用缩放比计算
        如果sScale<dScale，表示在高相等的情况下，图片比较宽，这时候要适应宽度，缩放比就是两则的宽之比，图片高度用缩放比计算
         */
        float scale = 1.0f;
        if (sScale > dScale) {
            scale = (float) dHeight / sHeight;
            height = sHeight;//图片高度就是屏幕高度
            width = (int) (dWidth * scale);//按照缩放比算出图片缩放后的宽度
        } else if (sScale < dScale) {
            scale = (float) dWidth / sWidth;
            width = sWidth;
            height = (int) (dHeight / scale);//这里用除
        } else {
            //最后两者刚好比例相同，其实可以不用写，刚好充满
            width = sWidth;
            height = sHeight;
        }
        //重设ImageView宽高
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        imageView.setLayoutParams(params);
        //这样就获得了一个既适应屏幕有适应内部图片的ImageView，不用再纠结该给ImageView设定什么尺寸合适了
    }
}
