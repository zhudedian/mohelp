package com.edong.mohelp.config;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.edong.mohelp.R;
import com.edong.mohelp.config.util.WifiAdapter;
import com.edong.mohelp.config.util.WifiUtil;

import java.util.List;

public class WifiListActivity extends AppCompatActivity {

    private ListView listView;
    private ImageView imageView;
    private WifiAdapter adapter;
    private List<ScanResult> scanResults ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_list);
        listView = findViewById(R.id.list_view);
        imageView = findViewById(R.id.image_view);

        handler.postDelayed(initRun,500);


    }
    Handler handler = new Handler(Looper.getMainLooper());
    Runnable initRun = new Runnable() {
        @Override
        public void run() {
            init();
        }
    };
    private void init(){
        scanResults = WifiUtil.getScanResults();
        adapter = new WifiAdapter(WifiListActivity.this,R.layout.item_list_wifi, scanResults);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("edong","result:"+scanResults.get(i).SSID);
//                Intent intent = new Intent(WifiListActivity.this, WifiPasswordActivity.class);
//                intent.putExtra("result",scanResults.get(i).SSID);
//                if (Build.VERSION.SDK_INT<21){
//                    startActivity(intent);
//                }else {
//                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(WifiListActivity.this, imageView, "image").toBundle());
//                }

                WifiPasswordActivity.setLastSelectWifi(scanResults.get(i).SSID);
                onBackPressed();
            }
        });
        register();
    }

    private void register() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(millsReceiver,intentFilter);
    }
    private BroadcastReceiver millsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            WifiUtil.startScan();
            if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int times = 6;
                        for (int i = 0; i < times; i++) {
                            getWifiList();
                            try {
                                Thread.sleep(60000 / times);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }else if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)){
                NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if (info.isConnected()&&scanResults.size() == 0) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (scanResults.size() == 0) {
                                getWifiList();
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).start();
                }
            }
        }
    };
    private void getWifiList(){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<ScanResult> list = WifiUtil.getScanResults();
                scanResults.clear();
                scanResults.addAll(list);
                adapter.notifyDataSetChanged();

            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unregister();
    }
    private void unregister(){
        try {
            unregisterReceiver(millsReceiver);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
