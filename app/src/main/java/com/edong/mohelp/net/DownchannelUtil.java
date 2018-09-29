package com.edong.mohelp.net;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSource;

public class DownchannelUtil {

    private static HandleResult handleResult;

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).readTimeout(300, TimeUnit.SECONDS).build();

    private static boolean isOpenDownchannel = false;

    public static boolean isHasOpenDownchannel(){
        return isOpenDownchannel;
    }


    public static synchronized void openDownchannel(HandleResult handleResult2){
        handleResult = handleResult2;
        if (Connect.downchannelUrl==null){
            handleResult.resultHandle("null");
            return;
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    isOpenDownchannel = true;
                        Request request = new Request.Builder()
                                .url(Connect.downchannelUrl).build();
                        Call call = okHttpClient.newCall(request);
                        Response response = call.execute();
                        String result = response.body().string();
//                        Log.i("edong", "Downchannel,result:"+result);


                        Message message = mHandler.obtainMessage();
                        message.what = 0;
                        Bundle data = new Bundle();
                        data.putString("result",result);
                        message.setData(data);
                        mHandler.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(1);
                }
            }
        }.start();
    }
    private static Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what) {
                case 0:
                    Bundle data = msg.getData();
                    if (data == null) {
                        return;
                    }
                    isOpenDownchannel = false;
                    handleResult.resultHandle(data.getString("result"));

                    break;
                case 1:
                    isOpenDownchannel = false;
                    handleResult.resultHandle("null");

                    break;
            }
        }
    };

    public interface HandleResult{
        void resultHandle(String result);
    }
}
