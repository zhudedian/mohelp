package com.edong.mohelp.net.socket;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.edong.mohelp.net.Connect;
import com.edong.mohelp.service.SerConnect;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Online {

    private static SocketClient client;

    private static OnlineListener onlineListener;

    private static Timer timer;

    private static int outCount = 0;

    public static void startListener(OnlineListener listener){
        onlineListener = listener;
        if (client==null){
            client = new SocketClient(new MyHandle());
            client.clintValue(Connect.boxIP, 7777);
            client.openClientThread();

        }
        if (timer != null){
            timer.cancel();
        }
        outCount = 0;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            boolean first = true;
            @Override
            public void run() {
                if (first){
                    first = false;
                    client.sendMsg("cstart ");
                }
                if (client!=null) {
                    client.sendMsg("conline ");
                }else {
                    timer = null;
                    this.cancel();
                    return;
                }
                if (outCount==3){
                    if (onlineListener!=null){
                        onlineListener.onChange(0);
                    }
                    timer = null;
                    this.cancel();
                }
                outCount++;
            }
        },1000,1000);


    }

    private static class MyHandle extends Handler{
        private int endCount;
        @Override
        public void handleMessage(Message msg) {
            String pos = msg.obj.toString();
            if (pos.contains("c")) {
                outCount = 0;
//                Log.i("msg",pos);
                if (pos.contains("outline")){
                    Connect.isConnect = false;
                    if (onlineListener!=null){
                        onlineListener.onChange(-1);
                    }
                }else if (pos.contains("hasnew")){
                    if (onlineListener!=null){
                        onlineListener.onChange(1);
                    }
                }
                endCount = 0;
            }else {
                if (endCount >= 4){
                    if (client!=null) {
                        client.close();
                        client = null;
                        Connect.isConnect = false;
                        if (onlineListener!=null){
                            onlineListener.onChange(0);
                        }
//                        Intent intent = new Intent("connect_failed");
//                        sendBroadcast(intent);
                        endCount = 0;
                    }
                }else {
                    endCount++;
                    Log.i("count",endCount+"");
                }
            }

        }
    }

    public interface OnlineListener{
        void onChange(int state);
    }
}
