package com.edong.mohelp.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.StringDef;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edong.mohelp.R;

public class BottomItem extends LinearLayout {

    private ImageView imageView;
    private TextView textView;
    private String tag;
    public static int GREEN ;
    public static int WHITE = Color.WHITE;

    public BottomItem(Context context) {
        this(context, null);
    }

    public BottomItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        GREEN = getResources().getColor(R.color.green_text_color);
        LayoutInflater.from(context).inflate(R.layout.item_bottom, this);
        imageView =  findViewById(R.id.image_view);
        textView = findViewById(R.id.text_view);
        tag = (String)getTag();
        initView();
    }

    private void initView(){
        if (tag.contains(getResources().getString(R.string.music))){
            imageView.setImageResource(R.drawable.ic_bottom_music_green);
            textView.setText(R.string.music);
        }else if (tag.contains(getResources().getString(R.string.skill_center))){
            imageView.setImageResource(R.drawable.ic_bottom_skill_center_white);
            textView.setText(R.string.skill_center);
        }else if (tag.contains(getResources().getString(R.string.net_connect_tip))){
            imageView.setImageResource(R.drawable.ic_bottom_network_white);
            textView.setText(R.string.net_connect_tip);
        }
//        imageView.setColorFilter(getResources().getColor(R.color.green_text_color));
    }
    public void setColor(int color){
        imageView.setColorFilter(color);
        textView.setTextColor(color);
    }

}
