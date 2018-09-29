package com.edong.mohelp;

import android.app.ActivityOptions;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.edong.mohelp.config.WifiListActivity;
import com.edong.mohelp.config.WifiPasswordActivity;
import com.edong.mohelp.music.MusicHomeFrag;
import com.edong.mohelp.music.MusicListActivity;
import com.edong.mohelp.net.BindUtil;
import com.edong.mohelp.net.Connect;
import com.edong.mohelp.net.Device;
import com.edong.mohelp.net.Event;
import com.edong.mohelp.net.RequestUtil;
import com.edong.mohelp.net.SettingsActivity;
import com.edong.mohelp.service.SerConnect;
import com.edong.mohelp.skill.Skill;
import com.edong.mohelp.skill.SkillAdapter;
import com.edong.mohelp.skill.SkillUtil;
import com.edong.mohelp.view.BottomItem;
import com.edong.mohelp.view.MusicHomeItem;
import com.edong.mohelp.view.MyFragmentPagerAdapter;
import com.edong.mohelp.view.ScrollListView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private View musicPage,skillPage,networkPage;
    private LinearLayout settingLinear,deviceDisconnectLinear;

    private TextView titleText;

    private BottomItem musicItem,skillItem,networkItem;

    private MusicHomeItem popularItem,classicalItem,electronicItem,rockItem,
            classicItem,childrenItem,folkItem,rapItem;
    private MusicHomeItem zhou_jie_lunItem,sun_yan_ziItem,xue_zhi_qianItem,tf_boyItem,
            justin_bieberItem,bruno_marsItem,siaItem,taylor_swiftItem;

    private ScrollListView listView;
    private SkillAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        musicPage = findViewById(R.id.music_home);
        skillPage = findViewById(R.id.skill_home);
        networkPage = findViewById(R.id.config_login_home);

        titleText = findViewById(R.id.title_text);
        settingLinear = findViewById(R.id.setting_linear);
        deviceDisconnectLinear = findViewById(R.id.device_disconnect_linear);
        musicItem = findViewById(R.id.music);
        skillItem = findViewById(R.id.skill);
        networkItem = findViewById(R.id.net_work);
        musicItem.setColor(BottomItem.GREEN);

        popularItem = findViewById(R.id.style_popular);
        classicalItem = findViewById(R.id.style_classical);
        electronicItem = findViewById(R.id.style_electronic);
        rockItem = findViewById(R.id.style_rock);
        classicItem = findViewById(R.id.style_classic);
        childrenItem = findViewById(R.id.style_children);
        folkItem = findViewById(R.id.style_folk);
        rapItem = findViewById(R.id.style_rap);

        zhou_jie_lunItem = findViewById(R.id.zhou_jie_lun);
        sun_yan_ziItem = findViewById(R.id.sun_yan_zi);
        xue_zhi_qianItem = findViewById(R.id.xue_zhi_qian);
        tf_boyItem = findViewById(R.id.tf_boy);
        justin_bieberItem = findViewById(R.id.justin_bieber);
        bruno_marsItem = findViewById(R.id.bruno_mars);
        siaItem = findViewById(R.id.sia);
        taylor_swiftItem = findViewById(R.id.taylor_swift);

        listView = findViewById(R.id.list_view);
        adapter = new SkillAdapter(MainActivity.this,R.layout.item_list_skill, SkillUtil.getSkills(MainActivity.this));
        listView.setAdapter(adapter);


        setListener();

        register();
        if (!Connect.isConnect) {
            Connect.onBrodacastSend(this, mHandler);
        }
    }

    private void setListener(){
        musicItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicItem.setColor(BottomItem.GREEN);
                skillItem.setColor(BottomItem.WHITE);
                networkItem.setColor(BottomItem.WHITE);
                titleText.setText(getString(R.string.music));
                musicPage.setVisibility(View.VISIBLE);
                skillPage.setVisibility(View.GONE);
                networkPage.setVisibility(View.GONE);
            }
        });
        skillItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicItem.setColor(BottomItem.WHITE);
                skillItem.setColor(BottomItem.GREEN);
                networkItem.setColor(BottomItem.WHITE);
                titleText.setText(getString(R.string.skill_center));
                skillPage.setVisibility(View.VISIBLE);
                musicPage.setVisibility(View.GONE);
                networkPage.setVisibility(View.GONE);
            }
        });
        networkItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicItem.setColor(BottomItem.WHITE);
                skillItem.setColor(BottomItem.WHITE);
                networkItem.setColor(BottomItem.GREEN);
                titleText.setText(getString(R.string.net_connect_tip));
                musicPage.setVisibility(View.GONE);
                skillPage.setVisibility(View.GONE);
                networkPage.setVisibility(View.VISIBLE);
            }
        });
        settingLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        popularItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startMusicListActivity(getString(R.string.popular),popularItem);
            }
        });
        classicalItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMusicListActivity(getString(R.string.classical),classicalItem);
            }
        });
        electronicItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMusicListActivity(getString(R.string.electronic),electronicItem);
            }
        });
        rockItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMusicListActivity(getString(R.string.rock),rockItem);
            }
        });
        classicItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMusicListActivity(getString(R.string.classic),classicItem);
            }
        });
        childrenItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMusicListActivity(getString(R.string.children),childrenItem);
            }
        });
        folkItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMusicListActivity(getString(R.string.folk),folkItem);
            }
        });
        rapItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMusicListActivity(getString(R.string.rap),rapItem);
            }
        });

        zhou_jie_lunItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMusicListActivity(getString(R.string.zhou_jie_lun),zhou_jie_lunItem);
            }
        });
        sun_yan_ziItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMusicListActivity(getString(R.string.sun_yan_zi),sun_yan_ziItem);
            }
        });
        xue_zhi_qianItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMusicListActivity(getString(R.string.xue_zhi_qian),xue_zhi_qianItem);
            }
        });
        tf_boyItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMusicListActivity(getString(R.string.tf_boy),tf_boyItem);
            }
        });
        justin_bieberItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMusicListActivity(getString(R.string.justin_bieber),justin_bieberItem);
            }
        });
        bruno_marsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMusicListActivity(getString(R.string.bruno_mars),bruno_marsItem);
            }
        });
        siaItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMusicListActivity(getString(R.string.sia),siaItem);
            }
        });
        taylor_swiftItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMusicListActivity(getString(R.string.taylor_swift),taylor_swiftItem);
            }
        });

        deviceDisconnectLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Connect.onBrodacastSend(MainActivity.this,mHandler);
            }
        });


    }

    @Override
    protected void onResume(){
        super.onResume();
        if (!Connect.isConnect) {
            deviceDisconnectLinear.setVisibility(View.VISIBLE);
        }else {
            deviceDisconnectLinear.setVisibility(View.GONE);
            SerConnect.bindService(MainActivity.this);
        }
    }

    private void startMusicListActivity(String type,View view){
        Intent intent = new Intent(MainActivity.this, MusicListActivity.class);
        intent.putExtra("type",type);
        if (Build.VERSION.SDK_INT<21){
            startActivity(intent);
        }else {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, view, "musicItem").toBundle());
        }
    }

    private void register(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("connect_failed");
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(receiver,intentFilter);

    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.e("edong","action="+action);
            if (action.equals("connect_failed")){
                Connect.onBrodacastSend(MainActivity.this, mHandler);
            }else if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
                NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
//                Log.e("edong","wifi,info.isConnected()="+info.isConnected());
                if (info.isConnected()&&!Connect.isConnect){
                    Connect.onBrodacastSend(MainActivity.this, mHandler);
                }
            }
        }
    };
    Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case 0:
                    deviceDisconnectLinear.setVisibility(View.GONE);
                    SerConnect.bindService(MainActivity.this);
//                    Toast.makeText(MainActivity.this, "连接成功！", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    deviceDisconnectLinear.setVisibility(View.VISIBLE);
                    SerConnect.dismissFloatView();
//                    Toast.makeText(MainActivity.this, "连接失败！", Toast.LENGTH_SHORT).show();

                    break;
                default:
                    break;
            }

        }
    };

    protected void onDestroy(){
        super.onDestroy();
        try{
            unregisterReceiver(receiver);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed(){
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);
//        SerConnect.dismissFloatView();
    }
}
