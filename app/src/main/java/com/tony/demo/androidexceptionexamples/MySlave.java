package com.tony.demo.androidexceptionexamples;

import android.content.Context;
import android.util.Log;

public class MySlave {
    private static final String TAG = "MySlave";
    private Thread mThread;
    private Context mCtxt;
    private boolean mWorking;

    public MySlave() {
        mWorking = false;
        mThread = new Thread() {
            @Override
            public void run() {
                while (mWorking) {
                    Log.i(TAG, "I'm working now...");
                }
            }
        };
    }

    public MySlave(Context ctxt) {
        this();
        mCtxt = ctxt;
    }

    public void start() {
        mWorking = true;
        mThread.start();
    }

    public void stop() {
        mWorking = false;
    }
}
