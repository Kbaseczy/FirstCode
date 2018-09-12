package com.example.bodhi.firstcode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
/*

 */
public class BaseActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        //todo 打印当前活动(activity) 名
        Log.e("BaseActivity",getClass().getSimpleName());
        ActivityCollector.addActivity(this); //添加当前Activity
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);//销毁当前Activity
    }
}
