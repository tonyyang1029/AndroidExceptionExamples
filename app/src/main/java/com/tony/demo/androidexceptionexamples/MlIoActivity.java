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
import java.text.SimpleDateFormat;
import java.util.Date;

public class MlIoActivity extends AppCompatActivity {
    private final String TAG = "MlIoActivity";
    private final int PERMISSION_REQUEST_CODE = 1000;
    private final int MSG_CHECK_PERMISSION = 1;
    private final int MSG_START_IO_TEST = 2;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ml_io);

        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == MSG_CHECK_PERMISSION) {
                    if (Build.VERSION.SDK_INT < 33 && checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        String[] permissions = new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_REQUEST_CODE);
                    } else {
                        mHandler.sendEmptyMessage(MSG_START_IO_TEST);
                    }
                } else if (msg.what == MSG_START_IO_TEST) {
                    try {
                        doIoTest();
                    } catch (IOException e) {
                        Log.e(TAG, e.toString());
                    }
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.sendEmptyMessage(MSG_CHECK_PERMISSION);
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
                mHandler.sendEmptyMessage(MSG_START_IO_TEST);
            } else {
                finish();
            }

            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * Creating a thread to read/write files avoid ANR in main thread.
     * @throws IOException
     */
    private void doIoTest() throws IOException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                File dirDoc = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
                File dir = new File(dirDoc, "ioml");

                if (dir.exists()) {
                    for (File f : dir.listFiles() ) {
                        f.delete();
                    }
                } else {
                    dir.mkdir();
                }

                for (int i = 0; i < 100000000; i++) {
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dateTime = sdf.format(date);

                    File file = new File(dir, dateTime + ".txt");
                    FileWriter writer = null;
                    try {
                        writer = new FileWriter(file);
                        writer.write("This is a test file.");
                        // writer.flush();
                        // writer.close();
                    } catch (IOException e) {
                        Log.e(TAG, e.toString());
                    }

                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }
}