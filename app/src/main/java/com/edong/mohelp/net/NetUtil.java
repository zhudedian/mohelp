package com.edong.mohelp.net;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by yd on 2018/5/23.
 */

public class NetUtil {
    public static boolean isWifiConnect(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Service.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = manager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo!=null) {
            return wifiInfo.isConnected() && wifiInfo.isAvailable();
        }
        return false;
    }
    /**
     * �ж�Ethernet�Ƿ����
     */
    @SuppressLint("InlinedApi")
    public static boolean isEthernetConnect(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Service.CONNECTIVITY_SERVICE);
        NetworkInfo etherInfo = manager
                .getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
        if (etherInfo!=null) {
            return etherInfo.isConnected() && etherInfo.isAvailable();
        }
        return false;
    }
    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }
    public static boolean isNetworkAvailable(Context context) {
        return isWifiConnect(context) || isEthernetConnect(context)||isMobileConnected(context);
    }

}
