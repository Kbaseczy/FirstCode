package com.example.contentprovider;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button addData,deleteData,updateData,queryData;
    String newID;
    Button getContacts,call;
    private static final String TAG = "ProviderTest";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO 点击事件触发方法运行，没有点击事件方法也会运行吗，二者运行的优先性如何
        //todo 获取联系人--跳转activity
        getContacts = findViewById(R.id.getContacts);
        getContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,GetContactsActivity.class);
                startActivity(intent);
            }
        });
        call = findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent call = new Intent(MainActivity.this,CallActivity.class);
                startActivity(call);
            }

    });
        initView();
    }
    private void initView(){
        addData = findViewById(R.id.add_data);
        deleteData = findViewById(R.id.delete_data);
        updateData = findViewById(R.id.update_data);
        queryData = findViewById(R.id.query_data);
        addData.setOnClickListener(this);
        deleteData.setOnClickListener(this);
        updateData.setOnClickListener(this);
        queryData.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_data:
                Uri uri = Uri.parse("content://com.example.persistentstorage.provider/book");
                ContentValues values = new ContentValues();
                values.put("name","the jesus");
                values.put("author","noBody");
                values.put("price","999");
                values.put("pages",789);
                Uri newUri = getContentResolver().insert(uri,values);
                assert newUri != null;  //断点测试
                newID = newUri.getPathSegments().get(1);
                break;
            case R.id.delete_data:
                Uri uri_delete = Uri.parse("com.example.persistentstorage.provider/book/"+newID);
                getContentResolver().delete(uri_delete,null,null);
                break;
            case R.id.update_data:
                Uri uri_update = Uri.parse("com.example.persistentstorage.provider/book/"+newID);
                ContentValues values_update = new ContentValues();
                values_update.put("name","changes_name");
                values_update.put("author","changes_author");
                values_update.put("price",0);
                values_update.put("pages",1314);
                getContentResolver().update(uri_update,values_update,null,null);
                break;
            case R.id.query_data:
                Uri uri_query = Uri.parse("com.example.persistentstorage.provider/book");

                Cursor cursor = getContentResolver().query(uri_query,null,null,null,null);
                if(cursor!=null){
                    while(cursor.moveToNext()){
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        String price = cursor.getString(cursor.getColumnIndex("price"));
                        String pages = cursor.getString(cursor.getColumnIndex("pages"));
                        Log.d(TAG,"Book's name : "+name);
                        Log.d(TAG,"Book's author : "+author);
                        Log.d(TAG,"Book's price : "+price);
                        Log.d(TAG,"Book's pages : "+pages);
                    }
                }
                cursor.close();
                break;
            default:
                break;
        }
    }
}
