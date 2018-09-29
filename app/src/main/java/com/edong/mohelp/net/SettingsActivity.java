package com.edong.mohelp.net;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.edong.mohelp.MainActivity;
import com.edong.mohelp.R;
import com.edong.mohelp.config.WifiPasswordActivity;
import com.edong.mohelp.service.SerConnect;

public class SettingsActivity extends AppCompatActivity {

    private LinearLayout myDeviceLinear,unbindLinear,reConnectLinear;
    private TextView deviceName;
    private TextView unConnectText,unbindText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        myDeviceLinear = findViewById(R.id.my_device_linear);
        unbindLinear = findViewById(R.id.un_bind_linear);
        reConnectLinear = findViewById(R.id.re_connect_wifi_linear);
        deviceName = findViewById(R.id.device_name);
        unbindText = findViewById(R.id.un_bind_text);
        unConnectText = findViewById(R.id.un_connect_text);

        setListener();
    }

    private void setListener(){
        myDeviceLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Connect.isConnect&&!deviceName.getText().toString().equals("未绑定")) {
                    Intent intent = new Intent(SettingsActivity.this, DeviceActivity.class);
                    startActivity(intent);
                }
            }
        });
        unbindLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (unbindText.getText().toString().equals("解除绑定")) {
                    showDialog();
                }else {
                    Connect.onBrodacastSend(SettingsActivity.this,null);
                }
            }
        });
        reConnectLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, WifiPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        Device device = BindUtil.getBindDevice();
        if (device == null) {
            deviceName.setText("未绑定");
            unbindText.setText("绑定设备");
            unConnectText.setVisibility(View.GONE);
        }else {
            deviceName.setText(device.getProduct());
            unbindText.setText("解除绑定");
            if (Connect.isConnect){
                unConnectText.setVisibility(View.GONE);
            }else {
                unConnectText.setVisibility(View.VISIBLE);
            }
        }
    }

    private void showDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("确定解除绑定？")
//                .setMessage("有多个按钮")
//                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        BindUtil.setBindDevice(new Device("","",""));
                        deviceName.setText("未绑定");
                        unConnectText.setVisibility(View.GONE);
                        unbindText.setText("绑定设备");
                        Connect.isConnect = false;
                        Toast.makeText(SettingsActivity.this,"解绑成功！",Toast.LENGTH_SHORT).show();
                        SerConnect.dismissFloatView();
                    }
                })

                .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Toast.makeText(SettingsActivity.this, "这是取消按钮", Toast.LENGTH_SHORT).show();
                    }
                })
//                .setNeutralButton("普通按钮", new DialogInterface.OnClickListener() {//添加普通按钮
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Toast.makeText(SettingsActivity.this, "这是普通按钮按钮", Toast.LENGTH_SHORT).show();
//                    }
//                })
                .create();
        alertDialog.show();
    }


}
