package com.example.broadcast_forceoffline;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/*
    MainActivity 中senBroadcast(intent) 其中的intent 设置的值如下  value
    在 BaseActivity 中 设置IntentFilter 并添加action--> intentFilter.addAction(value)

    todo 强制下线Demo 只需要发送  强制下线的intent值的广播-->Intent intent = new Intent("com.example.broadcast_forceoffline.FORCE_OFFLINE");
                                                    sendBroadcast(intent);
                    todo     并在BaseActivity中响应
    opinion：这种处理方式，重点还是广播（broadcast）的使用，最后都是触发强制下线的事件。
             此处是发送强制下线的广播，另外也可以调用强制下线的方法。
            如果说，广播是最好的，后面的学习慢慢认识
 */
public class MainActivity extends BaseActivity {

    private Button off;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        off =  findViewById(R.id.force_offLine);
        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo 隐式intent
                Intent intent = new Intent("com.example.broadcast_forceoffline.FORCE_OFFLINE");
                sendBroadcast(intent);
            }
        });

    }
}
