package com.example.service_servicetest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    MyService.DownloadBinder downloadBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            downloadBinder = (MyService.DownloadBinder)iBinder;
            downloadBinder.getProcess();
            downloadBinder.startDownload();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button start = findViewById(R.id.start_service);
        Button stop = findViewById(R.id.stop_service);
        Button bind = findViewById(R.id.bind_service);
        Button unbind = findViewById(R.id.unbind_service);
        Button start_intentService = findViewById(R.id.start_intentService);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        bind.setOnClickListener(this);
        unbind.setOnClickListener(this);
        start_intentService.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.start_service:
                Intent intent = new Intent(MainActivity.this,MyService.class);
                startService(intent);
                break;
            case R.id.stop_service:
                Intent intent_stop = new Intent(MainActivity.this,MyService.class);
                stopService(intent_stop);
                break;
            case R.id.bind_service:
                Intent intent_bind = new Intent(this,MyService.class);
                bindService(intent_bind,connection,BIND_ABOVE_CLIENT);
                break;
            case R.id.unbind_service:
                unbindService(connection);
                break;
            case R.id.start_intentService:
                //todo   IntentService 在服务执行完之后自动结束，不需要手动添加结束的代码
                Log.d("MainActivity:","Thread id is "+ Thread.currentThread().getId());
                Intent intent_intentService = new Intent(this,MyIntentService.class);
                startService(intent_intentService);
                break;
            default:
                break;
        }
    }
}
