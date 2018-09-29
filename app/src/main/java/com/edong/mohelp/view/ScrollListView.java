package com.edong.mohelp.view;

import android.content.Context;
import android.widget.ListView;

public class ScrollListView extends ListView {

    public ScrollListView(Context context) {
        super(context);
    }

    public ScrollListView(Context context, android.util.AttributeSet attrs){
        super(context, attrs);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }
}
