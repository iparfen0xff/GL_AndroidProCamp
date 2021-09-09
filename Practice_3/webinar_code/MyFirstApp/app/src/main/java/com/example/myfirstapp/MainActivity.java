package com.example.myfirstapp;

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
import android.widget.EditText;

import com.example.myservice.IMyInterface;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "MyFirstApp";
    private IMyInterface mService;

    final private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (iBinder != null){
                mService = IMyInterface.Stub.asInterface(iBinder);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText edtX = (EditText) findViewById(R.id.editX);
        final EditText edtY = (EditText) findViewById(R.id.editY);
        final EditText edtResult = (EditText) findViewById(R.id.editResult);
        final Button btCalc = (Button) findViewById(R.id.btnAdd);
        btCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClassName("com.example.myservice", "com.example.myservice.MyService");
                try {
                    //startService(intent);
                    boolean result = bindService(intent, mConnection, BIND_AUTO_CREATE);
                    Log.i(TAG, "Bind service returned: " + result);
                } catch (SecurityException e) {
                    Log.e(TAG, "bind to service failed by security");
                }

                String sX = edtX.getText().toString();
                String sY = edtY.getText().toString();

                try {
                    int x = Integer.parseInt(sX);
                    int y = Integer.parseInt(sY);
                    int res = mService.add(x, y);
                    edtResult.setText("" + res);
                } catch (NumberFormatException | RemoteException | NullPointerException e) {
                    edtResult.setText("error");
                    Log.e(TAG, "call Add failed!");
                }
            }
        });

//        Intent intent = new Intent();
//        intent.setClassName("com.example.myservice", "com.example.myservice.MyService");
//        try {
//            boolean result = bindService(intent, mConnection, BIND_AUTO_CREATE);
//            Log.i(TAG, "Bind service returned: " + result);
//        } catch (SecurityException e) {
//            Log.e(TAG, "bind to service failed by security");
//        }

    }
}