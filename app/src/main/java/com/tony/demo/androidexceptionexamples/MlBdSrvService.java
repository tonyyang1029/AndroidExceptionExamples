package com.tony.demo.androidexceptionexamples;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

public class MlBdSrvService extends Service {
    private final String TAG = "MlBgSrvService";
    private final int MSG_HEART_BEAT = 1;
    private Handler mHandler;
    private Context[] mContext;
    private IBinder mBinder;

    public MlBdSrvService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mHandler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == MSG_HEART_BEAT) {
                    Log.i(TAG, "Heartbeating...");
                    mHandler.sendEmptyMessageDelayed(MSG_HEART_BEAT, 1000);
                }
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mHandler.sendEmptyMessageDelayed(MSG_HEART_BEAT, 1000);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}