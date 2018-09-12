package com.example.bodhi.firstcode;

import android.os.Bundle;
import android.util.Log;
/*
    todo 解决活动被回收
 */
public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        Log.d(TAG,"onCreate");
        setContentView(R.layout.activity_the_two);
        //todo 取出数据
        if(saveInstanceState != null){
            String temData = saveInstanceState.getString("data_key");
            Log.d(TAG,temData);
        }
    }

    /*
    todo 保存临时数据
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String temData = "Something you just typed.";
        outState.putString("data_key",temData);
    }
}
