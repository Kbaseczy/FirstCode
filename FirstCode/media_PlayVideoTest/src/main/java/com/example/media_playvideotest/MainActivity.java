package com.example.media_playvideotest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private VideoView videoView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button play = findViewById(R.id.play_video);
        Button pause = findViewById(R.id.pause_video);
        Button reset = findViewById(R.id.resume_video);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        reset.setOnClickListener(this);

        if(ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }else{
            initVideo();
        }
    }

    private void initVideo() {
            File file = new File(Environment.getExternalStorageDirectory(),"video.mp4");
            videoView.setVideoPath(file.getPath());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.play_video:
                if(!videoView.isPlaying()){
                    videoView.start();
                }
                break;
            case R.id.pause_video:
                if(videoView.isPlaying()){
                    videoView.pause();
                }
                break;
            case R.id.resume_video:
                if(videoView.isPlaying()){
                    videoView.resume();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
       switch (requestCode){
           case 1:
               if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                   initVideo();
               }else{
                   Toast.makeText(this,"You denied the permission.",Toast.LENGTH_SHORT).show();
                   finish();
               }
       }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(videoView!=null){
            videoView.suspend();//todo release the resource of video
        }
    }
}
