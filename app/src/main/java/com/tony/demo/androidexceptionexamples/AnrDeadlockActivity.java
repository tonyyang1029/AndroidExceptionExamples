package com.tony.demo.androidexceptionexamples;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import java.util.ArrayList;

import kotlin.jvm.Synchronized;

public class AnrDeadlockActivity extends AppCompatActivity {
    private final String TAG = "AnrDeadlockActivity";
    private final int MSG_START_ACCESSING = 1;
    private Handler mHandler;
    private Object mLockedRes = new Object();
    private ArrayList<Integer> mList;
    private AsyncTask<String, Boolean, Boolean> mAsynTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anr_deadlock);

        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == MSG_START_ACCESSING) {
                    synchronized (mLockedRes) {
                        for (int i = 0; i < mList.size(); i++) {
                            Log.i(TAG, "Entry #" + (i + 1) + ": " + mList.get(i).intValue());
                        }
                    }
                }
            }
        };
        //
        mList = new ArrayList<>();
        //
        mAsynTask = new AsyncTask<String, Boolean, Boolean>() {
            @Override
            protected Boolean doInBackground(String... strings) {
                synchronized(mLockedRes) {
                    for (int i = 0; i < 1000000; i++) {
                        mList.add(Double.valueOf(Math.random() * 10000000).intValue());
                        SystemClock.sleep(200);
                    }
                }

                return true;
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();

        mHandler.sendEmptyMessageDelayed(MSG_START_ACCESSING, 1000);
        mAsynTask.execute();
    }
}