package com.tony.demo.androidexceptionexamples;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;

public class AnrBdSrvService extends Service {
    private IBinder iBinder = new IAnrBdSrvService.Stub() {
        @Override
        public int add(int a, int b) throws RemoteException {
            SystemClock.sleep(30000);
            return (a + b);
        }
    };

    public AnrBdSrvService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }
}