package com.edong.mohelp.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.edong.mohelp.MainActivity;
import com.edong.mohelp.net.Connect;
import com.edong.mohelp.net.DownchannelUtil;
import com.edong.mohelp.net.socket.Online;

import org.json.JSONException;
import org.json.JSONObject;

public class MyService extends Service {

    private FloatView floatView;

    private MyBinder myBinder = new MyBinder();
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        floatView = new FloatView();
        floatView.createView();
        floatView.dismiss();
        startListener();

    }

    private void startListener(){
        Online.startListener(new Online.OnlineListener() {
            @Override
            public void onChange(int state) {
//                Log.e("edong","state="+state);
                if (state == -1){
//                    Toast.makeText(MyService.this, "音响被另一手机连接！", Toast.LENGTH_SHORT).show();
                    Connect.isConnect = false;
                }else if (state == 0){
                    Connect.isConnect = false;
//                    Toast.makeText(MyService.this, "已断开连接！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent("connect_failed");
                    sendBroadcast(intent);

                }else if (state == 1){
                    openDownchannel();
                }
            }
        });
    }



    private void openDownchannel(){
        if (!DownchannelUtil.isHasOpenDownchannel()){
            DownchannelUtil.openDownchannel(new DownchannelUtil.HandleResult() {
                @Override
                public void resultHandle(String result) {
                    Log.e("edong","result = "+result);
                    if (result.contains("header")){
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(result);
                            String namespace = jsonObject.getJSONObject("header").getString("namespace");
                            String name = jsonObject.getJSONObject("header").getString("name");
                            String message = jsonObject.getJSONObject("header").getString("message");
                            if (namespace.equals("MusicPlay")){
                                if (name.equals("Play")){
                                    String albumPic = jsonObject.getJSONObject("music").getString("albumPic");
                                    long offset = jsonObject.getLong("offsetInMilliseconds");
                                    long total = jsonObject.getLong("totalMillseconds");
                                    floatView.setProgress(offset,total);
                                    floatView.setIcon(albumPic);
                                }else if (name.equals("Progress")){
                                    long offset = jsonObject.getLong("offsetInMilliseconds");
                                    long total = jsonObject.getLong("totalMillseconds");
                                    floatView.setProgress(offset,total);
                                }else if (name.equals("Pause")){
                                    floatView.dismiss();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });
        }
    }

    class MyBinder extends Binder {

        public void startOnlineListener(){
            startListener();
        }

        public void startDownchannel(){
            openDownchannel();
        }

        public void dismissFloatView(){
            if (floatView!=null) {
                floatView.dismiss();
            }
        }
    }
}
