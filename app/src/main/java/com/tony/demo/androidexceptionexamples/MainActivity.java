package com.tony.demo.androidexceptionexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnAnrUiThreadActivity;
    private Button mBtnAnrIoActivity;
    private Button mBtnAnrDeadlockActivity;
    private Button mBtnAnrFgActivity;
    private Button mBtnAnrBgActivity;
    private Button mBtnMlStaticActivity;
    private Button mBtnMlIoActivity;
    private Button mBtnMlHandlerActivity;
    private Button mBtnMlContextActivity;
    private Button mBtnMlInnerClassActivity;
    private Button mBtnMlReceiverActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnAnrUiThreadActivity = findViewById(R.id.main_btn_anr_ui_thread_activity);
        mBtnAnrUiThreadActivity.setOnClickListener(this);
        //
        mBtnAnrIoActivity = findViewById(R.id.main_btn_anr_io_activity);
        mBtnAnrIoActivity.setOnClickListener(this);
        //
        mBtnAnrDeadlockActivity = findViewById(R.id.main_btn_anr_deadlock_activity);
        mBtnAnrDeadlockActivity.setOnClickListener(this);
        //
        mBtnAnrFgActivity = findViewById(R.id.main_btn_anr_bd_srv_activity);
        mBtnAnrFgActivity.setOnClickListener(this);
        //
        mBtnAnrBgActivity = findViewById(R.id.main_btn_anr_bg_srv_activity);
        mBtnAnrBgActivity.setOnClickListener(this);
        //
        mBtnMlStaticActivity = findViewById(R.id.main_btn_ml_static_activity);
        mBtnMlStaticActivity.setOnClickListener(this);
        //
        mBtnMlIoActivity = findViewById(R.id.main_btn_ml_io_activity);
        mBtnMlIoActivity.setOnClickListener(this);
        //
        mBtnMlHandlerActivity = findViewById(R.id.main_btn_ml_handler_activity);
        mBtnMlHandlerActivity.setOnClickListener(this);
        //
        mBtnMlContextActivity = findViewById(R.id.main_btn_ml_context_activity);
        mBtnMlContextActivity.setOnClickListener(this);
        //
        mBtnMlInnerClassActivity = findViewById(R.id.main_btn_ml_innerclass_activity);
        mBtnMlInnerClassActivity.setOnClickListener(this);
        //
        mBtnMlReceiverActivity = findViewById(R.id.main_btn_ml_receiver_activity);
        mBtnMlReceiverActivity.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Boolean ret = true;
        Intent intent = new Intent();

        if (v.getId() == R.id.main_btn_anr_ui_thread_activity) {
            intent.setClass(this, AnrUiThreadActivity.class);
        } else if (v.getId() == R.id.main_btn_anr_io_activity) {
            intent.setClass(this, AnrIoActivity.class);
        } else if (v.getId() == R.id.main_btn_anr_deadlock_activity) {
            intent.setClass(this, AnrDeadlockActivity.class);
        } else if (v.getId() == R.id.main_btn_anr_bd_srv_activity) {
            intent.setClass(this, AnrBdSrvActivity.class);
        } else if (v.getId() == R.id.main_btn_anr_bg_srv_activity) {
            intent.setClass(this, AnrBgSrvActivity.class);
        } else if (v.getId() == R.id.main_btn_ml_static_activity) {
            intent.setClass(this, MlStaticActivity.class);
        } else if (v.getId() == R.id.main_btn_ml_io_activity) {
            intent.setClass(this, MlIoActivity.class);
        } else if (v.getId() == R.id.main_btn_ml_handler_activity) {
            intent.setClass(this, MlHandlerActivity.class);
        } else if (v.getId() == R.id.main_btn_ml_context_activity) {
            intent.setClass(this, MlContextActivity.class);
        } else if (v.getId() == R.id.main_btn_ml_innerclass_activity) {
            intent.setClass(this, MlInnerClassActivity.class);
        } else if (v.getId() == R.id.main_btn_ml_receiver_activity) {
            intent.setClass(this, MlReceiverActivity.class);
        } else {
            ret = false;
        }

        if (ret) startActivity(intent);
    }
}