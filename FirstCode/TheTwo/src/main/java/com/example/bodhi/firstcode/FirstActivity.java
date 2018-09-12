package com.example.bodhi.firstcode;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class FirstActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "FirstActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("ThirdActivity:","Task id is " + getTaskId());
        setContentView(R.layout.activity_first);

        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(this);
        Button button1_1 = findViewById(R.id.button1_1);
        button1_1.setOnClickListener(this);
        Button dial = findViewById(R.id.dial);
        dial.setOnClickListener(this);
        Button extraToSecond = findViewById(R.id.extraData);
        extraToSecond.setOnClickListener(this);
        Button button1_2 = findViewById(R.id.button1_2);
        button1_2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button1:
                //todo 启动SecondActivity , 简化代码。并清楚知道启动SecondActivity 需要传递那些数据
                SecondActivity.actionStart(FirstActivity.this,"data1","data2");
                //todo 隐式intent
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.baidu.com"));
                startActivity(intent);
                break;
            case R.id.button1_1:
                //todo 隐式intent
                //todo 每个Intent只能有一个action,但却可以指定多个category
                Log.d(TAG,"addCategory");
                Intent intent1 = new Intent("com.example.bodhi.firstcode.Action_START");
                intent1.addCategory("android.intent.category.DEFAULT");
                startActivity(intent1);
                break;
            case R.id.dial:
                Intent intent2 = new Intent(Intent.ACTION_DIAL);
                intent2.setData(Uri.parse("tel:123456"));
                startActivity(intent2);
                break;
            case R.id.extraData:
                String data = "Hello SecondActivity.";
                Intent intent3 = new Intent(FirstActivity.this,SecondActivity.class);
                intent3.putExtra("extraData1-2",data);
                startActivity(intent3);
                break;
            case R.id.button1_2:
                Intent intent4 = new Intent(FirstActivity.this,SecondActivity.class);
                startActivityForResult(intent4,1);//第二个参数是唯一值就可以了，也可以是2,3等
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            //todo 回调方法来得到返回的数据
            case 1:
                if(resultCode==RESULT_OK){
                    String returnData = data.getStringExtra("return_data");
                    Log.e(TAG,returnData);
                }
                break;
        }
    }
}
