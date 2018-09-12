package com.example.uiwidget.fruitRecycler.recyclerVertical;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.uiwidget.R;
import com.example.uiwidget.fruiLlist.Fruit;

import java.util.ArrayList;
import java.util.List;

public class RecyclerVerticalActivity extends AppCompatActivity {

    private List<Fruit> fruitList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_verticle);
        RecyclerView recyclerView = findViewById(R.id.recyclerView_vertical);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        RecyclerVerticalAdapter adapter = new RecyclerVerticalAdapter(fruitList);
        recyclerView.setAdapter(adapter);
        initFruit();

    }

    private void initFruit(){
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
