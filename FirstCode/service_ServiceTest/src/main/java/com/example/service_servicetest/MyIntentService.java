package com.example.service_servicetest;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class MyIntentService extends IntentService {

    //todo : 无参的构造函数 ，并且在内部调用父类的有参构造函数   --->   indispensable
    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent( Intent intent) {
        //todo 处理逻辑 。  打印当前Thread 的 id
        Log.d("MyIntentService:","Thread id is "+Thread.currentThread().getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyIntentService:","destroy");
    }
}
