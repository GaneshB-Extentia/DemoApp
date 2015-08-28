package com.ext.demo;

import android.app.Application;

import cartentia.com.common.manager.VolleyManager;

/**
 * Created by Abhijeet.Bhosale on 8/28/2015.
 */
public class DemoApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        VolleyManager.getInstance().initRequestQueue(this);
    }
}
