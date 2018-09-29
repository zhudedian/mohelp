package com.edong.mohelp.net;

import android.content.Context;
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

public class RequestUtil {



    private HandleResult handleResult;
    private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(20, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).build();

    private boolean isRequesting;

    private Object lock = new Object();

    public void request(final Event event, final HandleResult handleResult){
        this.handleResult = handleResult;
        new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (lock){
                            Gson gson = new Gson();
                            String value = gson.toJson(event);
                            Log.i("edong", "value:"+value);
                            Request request = new Request.Builder().header("event", changeToUnicode(value))
                                    .url(Connect.eventUrl).build();
                            Call call = okHttpClient.newCall(request);
                            Response response = call.execute();
                            String result = response.body().string();
                            Log.i("edong", "result:"+result);
                            Message message = mHandler.obtainMessage();
                            message.what = 0;
                            Bundle data = new Bundle();
                            data.putString("result",result);
                            message.setData(data);
                            mHandler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(1);
                }
            }
        }.start();
    }
    private Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what) {
                case 0:
                    Bundle data = msg.getData();
                    if (data == null) {
                        return;
                    }
                    handleResult.resultHandle(data.getString("result"));
                    isRequesting = false;
                    break;
                case 1:
                    handleResult.resultHandle("null");
                    isRequesting = false;
                    break;
            }
        }
    };
    private String changeToUnicode(String str){
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0, length = str.length(); i < length; i++) {
            char c = str.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                stringBuffer.append(String.format("\\u%04x", (int) c));
            } else {
                stringBuffer.append(c);
            }
        }
        String unicode = stringBuffer.toString();
        return unicode;
    }

    public interface HandleResult{
        void resultHandle(String result);
    }
}
