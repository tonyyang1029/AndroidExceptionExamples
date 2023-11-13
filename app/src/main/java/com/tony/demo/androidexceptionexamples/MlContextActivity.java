package com.tony.demo.androidexceptionexamples;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MlContextActivity extends AppCompatActivity {
    private static final String TAG = "MlContextActivity";
    private int[] mIntArray;
    private MySlave mSlave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ml_context);
        //
        mIntArray = new int[1024 * 1024 * 5];
        mSlave = new MySlave(this);
        //mSlave = new MySlave();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSlave.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //mSlave.stop();
    }
}