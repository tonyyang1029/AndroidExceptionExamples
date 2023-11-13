package com.tony.demo.androidexceptionexamples;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;

import androidx.annotation.NonNull;

public class AnrBgSrvService extends Service {
    private final String TAG = "AnrBgSrvService";
    private final int MSG_START_SERVICE = 1;
    private final int MSG_BIND_SERVICE = 2;
    private Handler mHandler;

    public AnrBgSrvService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == MSG_START_SERVICE || msg.what == MSG_BIND_SERVICE) {
                    SystemClock.sleep(30000);
                }
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mHandler.sendEmptyMessageDelayed(MSG_START_SERVICE, 1000);
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public IBinder onBind(Intent intent) {
        mHandler.sendEmptyMessageDelayed(MSG_BIND_SERVICE, 1000);
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}