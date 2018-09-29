package com.edong.mohelp.service;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.edong.mohelp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class NowPlayView extends ViewGroup {

    private Paint mPaint;

    private int width, height;

    private CircleImageView circleImageView;

    private float sweepAngle = 0;


    private ValueAnimator animator;

    public NowPlayView(Context context) {
        this(context, null);
    }

    public NowPlayView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NowPlayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(getResources().getColor(R.color.orange));
        mPaint.setAlpha(255);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(8);
        mPaint.setStrokeCap(Paint.Cap.ROUND);//没有
        mPaint.setStrokeJoin(Paint.Join.BEVEL);//直线

        circleImageView = new CircleImageView(context);
        addView(circleImageView);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w ;
        height = h;
        circleImageView.layout((int)(width*0.06), (int)(height*0.06), (int)(width*0.94), (int)(height*0.94));

        Glide.with(getContext()).load(R.drawable.ic_music_defult).into(circleImageView);
//        setBackgroundColor(getResources().getColor(R.color.divider_music_list));
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();


        mPaint.setStyle(Paint.Style.STROKE);
        RectF oval = new RectF( width*0.05f, height*0.05f, width*0.95f, height*0.95f);
        canvas.drawArc(oval,-90,sweepAngle,false,mPaint);


        canvas.restore();
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (i==0) {
                childView.layout((int)(width*0.06), (int)(height*0.06), (int)(width*0.94), (int)(height*0.94));
            }
        }

    }


    public void setIcon(String url){
        Glide.with(getContext()).load(url).error(R.drawable.ic_music_defult).into(circleImageView);
    }
    public void setProgress(long offset,long total){
        startAnimator((float) offset/total*360,total-offset);
    }

    public synchronized void cancelAnimator(){
        if (animator!=null){
            animator.cancel();
        }
    }

    public synchronized void startAnimator(float start,long time) {
//        Log.e("edong","start="+start+",time="+time);
        if (animator!=null){
            animator.cancel();
        }
        animator = ValueAnimator.ofFloat(start, 360);
        animator.setDuration(time);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                /**
                 * 通过这样一个监听事件，我们就可以获取
                 * 到ValueAnimator每一步所产生的值。
                 *
                 * 通过调用getAnimatedValue()获取到每个时间因子所产生的Value。
                 * */
                sweepAngle = (float) animation.getAnimatedValue();

                postInvalidate();
            }
        });

        animator.start();

    }
}
