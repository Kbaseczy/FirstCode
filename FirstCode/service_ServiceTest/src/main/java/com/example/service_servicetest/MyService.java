package com.example.service_servicetest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class MyService extends Service {

    private DownloadBinder downloadBinder = new DownloadBinder();

    class DownloadBinder extends Binder{
        public void startDownload(){
            Log.d("MyService","startDownload.");
        }

        public void getProcess(){
            Log.d("MyService","getProcess.");
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // todo QUESTION:  what the used for?
        // TODO: Return the communication channel to the service.
        return downloadBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyService:","onCreate");

        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this,0,intent,0);
        //todo Warn:NotificationCompat.Builder(context)失效 被 NotificationCompat.Builder(context,channelId) 取代
        //todo 不显示前台服务通知， failed to post notification on channel "....."
        //todo err: 仍然不显示前台服务通知，更改minsdk = 26 仍然: failed to post notification on channel "miscellaneous"
        Notification notification = new NotificationCompat.Builder(this, NotificationChannel.DEFAULT_CHANNEL_ID)
                .setContentTitle("foregroundService title")
                .setContentText("foregroundService content.")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setContentIntent(pi)
                .build();
        startForeground(1,notification);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyService","onStartCommand");

        //todo 开启子线程执行逻辑，并在执行完后结束服务。 这个可以被IntentService 替代
        new Thread(new Runnable() {
            @Override
            public void run() {
                stopSelf();
            }
        });

        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyService","onDestroy");
    }
}
