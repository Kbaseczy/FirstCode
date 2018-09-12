package com.example.uiwidget;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//todo Warn:控件的监听器 在TitleLayout执行
//TODO 之前也做过引用布局，没采取此种自定义控件去监听，这种方法应安全些，防止空对象引用。有的时候回出现id绑定错乱
public class CustomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        //todo 隐藏标题栏
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
    }


}
