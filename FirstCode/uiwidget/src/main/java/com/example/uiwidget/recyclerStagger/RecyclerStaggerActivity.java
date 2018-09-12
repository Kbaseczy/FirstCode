package com.example.uiwidget.recyclerStagger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.uiwidget.R;
import com.example.uiwidget.fruiLlist.Fruit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecyclerStaggerActivity extends AppCompatActivity {

    private List<Fruit> fruitList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_stagger);

        RecyclerView recyclerView = findViewById(R.id.recyclerView_stagger);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        RecyclerStaggerAdapter adapter = new RecyclerStaggerAdapter(fruitList);
        recyclerView.setAdapter(adapter);
        initFruits();

    }

    private void initFruits() {
        for (int i = 0; i < 2; i++) {
            Fruit banana = new Fruit(getRandomLength("banana"), R.drawable.banana);
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
            Fruit watermelon = new Fruit(getRandomLength("watermelon"), R.drawable.watermelon);
            fruitList.add(watermelon);
        }
    }

    //todo 随机水果名出现次数-->水果名长度
    private String getRandomLength(String name){
        Random random = new Random();
        int length = random.nextInt(20)+1;
        StringBuilder builder = new StringBuilder();
        for(int i =0;i<length;i++){
            builder.append(name);
        }
        return builder.toString();
    }

}
