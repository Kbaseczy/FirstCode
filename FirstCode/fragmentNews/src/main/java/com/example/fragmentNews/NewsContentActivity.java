package com.example.fragmentNews;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NewsContentActivity extends AppCompatActivity {

    String newsContent,newsTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content);
        newsContent = getIntent().getStringExtra("news_content");
        newsTitle = getIntent().getStringExtra("news_title");
        //todo 导包 不兼容  . app.Fragment -->getFragmentManager
        //todo              support.v4.Fragment --> getSupportFragmentManager
        NewsContentFragment newsContentFragment = (NewsContentFragment)
                getSupportFragmentManager().findFragmentById(R.id.news_content_fragment);
        assert newsContentFragment != null; //todo 断言调试，如果为空则不执行后面的代码
        newsContentFragment.refresh(newsTitle,newsContent);
    }

    public static void actionStart(Context context,String newsTitle,String newsContent){
        Intent intent = new Intent(context,NewsContentActivity.class);
        intent.putExtra("news_title",newsTitle);
        intent.putExtra("news_content",newsContent);
        context.startActivity(intent);
    }
}
