package com.example.android.bakingapp.application;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by akshayshahane on 15/07/17.
 */

public class BakingApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        MultiDex.install(this);
        // Normal app init code...
    }
}
