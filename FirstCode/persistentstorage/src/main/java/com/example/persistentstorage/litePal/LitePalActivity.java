package com.example.persistentstorage.litePal;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DialogTitle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.persistentstorage.R;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;
import java.util.zip.DataFormatException;

public class LitePalActivity extends AppCompatActivity implements View.OnClickListener{
    private Button createDatabase;
    private Button addData,updateData,deleteData,queryData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_litepal);
        initView();
    }

    private void initView(){
        createDatabase = findViewById(R.id.litePal_createDatabase);
        addData = findViewById(R.id.litePal_addData);
        updateData = findViewById(R.id.litePal_updateData);
        deleteData = findViewById(R.id.litePal_deleteData);
        queryData = findViewById(R.id.litePal_queryData);

        createDatabase.setOnClickListener(this);
        addData.setOnClickListener(this);
        updateData.setOnClickListener(this);
        deleteData.setOnClickListener(this);
        queryData.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.litePal_createDatabase:
                //todo err：dbname is empty or not defined in litepal.xml file
                Connector.getDatabase(); //创建数据库
                break;
            case R.id.litePal_addData:
                Book book = new Book();
                book.setAuthor("Jankin");
                book.setId(1);
                book.setName("brief history");
                book.setPage(489);
                book.setPrice(56);
                book.setPublisher("Peking");
                book.save();
                break;
            case R.id.litePal_updateData:
                Book book1 = new Book();
                book1.setPage(890);
                book1.setPrice(67.1);
                //book1.setToDefault("列名",value);
                //book1.updateAll();不设置条件，就更新所有
                book1.updateAll("name = ? and author =?","brief history","Jankin");
                break;
            case R.id.litePal_deleteData:
                DataSupport.deleteAll(Book.class,"price > ?","50");
                break;
            case R.id.litePal_queryData:

                // TODO 查询表中全部信息
                List<Book> books = DataSupport.findAll(Book.class);
                for(Book book2:books){
                    Log.d("LitePal-query:","name : "+book2.getName());
                    Log.d("LitePal-query:","author : "+book2.getAuthor());
                    Log.d("LitePal-query:","pages : "+book2.getPage());
                    Log.d("LitePal-query:","price : "+book2.getPrice());
                    Log.d("LitePal-query:","publisher : "+book2.getPublisher());
                }

                //todo 查询表中第一条、最后一条图书信息
                Book book2 = DataSupport.findFirst(Book.class);
                Log.d("findFirst : ",book2.getAuthor());
                Book book3 = DataSupport.findLast(Book.class);
                Log.d("findLast : ",book3.getPublisher());

                // TODO 查询 name , author 信息
                List<Book> bookList = DataSupport.select("name","author").find(Book.class);
                // todo 查询页数大于400 的
                List<Book> bookList1 = DataSupport.where("page > ?","400").find(Book.class);
                //todo 价格降序查询
                List<Book> bookList2 = DataSupport.order("price desc ").find(Book.class);
                //TODO limit 指定查询结果数量
                List<Book> bookList3 = DataSupport.limit(3).find(Book.class);
                // todo offset 指定查询结果偏移量，如下，查询第4,5,6条数据
                List<Book> bookList4 = DataSupport.limit(3).offset(3).find(Book.class);
                // TODO 组合 查询 第3~7条信息满足页数>400，name,author,price的信息，按价格升序排列
                List<Book> list = DataSupport.select("name","author","price")
                        .where("pages > ?","400")
                        .limit(5)
                        .offset(2)
                        .order("price")
                        .find(Book.class);
                // todo sql语句查询，findBySQL 返回 cursor 对象
                Cursor cursor = DataSupport.findBySQL("SQL语句");
                break;

        }
    }
}
