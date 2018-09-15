package com.example.contentprovider;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/*
        没有跳转到拨号页面
 */
public class CallActivity extends AppCompatActivity {
    private static final String TAG = "CallActivity";
    Button call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        call = findViewById(R.id.callCall);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"call click: call call");
                if(ContextCompat.checkSelfPermission(CallActivity.this,Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions
                            (CallActivity.this,new String[]{Manifest.permission.CALL_PHONE},1);
                }else{
                    call();
                }
            }
        });
    }

    private void call() {
        try {
            Intent call = new Intent(Intent.ACTION_CALL);
            call.setData(Uri.parse("tel：10086"));
            //todo err:No Activity found to handle Intent
            // todo 在manifest文件中给CallActivity添加intent-filter :<category android:name="android.intent.category.DEFAULT"/>
            startActivity(call);
        } catch (SecurityException e) {
            e.printStackTrace();
        }catch (ActivityNotFoundException e){
            e.getMessage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        switch (requestCode){
            case 1:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    call();
                }else{
                    Toast.makeText(CallActivity.this,"You denied permission.",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}

