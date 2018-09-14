package com.example.persistentstorage;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.persistentstorage.litePal.LitePalActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText inputData,inputData2;
    private CheckBox marry;
    Button file_save,file_load,shared_save,shared_load,dataBase_create;
    Button dataBase_add,dataBase_delete,dataBase_update,dataBase_query;
    Button toLitePal;
    private MyDatabaseHelper databaseHelper;
    private static final String TAG = "Jankin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new MyDatabaseHelper(this,"BookStore.db",null,2);

        initView();
    }

    private void initView(){
        inputData = findViewById(R.id.dataSource);
        inputData2 = findViewById(R.id.dataSource2);
        marry = findViewById(R.id.married);
        file_save = findViewById(R.id.file_save);
        file_load = findViewById(R.id.file_load);
        shared_save = findViewById(R.id.SharedPreference_save);
        shared_load = findViewById(R.id.SharedPreference_load);
        dataBase_create = findViewById(R.id.dataBase_create);
        dataBase_add = findViewById(R.id.dataBase_add);
        dataBase_delete = findViewById(R.id.dataBase_delete);
        dataBase_update = findViewById(R.id.dataBase_update);
        dataBase_query = findViewById(R.id.dataBase_query);
        toLitePal = findViewById(R.id.litePal);

        file_save.setOnClickListener(this);
        file_load.setOnClickListener(this);
        shared_save.setOnClickListener(this);
        shared_load.setOnClickListener(this);
        dataBase_create.setOnClickListener(this);
        dataBase_add.setOnClickListener(this);
        dataBase_delete.setOnClickListener(this);
        dataBase_update.setOnClickListener(this);
        dataBase_query.setOnClickListener(this);
        toLitePal.setOnClickListener(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //todo 生命周期的使用，destroy 活动结束时候进行引用，对象的销毁。这里是做的存储数据

//        String input = inputData.getText().toString().trim();
//        fileSave(input);
    }

    private void fileSave(String input)  {
        FileOutputStream out;
        BufferedWriter writer = null ;
        try{
            out = openFileOutput("data", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(input);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(writer != null){
                    writer.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    private String fileLoad() {
        FileInputStream in;
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try{
            in = openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(in));
            String line ;
            while((line = reader.readLine())!= null){
                builder.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(reader!=null){
                try{
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return builder.toString();
    }

    private void sharedSave(){
        boolean m;
        String name = inputData.getText().toString().trim();
        //todo 判断是否是 int 型，否则重新输入
        int age = Integer.parseInt(inputData2.getText().toString().trim());

        if(marry.isChecked()){
            m = true;
        }else{
            m = false;
        }
        SharedPreferences.Editor editor = getSharedPreferences("data_shared",MODE_PRIVATE).edit();
        editor.putString("name",name);
        editor.putInt("age", age);
        editor.putBoolean("married",m);
        editor.apply();
    }

    private void sharedLoad(){
        SharedPreferences getData = getSharedPreferences("data_shared",MODE_PRIVATE);
        String name = getData.getString("name","");
        int age = getData.getInt("age",0);
        boolean married = getData.getBoolean("married",false);
        Log.d("sharedLoad","name:"+name);
        Log.d("sharedLoad","age:"+age);
        Log.d("sharedLoad","married:"+married);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.file_save:
                String input = inputData.getText().toString().trim();
                fileSave(input);
                break;
            case R.id.file_load:
                String getInput = fileLoad();
                if(!(TextUtils.isEmpty(( getInput)))){
                    inputData.setText(getInput);
                    inputData.setSelection(getInput.length());
                    Toast.makeText(this,"Restoring succeed.",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.SharedPreference_save:
                sharedSave();
                break;
            case R.id.SharedPreference_load:
                sharedLoad();
                break;
            case R.id.dataBase_create:
                databaseHelper.getWritableDatabase();
                break;
            case R.id.dataBase_add:
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                ContentValues values  = new ContentValues();
                values.put("author","Jankin");
                values.put("price",78);
                values.put("pages",123);
                values.put("name","People");
                db.insert("Book",null,values);
                values.clear();

                values.put("author","Jack");
                values.put("price",90);
                values.put("pages",786);
                values.put("name","Monkey");
                db.insert("Book",null,values);
                break;
            case R.id.dataBase_delete:
                SQLiteDatabase dbDelete = databaseHelper.getWritableDatabase();
                //todo 第二、三参数设置条件，配合使用，比如第二个参数是name,第三个参数便是对应到表中name的值
                dbDelete.delete("Book","name=?",new String[]{"Monkey"});

                break;
            case R.id.dataBase_update:
                SQLiteDatabase dbUpdate = databaseHelper.getWritableDatabase();
                ContentValues valuesUpdate = new ContentValues();
                valuesUpdate.put("price",28.8);
                //TODO 同上
                dbUpdate.update("Book",valuesUpdate,"name=?",new String[]{"Monkey"});
                break;
            case R.id.dataBase_query:
                SQLiteDatabase dbQuery = databaseHelper.getWritableDatabase();
                Cursor cursor = dbQuery.query("Book",null,null,null,null,null,null);
                if(cursor.moveToFirst()){
                    do{
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        String price = cursor.getString(cursor.getColumnIndex("price"));
                        String pages = cursor.getString(cursor.getColumnIndex("pages"));
                        Log.d(TAG,"Book's name : "+name);
                        Log.d(TAG,"Book's author : "+author);
                        Log.d(TAG,"Book's price : "+price);
                        Log.d(TAG,"Book's pages : "+pages);
                    }while(cursor.moveToNext());
                }
                cursor.close();
                break;
            case R.id.litePal:
                Intent intent = new Intent(MainActivity.this, LitePalActivity.class);;
                startActivity(intent);
                break;
        }
    }
}
