package com.delavictoria.codepathtodoapp;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

public class TodoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(
                new FlowConfig
                        .Builder(this)
                        .openDatabasesOnInit(true)
                        .build()
        );

//        FlowLog.setMinimumLoggingLevel(FlowLog.Level.V);
        Stetho.initializeWithDefaults(this);
    }
}
