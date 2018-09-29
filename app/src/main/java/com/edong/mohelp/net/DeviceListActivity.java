package com.edong.mohelp.net;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.edong.mohelp.MainActivity;
import com.edong.mohelp.R;
import com.edong.mohelp.config.NoticeActivity;
import com.edong.mohelp.config.WifiPasswordActivity;

public class DeviceListActivity extends AppCompatActivity {


    private ListView listView;
    private DeviceAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        listView = findViewById(R.id.list_view);
        adapter = new DeviceAdapter(this,R.layout.item_list_device,Connect.getDevices());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BindUtil.setBindDevice(Connect.getDevices().get(i));
                Connect.setData(Connect.getDevices().get(i));
                onBackPressed();
            }
        });
    }

}
