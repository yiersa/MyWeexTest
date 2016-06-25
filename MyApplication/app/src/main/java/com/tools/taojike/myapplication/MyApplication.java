package com.tools.taojike.myapplication;

import android.app.Application;
import android.content.Context;

import com.taobao.weex.WXSDKEngine;

/**
 * Created by taoji on 2016/6/25 0025.
 */
public class MyApplication extends Application {
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
        WXSDKEngine.init(this,null,null, new ImageAdapter(),null);

    }

}
