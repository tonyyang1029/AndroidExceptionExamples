package com.tony.demo.androidexceptionexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class AnrBdSrvActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "AnrBdSrvActivity";
    private Button mBtnBind;
    private Button mBtnAdd;
    private Button mBtnUnbind;
    private ServiceConnection mConnection;
    private Boolean mBound = false;
    private IAnrBdSrvService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anr_bd_srv);
        //
        mBtnBind = findViewById(R.id.anrbdsrv_btn_bind);
        mBtnBind.setOnClickListener(this);
        //
        mBtnAdd = findViewById(R.id.anrbdsrv_btn_add);
        mBtnAdd.setOnClickListener(this);
        //
        mBtnUnbind = findViewById(R.id.anrbdsrv_btn_unbind);
        mBtnUnbind.setOnClickListener(this);
        //
        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.i(TAG, "Service is connected.");
                mBound = true;
                mService = IAnrBdSrvService.Stub.asInterface(iBinder);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                Log.i(TAG, "Service is disconnected.");
                mBound = false;
                mService = null;
            }
        };
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, AnrBdSrvService.class);
        if (view.getId() == R.id.anrbdsrv_btn_bind) {
            if (!mBound) {
                mBound = true;
                bindService(intent, mConnection, BIND_AUTO_CREATE);
            }
        } else if (view.getId() == R.id.anrbdsrv_btn_add) {
            if (mBound && mService != null) {
                try {
                    int ret = mService.add(1, 2);
                    Log.i(TAG, "Add = " + ret);
                } catch (RemoteException e) {
                    Log.e(TAG, e.toString());
                }
            }
        } else if (view.getId() == R.id.anrbdsrv_btn_unbind) {
            if (mBound) {
                mBound = false;
                unbindService(mConnection);
            }
        }
    }
}