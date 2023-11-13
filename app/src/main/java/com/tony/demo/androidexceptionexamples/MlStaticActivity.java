package com.tony.demo.androidexceptionexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class MlStaticActivity extends AppCompatActivity {
    public static Activity mContext;
    private int[] mIntArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ml_static);
        //
        mContext = this;
        mIntArray = new int[1024 * 1024 * 5];
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, MlBdSrvService.class);
        startService(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent intent = new Intent(this, MlBdSrvService.class);
        stopService(intent);
        //mIntArray = null;
    }
}