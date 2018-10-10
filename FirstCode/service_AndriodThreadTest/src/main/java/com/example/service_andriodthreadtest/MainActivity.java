package com.example.service_andriodthreadtest;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/*
    1.不开启线程可以直接点击按钮变换字
    2.开启线程，在子线程中进行UI操作报错：
    TODO err：Only the original thread that created a view hierarchy can touch its views.
    3.开启线程，通过异步消息处理，可实现在子线程中进行UI操作：-->把操作信号传递到主线程执行。
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int UPDATE_TEXT = 1;
    private TextView text;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @SuppressLint("SetTextI18n")
        public void handleMessage(Message msg){
            switch (msg.what){
                case UPDATE_TEXT:
                    text.setText("Nice nice nice.");
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button changeText = findViewById(R.id.change_text);
        changeText.setOnClickListener(this);
        text = findViewById(R.id.text_view);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.change_text:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                       Message message = new Message();
                       message.what = UPDATE_TEXT;
                       handler.sendMessage(message);
                    }
                }).start();
                break;
            default:
                break;
        }
    }
}
