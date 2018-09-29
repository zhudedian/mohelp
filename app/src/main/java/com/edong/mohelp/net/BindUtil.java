package com.edong.mohelp.net;

import android.content.Context;
import android.content.SharedPreferences;

import com.edong.mohelp.app.App;

public class BindUtil {

    public static void setBindDevice(Device device){
        SharedPreferences preferences = App.getContext().getSharedPreferences("edong_bind_device", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("ip",device.getIp());
        editor.putString("serial",device.getSerial());
        editor.putString("product",device.getProduct());
        editor.apply();

    }

    public static Device getBindDevice(){
        SharedPreferences preferences = App.getContext().getSharedPreferences("edong_bind_device", Context.MODE_PRIVATE);
        String ip =preferences.getString("ip","");
        String serial = preferences.getString("serial","");
        String product = preferences.getString("product","");
        if (ip.equals("")||serial.equals("")){
            return null;
        }
        return new Device(ip,serial,product);
    }
}
