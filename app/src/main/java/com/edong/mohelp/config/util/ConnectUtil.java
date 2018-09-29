package com.edong.mohelp.config.util;

import android.app.Activity;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.util.Log;

import java.util.List;

import static com.edong.mohelp.config.sclib.ConfigLibrary.configLibrary;


public class ConnectUtil {

    private static String ssid = "";
    private static String password = "";

    private static boolean isInit = false;


    public static synchronized void init(Activity activity){
        if (isInit){
            return;
        }
        isInit = true;
        configLibrary.rtk_sc_init();
        configLibrary.WifiInit(activity);
    }
    public static void createWifi(WifiManager wifiManager){
        WifiConfiguration config = new WifiConfiguration();
        config.SSID = "\"" + ssid + "\"";
        config.preSharedKey = "\"" + password + "\"";
        int networkId = wifiManager.addNetwork(config);
        Log.e("edong","networkId="+networkId);
        if (networkId!=-1) {
            wifiManager.updateNetwork(config);
            wifiManager.saveConfiguration();
        }
    }
    public static void conncetWifi(WifiManager wifiManager){
        WifiConfiguration config = new WifiConfiguration();
        config.SSID = "\"" + ssid + "\"";
        config.preSharedKey = "\"" + password + "\"";
        int networkId = wifiManager.addNetwork(config);
        if (networkId!=-1) {
            wifiManager.updateNetwork(config);
            wifiManager.saveConfiguration();
            wifiManager.enableNetwork(networkId, true);
        }else {
            List<WifiConfiguration> configurations = configLibrary.getConfiguredNetworks();
            for (WifiConfiguration configuration:configurations){
                if (configuration.SSID.equals(ssid)){
                    wifiManager.enableNetwork(configuration.networkId,true);
                }
            }
        }
    }

    public static void setTreadMsgHandler(Handler handler){
        configLibrary.TreadMsgHandler = handler;
    }

    public static void setSsid(String s){
        ssid = s;
    }
    public static void setPassword(String pword){
        password = pword;
    }
    public static void startConfig(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                configLibrary.rtk_sc_reset();
                configLibrary.rtk_sc_set_ssid(ssid);
                configLibrary.rtk_sc_set_password(password);
                configLibrary.rtk_sc_start("","");
            }
        }).start();

    }
    public static void stopConfig(){
        configLibrary.rtk_sc_stop();
    }
}
