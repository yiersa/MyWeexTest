package com.tools.taojike.myapplication;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;

import java.util.HashMap;

/**
 * Created by taoji on 2016/6/25 0025.
 */
public class SecondActivity extends AppCompatActivity {
    WXSDKInstance mInstance;
    ViewGroup mContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mContainer = (ViewGroup)findViewById(R.id.root);
        mInstance = new WXSDKInstance(this); //create weex instance
        mInstance.registerRenderListener(new SimpleRenderListener()); //SimpleRenderListener需要开发者来实现

        mInstance.renderByUrl("tag",
                "http://192.168.0.101:12580/examples/build/index.js",
                new HashMap<String, Object>(),
                null,
                ScreenUtil.getDisplayWidth(this),
                ScreenUtil.getDisplayHeight(this),
                WXRenderStrategy.APPEND_ASYNC);

    }

    @Override
    public void onStart() {
        super.onStart();
        if(mInstance!=null){
            mInstance.onActivityStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mInstance!=null){
            mInstance.onActivityResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mInstance!=null){
            mInstance.onActivityPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mInstance!=null){
            mInstance.onActivityStop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mInstance!=null){
            mInstance.onActivityDestroy();
        }
    }

    class SimpleRenderListener implements IWXRenderListener {
        @Override
        public void onViewCreated(WXSDKInstance wxsdkInstance, View view) {
            if (mContainer != null) {
                mContainer.addView(view);
            }
        }

        @Override
        public void onRenderSuccess(WXSDKInstance instance, int width, int height) {

        }

        @Override
        public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {

        }

        @Override
        public void onException(WXSDKInstance instance, String errCode, String msg) {

        }
    }

}