package com.tony.demo.androidexceptionexamples;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AnrUiThreadActivity extends AppCompatActivity {
    private final String TAG = "AnrSortingActivity";
    private final int MSG_START_SORTING = 1;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anr_ui_thread);

        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == MSG_START_SORTING) {
                    sortArray();
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.sendEmptyMessageDelayed(MSG_START_SORTING, 1000);
    }

    private void sortArray() {
        long currTime = System.currentTimeMillis();
        ArrayList<Integer> randomList = new ArrayList<>(1000000);
        for (int i = 0; i < 1000000; i++) {
            randomList.add(Double.valueOf(Math.random() * 10000000).intValue());
        }

        for (int i = 0; i < randomList.size() - 1; i++) {
            for (int j = 0; j < randomList.size() - i - 1; j++) {
                if (randomList.get(j) > randomList.get(j + 1) ) {
                    int temp = randomList.get(j);
                    randomList.set(j, randomList.get(j + 1));
                    randomList.set(j + 1, temp);
                }
            }
        }

        Log.i(TAG, "Time: " + (System.currentTimeMillis() - currTime) + " ms");

        finish();
    }
}