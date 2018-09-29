package com.edong.mohelp.config;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.edong.mohelp.MainActivity;
import com.edong.mohelp.R;
import com.edong.mohelp.app.App;
import com.edong.mohelp.config.util.ConnectUtil;
import com.edong.mohelp.config.util.WifiUtil;

import java.util.List;

public class WifiPasswordActivity extends AppCompatActivity {

    private LinearLayout wifiPasswordLinear;
    private RelativeLayout wifiNameRelative;
    private ImageView imageView;
    private TextView wifiName;
    private EditText passWord;
    private Button nextBt;

    private WifiManager mWifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ConnectUtil.init(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_password);
        wifiPasswordLinear = findViewById(R.id.wifi_password_linear);
        wifiNameRelative = findViewById(R.id.wifi_name_relative);
        wifiName = findViewById(R.id.wifi_name);
        passWord = findViewById(R.id.wifi_password);
        imageView = findViewById(R.id.image_view);
        nextBt = findViewById(R.id.next_button);

        mWifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);


//        Intent intent = getIntent();
//        wifiSelect(intent.getStringExtra("result"));
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (ContextCompat.checkSelfPermission(WifiPasswordActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(WifiPasswordActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }else {
//            Toast.makeText(MainActivity.this,"权限申请成功",Toast.LENGTH_LONG).show();
            init();
        }
    }
    public  void init(){

        if(WifiUtil.getWifiStatus() != WifiManager.WIFI_STATE_ENABLED) {
            WifiUtil.wifiOpen();
            WifiUtil.startScan();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            init();
                        }
                    });

                }
            }).start();
        } else {
//            if (wifiName.getText().toString().equals(getResources().getString(R.string.default_wifi_name))) {
                List<ScanResult> scanResults = WifiUtil.getScanResults();
                if (scanResults != null && scanResults.size() > 0) {
                    String lastSeleteWifi = getLastSelectWifi(scanResults);
                    if (!lastSeleteWifi.equals("")) {
                        wifiName.setText(lastSeleteWifi);
                        String password = getPassword(lastSeleteWifi);
                        if (password != null && !password.equals("")) {
                            passWord.setText(password);
                        }else {
                            passWord.setText("");
                        }
                    } else {
                        wifiName.setText(scanResults.get(0).SSID);
                    }
                }
//            }
        }
        setListener();
    }

    private long lastTouchNextTime = 0;
    private void nextPressed(){
        String ssid = wifiName.getText().toString();
        String pword = passWord.getText().toString();
        if (ssid.equals(getResources().getString(R.string.default_wifi_name))){
            Toast.makeText(WifiPasswordActivity.this,"请选择wifi",Toast.LENGTH_SHORT).show();
            return;
        }
        if (pword.equals("")&&System.currentTimeMillis()-lastTouchNextTime>2000){
            lastTouchNextTime = System.currentTimeMillis();
            Toast.makeText(WifiPasswordActivity.this,"密码为空，再次点击进入下一步！",Toast.LENGTH_LONG).show();
        }else {
            ConnectUtil.setSsid(ssid);
            ConnectUtil.setPassword(pword);
            setPassword(ssid,pword);
            if (WifiUtil.isWifiConnect(WifiPasswordActivity.this)){
                startNoticeActivity();
            }else {
//                        Log.e("edong","conncetWifi");
                Toast.makeText(WifiPasswordActivity.this,"正在连接WiFi请稍后……",Toast.LENGTH_SHORT).show();
                ConnectUtil.conncetWifi(mWifiManager);
//                isNeedToNoticeActivity = true;
            }

        }
    }
    private void wifiSelect(String ssid){
        Log.e("edong",""+ssid);
        if (ssid == null){
            return;
        }
        wifiName.setText(ssid);
        setLastSelectWifi(ssid);
        String password = getPassword(ssid);
        if (password != null ) {
            passWord.setText(password);
        }
    }
    private void setListener(){
        wifiNameRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(WifiPasswordActivity.this, WifiListActivity.class);
                if (Build.VERSION.SDK_INT<21){
                    startActivity(intent);
                }else {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(WifiPasswordActivity.this, imageView, "image").toBundle());
                }

            }
        });
        nextBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextPressed();
            }
        });
    }

    private void startNoticeActivity(){
        Intent intent = new Intent(WifiPasswordActivity.this,NoticeActivity.class);
        startActivity(intent);

    }

//    @Override
//    public void onBackPressed(){
//        Intent intent = new Intent(WifiPasswordActivity.this, MainActivity.class);
//        startActivity(intent);
//    }

    public String getPassword(String ssid){
        SharedPreferences preferences = getSharedPreferences("edong_config_password", Context.MODE_PRIVATE);
        return preferences.getString(ssid,"");
    }
    public void setPassword(String ssid,String password){
        SharedPreferences preferences = getSharedPreferences("edong_config_password", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ssid,password);
        editor.apply();
    }
    public static void setLastSelectWifi(String ssid){
        SharedPreferences preferences = App.getContext().getSharedPreferences("edong_config_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("last_select_wifi",ssid);
        editor.apply();
    }
    public String getLastSelectWifi(List<ScanResult> scanResults){
        SharedPreferences preferences = getSharedPreferences("edong_config_preferences", Context.MODE_PRIVATE);
        String ssid = preferences.getString("last_select_wifi","");
        Log.e("edong",""+ssid);
        if (!ssid.equals("")){
            for (ScanResult scanResult:scanResults){
                if (scanResult.SSID.equals(ssid)){
                    return ssid;
                }
            }
        }
        return "";
    }
}
