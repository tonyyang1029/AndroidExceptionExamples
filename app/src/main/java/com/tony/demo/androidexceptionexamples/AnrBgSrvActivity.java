package com.tony.demo.androidexceptionexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class AnrBgSrvActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "AnrBgSrvActivity";
    private Button mBtnStart;
    private Button mBtnStop;
    private Button mBtnBind;
    private Button mBtnUnbind;
    private ServiceConnection mConnection;
    private Boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anr_bg_srv);
        //
        mBtnStart = findViewById(R.id.anrbgsrv_btn_start);
        mBtnStart.setOnClickListener(this);
        //
        mBtnStop = findViewById(R.id.anrbgsrv_btn_stop);
        mBtnStop.setOnClickListener(this);
        //
        mBtnBind = findViewById(R.id.anrbgsrv_btn_bind);
        mBtnBind.setOnClickListener(this);
        //
        mBtnUnbind = findViewById(R.id.anrbgsrv_btn_unbind);
        mBtnUnbind.setOnClickListener(this);
        //
        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.i(TAG, "Service is connected.");
                mBound = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                Log.i(TAG, "Service is disconnected.");
                mBound = false;
            }
        };
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, AnrBgSrvService.class);

        if (view.getId() == R.id.anrbgsrv_btn_start) {
                startService(intent);
        } else if (view.getId() == R.id.anrbgsrv_btn_stop) {
                stopService(intent);
        } else if (view.getId() == R.id.anrbgsrv_btn_bind) {
            if (!mBound) {
                bindService(intent, mConnection, BIND_AUTO_CREATE);
            }
        } else if (view.getId() == R.id.anrbgsrv_btn_unbind) {
            if (mBound) {
                unbindService(mConnection);
            }
        }
    }
}