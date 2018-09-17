package com.example.notificationtest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;  //兼容API
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button noti = findViewById(R.id.notificationTest);
        noti.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.notificationTest:
            //todo 通知栏监听事件
                Intent intent = new Intent(this,NotificationActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);

                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.cancel(1);         //todo 设置通知栏自动消失，          第二种方式
                Notification notification = new NotificationCompat.Builder(this).setContentTitle("contentTitle")
                    //.setContentText("contentText")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher_round))
                    .setAutoCancel(true)  //todo 设置跳转偶后，通知栏自动消失。第一种方式
                    .setStyle(new NotificationCompat.BigTextStyle().bigText
                            ("new NotificationCompat.BigTextStyle().bigText,new NotificationCompat.BigTextStyle().bigText" +
                                    ",new NotificationCompat.BigTextStyle().bigText,new NotificationCompat.BigTextStyle().bigText"))
                    .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher_round)))
                    .setLights(Color.GREEN,1000,1000)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setVibrate(new long[]{0,1000,1000,1000}) //todo 震动提示，奇数下标静止，偶数震动，达到震动交替进行->震动1s,静止1s，震动1s
                        .setSound(Uri.fromFile(new File("/storage/emulated/0/netease/cloudmusic/Music/Papillon.mp3")))
                    .build();
                manager.notify(1,notification);
                break;
        }

    }
}
