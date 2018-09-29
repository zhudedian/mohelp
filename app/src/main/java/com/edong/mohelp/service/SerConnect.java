package com.edong.mohelp.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import static android.content.Context.BIND_ABOVE_CLIENT;

public class SerConnect implements ServiceConnection {

    private static SerConnect serConnect;
    private static MyService.MyBinder myBinder;

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        myBinder = (MyService.MyBinder)iBinder;

    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }

    public static void bindService(Context context){
        if (serConnect == null){
            serConnect = new SerConnect();
            Intent intent = new Intent(context, MyService.class);
            context.startService(intent);
            context.bindService(intent, serConnect, BIND_ABOVE_CLIENT);
        }else {
            startOnlineListener();
        }




    }

    public static void unbindService(Context context){
        if (serConnect!=null&&myBinder!=null) {
            try {
                context.unbindService(serConnect);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    public static void startOnlineListener(){
        if (serConnect!=null&&myBinder!=null) {
            myBinder.startOnlineListener();
        }
    }
    public static void startDownchannel(){
        if (serConnect!=null&&myBinder!=null) {
            myBinder.startDownchannel();
        }
    }

    public static void dismissFloatView(){
        if (serConnect!=null&&myBinder!=null) {
            myBinder.dismissFloatView();
        }
    }
}
