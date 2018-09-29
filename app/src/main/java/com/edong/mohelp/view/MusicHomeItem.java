package com.edong.mohelp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edong.mohelp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class MusicHomeItem extends LinearLayout {

    private CircleImageView imageView;
    private TextView textView;
    private String tag;

    public MusicHomeItem(Context context) {
        this(context, null);
    }

    public MusicHomeItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.item_music_home, this);
        imageView =  findViewById(R.id.image_view);
        textView = findViewById(R.id.text_view);
        tag = (String)getTag();
        initView();
    }

    private void initView(){
        if (tag.contains(getResources().getString(R.string.popular))){
            imageView.setImageResource(R.drawable.ic_music_style_popular);
            textView.setText(R.string.popular);
        }else if (tag.contains(getResources().getString(R.string.classical))){
            imageView.setImageResource(R.drawable.ic_music_style_classical);
            textView.setText(R.string.classical);
        }else if (tag.contains(getResources().getString(R.string.electronic))){
            imageView.setImageResource(R.drawable.ic_music_style_electronic);
            textView.setText(R.string.electronic);
        }else if (tag.contains(getResources().getString(R.string.rock))){
            imageView.setImageResource(R.drawable.ic_music_style_rock);
            textView.setText(R.string.rock);
        }else if (tag.contains(getResources().getString(R.string.classic))){
            imageView.setImageResource(R.drawable.ic_music_style_classic);
            textView.setText(R.string.classic);
        }else if (tag.contains(getResources().getString(R.string.children))){
            imageView.setImageResource(R.drawable.ic_music_style_children);
            textView.setText(R.string.children);
        }else if (tag.contains(getResources().getString(R.string.folk))){
            imageView.setImageResource(R.drawable.ic_music_style_folk);
            textView.setText(R.string.folk);
        }else if (tag.contains(getResources().getString(R.string.rap))){
            imageView.setImageResource(R.drawable.ic_music_style_rap);
            textView.setText(R.string.rap);
        }else if (tag.contains(getResources().getString(R.string.zhou_jie_lun))){
            imageView.setImageResource(R.drawable.ic_music_artist_zhou_jie_lun);
            textView.setText(R.string.zhou_jie_lun);
        }else if (tag.contains(getResources().getString(R.string.sun_yan_zi))){
            imageView.setImageResource(R.drawable.ic_music_artist_sun_yan_zi);
            textView.setText(R.string.sun_yan_zi);
        }else if (tag.contains(getResources().getString(R.string.xue_zhi_qian))){
            imageView.setImageResource(R.drawable.ic_music_artist_xue_zhi_qian);
            textView.setText(R.string.xue_zhi_qian);
        }else if (tag.contains(getResources().getString(R.string.tf_boy))){
            imageView.setImageResource(R.drawable.ic_music_artist_tf_boy);
            textView.setText(R.string.tf_boy);
        }else if (tag.contains(getResources().getString(R.string.justin_bieber))){
            imageView.setImageResource(R.drawable.ic_music_artist_justin_bieber);
            textView.setText(R.string.justin_bieber);
        }else if (tag.contains(getResources().getString(R.string.bruno_mars))){
            imageView.setImageResource(R.drawable.ic_music_artist_bruno_mars);
            textView.setText(R.string.bruno_mars);
        }else if (tag.contains(getResources().getString(R.string.sia))){
            imageView.setImageResource(R.drawable.ic_music_artist_sia);
            textView.setText(R.string.sia);
        }else if (tag.contains(getResources().getString(R.string.taylor_swift))){
            imageView.setImageResource(R.drawable.ic_music_artist_taylor_swift);
            textView.setText(R.string.taylor_swift);
        }

    }
}
