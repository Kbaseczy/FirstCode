package com.example.bodhi.firstcode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "SecondActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("ThirdActivity:","Task id is " + getTaskId());
        setContentView(R.layout.activity_second);
        actionStart(this,"test1","test2");//test

        Button getData = findViewById(R.id.getDataFrom1);
        getData.setOnClickListener(this);
        Button returnData = findViewById(R.id.returnData);
        returnData.setOnClickListener(this);
    }

    //TODO 调用该方法，传递数据。启动活动的最佳方法
    public static void actionStart(Context context,String data1,String data2){
        Intent intent = new Intent(context,SecondActivity.class);
        intent.putExtra("param1",data1);
        intent.putExtra("param2",data2);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.getDataFrom1:
                Intent intent = getIntent();
                String data = intent.getStringExtra("extraData1-2");
                Log.d(TAG,data);
                break;
            case R.id.returnData:
                Intent intent1 = new Intent();
                intent1.putExtra("return_data","Hello firstActivity.");
                setResult(RESULT_OK,intent1);
                break;
        }
    }

    //todo 通过覆写此方法按返回键返回数据
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("data_return","Hello FirstActivity");
        setResult(RESULT_OK,intent);
        finish();
    }
}
