package com.edong.mohelp.music;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.edong.mohelp.R;
import com.edong.mohelp.net.BindUtil;
import com.edong.mohelp.net.Connect;
import com.edong.mohelp.net.DeviceAdapter;
import com.edong.mohelp.net.Event;
import com.edong.mohelp.net.RequestUtil;
import com.google.gson.Gson;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MusicListActivity extends AppCompatActivity {


    private CircleImageView imageView;
    private TextView typeName;
    private ListView listView;
    private MusicAdapter adapter;
    private List<Music> musicList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);

        imageView = findViewById(R.id.circle_image_view);
        typeName = findViewById(R.id.type_name);
        listView = findViewById(R.id.list_view);
        adapter = new MusicAdapter(this,R.layout.item_list_music, musicList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MusicEvent event = new MusicEvent("Play");
                event.setData(musicList);
                event.setPosition(i);
                new RequestUtil().request(event, new RequestUtil.HandleResult() {
                    @Override
                    public void resultHandle(String result) {
                        if (result.length() > 6000) {
                            Log.d("json", "json:" + result.substring(0, 3000));
                            Log.d("json", "json:" + result.substring(3000, 6000));
                            Log.d("json", "json:" + result.substring(6000, result.length()));
                        } else if (result.length() > 3000) {
                            Log.d("json", "json:" + result.substring(0, 3000));
                            Log.d("json", "json:" + result.substring(3000, result.length()));
                        } else {
                            Log.d("json", "json:" + result);
                        }
                    }
                });
            }
        });

        initView();



    }

    private void initView(){
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        if (type!=null){
            requestInfo(type);
            typeName.setText(type);
            if (type.equals(getResources().getString(R.string.popular))){
                imageView.setImageResource(R.drawable.ic_music_style_popular);
            }else if (type.equals(getResources().getString(R.string.classical))){
                imageView.setImageResource(R.drawable.ic_music_style_classical);
            }else if (type.equals(getResources().getString(R.string.rap))){
                imageView.setImageResource(R.drawable.ic_music_style_rap);
            }else if (type.equals(getResources().getString(R.string.electronic))){
                imageView.setImageResource(R.drawable.ic_music_style_electronic);
            }else if (type.equals(getResources().getString(R.string.rock))){
                imageView.setImageResource(R.drawable.ic_music_style_rock);
            }else if (type.equals(getResources().getString(R.string.classic))){
                imageView.setImageResource(R.drawable.ic_music_style_classic);
            }else if (type.equals(getResources().getString(R.string.children))){
                imageView.setImageResource(R.drawable.ic_music_style_children);
            }else if (type.equals(getResources().getString(R.string.folk))){
                imageView.setImageResource(R.drawable.ic_music_style_folk);
            }else if (type.equals(getResources().getString(R.string.rap))){
                imageView.setImageResource(R.drawable.ic_music_style_rap);
            }else if (type.equals(getResources().getString(R.string.zhou_jie_lun))){
                imageView.setImageResource(R.drawable.ic_music_artist_zhou_jie_lun);
            }else if (type.equals(getResources().getString(R.string.sun_yan_zi))){
                imageView.setImageResource(R.drawable.ic_music_artist_sun_yan_zi);
            }else if (type.equals(getResources().getString(R.string.xue_zhi_qian))){
                imageView.setImageResource(R.drawable.ic_music_artist_xue_zhi_qian);
            }else if (type.equals(getResources().getString(R.string.tf_boy))){
                imageView.setImageResource(R.drawable.ic_music_artist_tf_boy);
            }else if (type.equals(getResources().getString(R.string.justin_bieber))){
                imageView.setImageResource(R.drawable.ic_music_artist_justin_bieber);
            }else if (type.equals(getResources().getString(R.string.bruno_mars))){
                imageView.setImageResource(R.drawable.ic_music_artist_bruno_mars);
            }else if (type.equals(getResources().getString(R.string.sia))){
                imageView.setImageResource(R.drawable.ic_music_artist_sia);
            }else if (type.equals(getResources().getString(R.string.taylor_swift))){
                imageView.setImageResource(R.drawable.ic_music_artist_taylor_swift);
            }
        }


    }

    private void requestInfo(String type){
        Log.e("edong","type="+type);
        Event event = new Event("RequestAI","MusicList","播放"+type+"音乐");
        new RequestUtil().request(event, new RequestUtil.HandleResult() {
            @Override
            public void resultHandle(String result) {
                if (result.equals("null")){
                    Toast.makeText(MusicListActivity.this,"获取失败！",Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    List<Music> list = MusicUtil.format(result);
                    if (list!=null){
                        musicList.clear();
                        musicList.addAll(list);
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
