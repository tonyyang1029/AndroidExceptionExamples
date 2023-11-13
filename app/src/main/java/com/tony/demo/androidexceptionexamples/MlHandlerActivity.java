package com.tony.demo.androidexceptionexamples;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import java.util.ArrayList;

public class MlHandlerActivity extends AppCompatActivity {
    private final String TAG = "MlHandlerActivity";
    private Handler mHandler;
    private ArrayList<int[]> mList;
    private boolean mRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ml_handler);

        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.i(TAG, "Processing message...");
            }
        };

        mList = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (mRunning) {
                    CustomRunnable runnable = new CustomRunnable();
                    mHandler.postDelayed(runnable, 500000);
                    SystemClock.sleep(200);
                }
            }
        };
        mRunning = true;
        thread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRunning = false;
        //mHandler.removeCallbacksAndMessages(null);
    }

    private class CustomRunnable implements Runnable {
        public CustomRunnable() {
            mList.add(new int[1024 * 1024]);
        }

        @Override
        public void run() {
            Log.i(TAG, "List size: " + mList.size());
        }
    }
}