package com.example.bodhi.materialtest;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*     TODO xmlns:app 是Material Design 经常用到的

    难点在于 DrawerLayout 的理解，区别于之前使用较多的LinearLayout
    TODO ----------------------------------------------------------------------
    总体布局：CoordinatorLayout(加强版FrameLayout (Toolbar,RecyclerView,FloatingActionButton))
            + NavigationView(侧滑栏内容 (nav_head,nav_menu))
    在分布局里面还有细节，需要细心摸索
    TODO ----------------------------------------------------------------------
    todo 遮挡问题
    android.support.design.widget.CoordinatorLayout 解决  FloatingActionButton(悬浮按钮)遮挡 Snackbar
    android.support.design.widget.AppBarLayout 解决 recyclerView遮挡Toolbar
    TODO ----------------------------------------------------------------------
    todo 刷新效果
    SwipeRefreshLayout 实现 RecyclerView 的下拉刷新 ，因此是将RecyclerView 嵌套在 SwipeRefreshLayout 里面
    todo 关于 setColorSchemeColors & setColorSchemeResources  设置下拉刷新的颜色
    如果用setColorSchemeColors方法，参数传的是颜色值，用setColorSchemeResources 参数传资源id。
    而setColorSchemeResources其实也是先通过资源id得到颜色值，最后调用setColorSchemeColors方法。
    TODO ----------------------------------------------------------------------
        todo 可折叠式标题栏
        1.布局 2.FruitActivity取数据 3.FruitAdapter发数据
        1.布局 AppBarLayout标题栏（图片&标题） ， NestedScrollView详情 ， FloatingActionButton悬浮按钮
        2.取数据 Intent :intent.getStringExtra
        3.发数据 intent.putExtra
---------------------

 */
public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;

    private Fruit[] fruits = {new Fruit("cherry",R.drawable.cherry),
            new Fruit("Grape",R.drawable.grape),
            new Fruit("Banana",R.drawable.banana),
            new Fruit("Lemon",R.drawable.lemon),
            new Fruit("Mongo",R.drawable.mongo),
            new Fruit("Pear",R.drawable.pear),
            new Fruit("WaterMelon",R.drawable.watermelon),};
    private List<Fruit> fruitList = new ArrayList<>();
    private FruitAdapter fruitAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar); //import android.widget.Toolbar; TODO 导这个包报错方法参数  //TODO 报错看看括号内的内容，这里就是因为包之间的不兼容
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navView = findViewById(R.id.nav_view);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.icon_menu);
        }

        //TODO 设置侧滑栏
        navView.setCheckedItem(R.id.nav_friends);  //todo 默认这一项为选中状态
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem menuItem) {
                //todo 侧滑栏菜单中的点击事件，和普通的按钮点击一样设置。  这里简单设为退出
                drawerLayout.closeDrawers();
                return true;
            }
        });

        //TODO 设置悬浮按钮
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo Snackbar 额外增加一个按钮的点击事件
                //todo Snackbar 会遮挡 悬浮按钮 ->CoordinatorLayout 替换FrameLayout解决使得悬浮按钮协调向上不被遮挡
                Snackbar.make(view,"Data delete",Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this,"Data Restored",Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });

        initFruit();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        fruitAdapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(fruitAdapter);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorAccent);
//        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#FFF"));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //todo 正常应是从网络获取数据进行刷新，这里简单化直接定义一个方法模拟刷新效果
                refreshFruit();
            }
        });
    }

    private void initFruit(){
        fruitList.clear();
        for(int i=0;i < 50; i++){
            Random random = new Random();

            int index = random.nextInt(fruits.length);//todo 随机获取水果列表的水果，取出索引
            fruitList.add(fruits[index]);              // TODO 达到 随机添加到fruitList中，每次看到的水果列表都不同
        }
    }

    private void refreshFruit(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruit();
                        fruitAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.backup:
                Toast.makeText(this,"Backup",Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this,"delete",Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                Toast.makeText(this,"setting",Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home://todo 注意这里的“home” id 便是HomeAsUp按钮的
                //  No drawer view found with gravity LEFT -->android:layout_gravity="start"
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    }




}
