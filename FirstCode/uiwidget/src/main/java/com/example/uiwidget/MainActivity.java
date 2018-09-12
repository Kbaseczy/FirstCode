package com.example.uiwidget;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.uiwidget.fruiLlist.ListViewActivity;
import com.example.uiwidget.fruitRecycler.recyclerVertical.RecyclerVerticalActivity;
import com.example.uiwidget.recyclerGrid.GridActivity;
import com.example.uiwidget.recyclerStagger.RecyclerStaggerActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button changeImage,customLayout,listView;
    private Button processBar,alertDialog,processDialog;
    private Button recyclerVertical,recyclerStagger,recyclerGrid;
    private ImageView imageView;
    private EditText editText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changeImage = findViewById(R.id.changeImage);
        editText = findViewById(R.id.edit_text);
        imageView = findViewById(R.id.imageMain);
        progressBar = findViewById(R.id.processBar);
        customLayout = findViewById(R.id.customLayout);
        listView = findViewById(R.id.listView);
        processBar = findViewById(R.id.clickProcessBar);
        alertDialog = findViewById(R.id.alertDialog);
        processDialog = findViewById(R.id.processDialog);
        recyclerVertical = findViewById(R.id.recyclerView_vertical);
        recyclerStagger = findViewById(R.id.recyclerView_stagger);
        recyclerGrid = findViewById(R.id.recyclerView_grid);

        changeImage.setOnClickListener(this);
        customLayout.setOnClickListener(this);
        listView.setOnClickListener(this);
        processBar.setOnClickListener(this);
        processDialog.setOnClickListener(this);
        alertDialog.setOnClickListener(this);
        processDialog.setOnClickListener(this);
        recyclerVertical.setOnClickListener(this);
        recyclerStagger.setOnClickListener(this);
        recyclerGrid.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.changeImage:
                //todo 点击按钮触发，更换图片
                imageView.setImageResource(R.drawable.ic_launcher_foreground);
                break;

            case R.id.processBar:
                //todo 设置进度条的可见性，在可见与不可见之间交替
                if(progressBar.getVisibility()==View.GONE){
                    progressBar.setVisibility(View.VISIBLE);
                }else{
                    progressBar.setVisibility(View.GONE);
                }
                //todo 设置每点击一次按钮后，进度就会向前进行10个进度
                int process = progressBar.getProgress();
                process += 10;
                progressBar.setProgress(process);
                break;

            case R.id.alertDialog:
                //todo alertDialog
                alertDialog();
                break;

            case R.id.processDialog:
                //TODO progressDialog
                progressDialog();
                break;

            case R.id.customLayout:
                //todo 跳转至自定义控件布局：title_layout XML文件。 TitleLayout自定义控件
                Intent intent = new Intent(MainActivity.this,CustomActivity.class);
                startActivity(intent);
                break;

            case R.id.listView:
                Intent i = new Intent(MainActivity.this,ListViewActivity.class);
                startActivity(i);
                break;
                //TODO recyclerView
            case R.id.recyclerView_vertical:
                Intent intent1 = new Intent(MainActivity.this, RecyclerVerticalActivity.class);
                startActivity(intent1);
                break;
            case R.id.recyclerView_stagger:
                Intent intent2 = new Intent(MainActivity.this, RecyclerStaggerActivity.class);
                startActivity(intent2);
                break;
            case R.id.recyclerView_grid:
                Intent intent3 = new Intent(MainActivity.this, GridActivity.class);
                startActivity(intent3);
                break;
        }
    }
    public void alertDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("dialog");
        dialog.setMessage("alert information.");
        dialog.setCancelable(false);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    //todo 在对话框点击“OK”后 进行的逻辑操作
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //todo 在对话框点击“Cancel”后 进行的逻辑操作
            }
        });
        dialog.show();
    }
    public void progressDialog(){
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("progressDialog");
        progressDialog.setMessage("tips of content");
        //设置为false 会一直不消失.则需要在加载完数据后设置 dismiss（）来关闭对话框
        progressDialog.setCancelable(true);
        progressDialog.show();
    }
}
