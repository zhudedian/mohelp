package com.edong.mohelp.app;

import android.app.Application;
import android.content.Context;

import com.edong.mohelp.service.SerConnect;


/**
 * Created by Eric on 2017/6/27.
 */

public class App extends Application {
    private static Context context;

    @Override
    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();

    }
    public static Context getContext(){
        return context;
    }
}
