package com.edong.mohelp.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.edong.mohelp.app.App;

public class FloatView {

    private NowPlayView mFloatView;
    private WindowManager.LayoutParams mLayoutParams;
    private WindowManager mWindowManager;


    public void createView() {
        mWindowManager = (WindowManager) App.getContext().getSystemService(Context.WINDOW_SERVICE);
        int displayHeight = mWindowManager.getDefaultDisplay().getHeight();
        int displayWidth = mWindowManager.getDefaultDisplay().getWidth();

        mFloatView = new NowPlayView(App.getContext());
        //mFloatView = mLayoutInflater.inflate(R.layout.mouse, null);
        //为View设置监听，以便处理用户的点击和拖动
//        mFloatView.setOnTouchListener(new MyOnTouchListener());
        /*为View设置参数*/
        mLayoutParams = new WindowManager.LayoutParams();
        //设置View默认的摆放位置
        mLayoutParams.gravity = Gravity.LEFT|Gravity.TOP ;
        //设置window type
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //设置背景为透明
        mLayoutParams.format = PixelFormat.RGBA_8888;
        //注意该属性的设置很重要，FLAG_NOT_FOCUSABLE使浮动窗口不获取焦点,若不设置该属性，屏幕的其它位置点击无效，应为它们无法获取焦点
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //设置视图的显示位置，通过WindowManager更新视图的位置其实就是改变(x,y)的值
        int mCurrentX = mLayoutParams.x = 1000;
        int mCurrentY = mLayoutParams.y = 180;
        //设置视图的宽、高
        mLayoutParams.width = 120;
        mLayoutParams.height = 120;
        //将视图添加到Window中
        mWindowManager.addView(mFloatView, mLayoutParams);
        mLayoutParams.x = mCurrentX;
        mLayoutParams.y = mCurrentY;
        mWindowManager.updateViewLayout(mFloatView, mLayoutParams);
//        mFloatView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MyApplication.getContext(), MainActivity.class);
//                MyApplication.getContext().startActivity(intent);
//            }
//        });
    }

    public void show(){
        mFloatView.setVisibility(View.VISIBLE);
    }

    public void dismiss(){
        mFloatView.setVisibility(View.GONE);
        mFloatView.cancelAnimator();
    }

    public void setIcon(String url){
        mFloatView.setIcon(url);
        show();
    }

    public void setProgress(long offset,long total){
        mFloatView.setProgress(offset,total);
    }
}
