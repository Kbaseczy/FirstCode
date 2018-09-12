package com.example.broadcast_forceoffline;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    OffLineReceiver offLineReceiver;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        //TODO 动态添加intentFilter
        intentFilter.addAction("com.example.broadcast_forceoffline.FORCE_OFFLINE");
        offLineReceiver = new OffLineReceiver();
        registerReceiver(offLineReceiver,intentFilter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    class OffLineReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(final Context context, Intent intent) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle("WARNING");
            alertDialog.setMessage("You are forced to be offline,Please try to login again.");
            alertDialog.setCancelable(false);
            alertDialog.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCollector.finishAll();
                    Intent intent1 = new Intent(context,LoginActivity.class);
                    context.startActivity(intent1);
                }
            });
            alertDialog.show();
        }
    }
}
