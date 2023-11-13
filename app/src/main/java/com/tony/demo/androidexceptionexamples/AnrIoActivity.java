package com.tony.demo.androidexceptionexamples;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Permission;

public class AnrIoActivity extends AppCompatActivity {
    private final String TAG = "AnrIoActivity";
    private final int PERMISSION_REQUEST_CODE = 666;
    private final int MSG_START_IO = 1;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anr_io);

        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == MSG_START_IO) {
                    try {
                        ioStressTest();
                    } catch (IOException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT < 33 && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE};
            requestPermissions(permissions, PERMISSION_REQUEST_CODE);
        } else {
            mHandler.sendEmptyMessageDelayed(MSG_START_IO, 1000);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean ret = true;

        if (requestCode == PERMISSION_REQUEST_CODE) {
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    ret = false;
                    break;
                }
            }

            if (ret) {
                mHandler.sendEmptyMessageDelayed(MSG_START_IO, 1000);
            } else {
                finish();
            }

            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void ioStressTest() throws IOException {
        File dirDocuments = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File dirStressTest = new File(dirDocuments, "iostresstest");
        if (dirStressTest.exists()) {
            File[] files = dirStressTest.listFiles();
            if (files.length > 0) {
                for (File file : files) {
                    file.delete();
                }
            }
        } else {
            dirStressTest.mkdir();
        }

        for (int i = 0; i < 1000000; i++) {
            File file = new File(dirDocuments, "test" + i);
            FileWriter writer = new FileWriter(file);
            writer.write("This is a test file.");
            writer.flush();
            writer.close();
        }
    }
}