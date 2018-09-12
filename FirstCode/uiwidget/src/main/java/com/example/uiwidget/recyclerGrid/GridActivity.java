package com.example.uiwidget.recyclerGrid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.uiwidget.R;
import com.example.uiwidget.fruiLlist.Fruit;

import java.util.ArrayList;
import java.util.List;

public class GridActivity extends AppCompatActivity {

    private List<Fruit> fruitList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        RecyclerView recyclerView = findViewById(R.id.gridRecycler);
        GridLayoutManager manager = new GridLayoutManager(this,3);
        GridAdapter adapter = new GridAdapter(fruitList);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
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
