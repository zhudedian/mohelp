package com.edong.mohelp.config;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.edong.mohelp.R;
import com.edong.mohelp.config.util.ConnectUtil;

public class ConfigActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        startConfig();
    }

    private void startConfig(){
        ConnectUtil.setTreadMsgHandler(new MsgHandler());
        ConnectUtil.startConfig();
    }


    class MsgHandler extends Handler {
        @Override
        public void handleMessage(Message msg){
            switch (msg.what) {
                case 5:

                    finish();
                    break;
                default:
                    break;
            }
        }
    }
}
