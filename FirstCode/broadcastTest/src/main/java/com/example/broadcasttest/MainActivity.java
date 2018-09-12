package com.example.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;
    private Button sendBroadcast;
    private LocalBroadcastManager manager;
    private LocalReceiver localReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = LocalBroadcastManager.getInstance(this);

//        intentFilter = new IntentFilter();
//        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//        networkChangeReceiver = new NetworkChangeReceiver();
//        registerReceiver(networkChangeReceiver,intentFilter);

        sendBroadcast = findViewById(R.id.SendBroadcast);
        sendBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"~~~~~~~~");
//                Intent intent = new Intent("com.example.broadcasttest.MY_BROADCAST" );
//                sendOrderedBroadcast(intent,null);
                Intent intent = new Intent("com.example.broadcasttest.LOCAL_BROADCAST" );
                manager.sendBroadcast(intent);
            }
        });

        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.broadcasttest.LOCAL_BROADCAST");
        localReceiver = new LocalReceiver();
        manager.registerReceiver(localReceiver,intentFilter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.unregisterReceiver(networkChangeReceiver);
    }

    class NetworkChangeReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isAvailable())
            {
                Toast.makeText(context,"network is available.",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context,"network is not available.",Toast.LENGTH_SHORT).show();

            }

            if(networkInfo != null && networkInfo.isConnected())
            {
                Toast.makeText(context,"network is connected.",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context,"network is not connected.",Toast.LENGTH_SHORT).show();

            }
        }
    }

    class LocalReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context,"received local broadcast",Toast.LENGTH_SHORT).show();
        }
    }
}
