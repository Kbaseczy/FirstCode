package com.example.uiwidget.fruiLlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.uiwidget.R;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {

    private ListView list;
    private List<Fruit> fruitList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        list = findViewById(R.id.listUI);
        FruitAdapter fruitAdapter = new FruitAdapter(ListViewActivity.this,R.layout.fruit_item,fruitList);
        list.setAdapter(fruitAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fruit fruit = fruitList.get(i);
                Toast.makeText(ListViewActivity.this,fruit.getName(),Toast.LENGTH_LONG).show();
            }
        });
        initFruits();

    }

    private void initFruits() {
        for (int i = 0; i < 2; i++) {
            Fruit banana = new Fruit("banana", R.drawable.banana);
            fruitList.add(banana);
            Fruit cherry = new Fruit("cherry", R.drawable.cherry);
            fruitList.add(cherry);
            Fruit grape = new Fruit("grape", R.drawable.grape);
            fruitList.add(grape);
            Fruit lemon = new Fruit("lemon", R.drawable.lemon);
            fruitList.add(lemon);
            Fruit mango = new Fruit("mango", R.drawable.mongo);
            fruitList.add(mango);
            Fruit pear = new Fruit("pear", R.drawable.pear);
            fruitList.add(pear);
            Fruit strawberry = new Fruit("strawberry", R.drawable.strawberry);
            fruitList.add(strawberry);
            Fruit watermelon = new Fruit("watermelon", R.drawable.watermelon);
            fruitList.add(watermelon);
        }
    }
}
