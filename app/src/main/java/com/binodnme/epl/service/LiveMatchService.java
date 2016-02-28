package com.binodnme.epl.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by binodnme on 2/27/16.
 */
public class LiveMatchService extends Service {


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //check for live match in current match day
        //if live match exists: pool the data at regular interval
        // and update the UI


        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
