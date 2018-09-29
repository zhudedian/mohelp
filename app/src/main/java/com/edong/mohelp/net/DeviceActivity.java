package com.edong.mohelp.net;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.edong.mohelp.R;
import com.edong.mohelp.music.Music;
import com.edong.mohelp.music.MusicListActivity;
import com.edong.mohelp.music.MusicUtil;
import com.google.gson.Gson;

import org.json.JSONException;

import java.util.List;

public class DeviceActivity extends AppCompatActivity {

    private TextView titleDevice,titleWifi,titleBlue,titleIp,titleCpu;
    private TextView deviceName,deviceWifi,deviceBlue,deviceIp,deviceCpu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        titleDevice = findViewById(R.id.title_device);
        titleWifi = findViewById(R.id.title_wifi);
        titleBlue = findViewById(R.id.title_blue);
        titleIp = findViewById(R.id.title_ip);
        titleCpu = findViewById(R.id.title_cpu);

        deviceName = findViewById(R.id.device_name);
        deviceWifi = findViewById(R.id.device_wifi);
        deviceBlue = findViewById(R.id.device_bluetooth);
        deviceIp = findViewById(R.id.device_ip);
        deviceCpu = findViewById(R.id.device_cpu);
        initView();
        requestInfo();
    }

    private void initView(){
//        TextUtil.autoSplitText(titleDevice,"设备名称：");
//        TextUtil.autoSplitText(titleWifi,"设备名称：");
//        TextUtil.autoSplitText(titleBlue,"设备名称：");
//        TextUtil.autoSplitText(titleIp,"设备名称：");
//        TextUtil.autoSplitText(titleCpu,"设备名称：");
    }

    private void requestInfo(){

        Event event = new Event("DeviceInfo","device","");
        new RequestUtil().request(event, new RequestUtil.HandleResult() {
            @Override
            public void resultHandle(String result) {
                if (result.equals("null")){
                    Toast.makeText(DeviceActivity.this,"获取失败！",Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    Device device = new Gson().fromJson(result,Device.class);
                    deviceName.setText(device.getProduct());
                    deviceWifi.setText(device.getWifi());
                    deviceBlue.setText(device.getBluetooth());
                    deviceIp.setText(device.getIp());
                    deviceCpu.setText(device.getHardware());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
