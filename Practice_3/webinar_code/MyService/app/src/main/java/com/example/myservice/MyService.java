package com.example.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class MyService extends Service {

    final static String TAG = "MyService";
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"Service created");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"Service destroyed");
    }

    private IMyInterface.Stub mService = new IMyInterface.Stub() {
        @Override
        public int add(int x, int y) {
            return x + y;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        Log.i(TAG,"Message on bind " + intent);
        return mService;
    }
}