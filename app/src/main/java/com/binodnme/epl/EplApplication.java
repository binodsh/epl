package com.binodnme.epl;

import android.app.Application;
import android.content.Context;

/*
 * Created by binodnme on 2/19/16.
 */
public class EplApplication extends Application {

    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }
}
