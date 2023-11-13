package com.tony.demo.androidexceptionexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

public class MlInnerClassActivity extends AppCompatActivity {
    private final static String TAG = "MlInnerClassActivity";
    private int[] mIntArray;
    private MySlave mSlave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ml_inner_class);
        //
        mIntArray = new int[1024 * 1024 * 5];
        //mSlave = new MySlave(this);
        mSlave = new MySlave();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSlave.start();
    }

    private class MySlave {
        private boolean mRunning = false;
        private Context mCtxt;
        private Thread mThread;

        public MySlave() {
            mThread = new Thread() {
                @Override
                public void run() {
                    while (mRunning) {
                        Log.i(TAG, "I'm running...");
                        SystemClock.sleep(1000);
                    }
                }
            };
        }

        public MySlave(Context ctxt) {
            this();
            mCtxt = ctxt;
        }

        public void start() {
            mRunning = true;
            mThread.start();
        }

        public void stop() {
            mRunning = false;
        }
    }
}