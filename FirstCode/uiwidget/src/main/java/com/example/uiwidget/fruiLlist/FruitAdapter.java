package com.example.uiwidget.fruiLlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uiwidget.R;

import java.util.List;

public class FruitAdapter extends ArrayAdapter<Fruit> {

    private int resourceId ;
    FruitAdapter( Context context, int textViewResourceId,List<Fruit> list) {
        super(context,textViewResourceId,list);
        resourceId = textViewResourceId;

    }

    //TODO 在每个子项滚到屏幕内时调用该方法
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        Fruit fruit = getItem(position);//todo 获取当前项的实例
        ViewHolder viewHolder;
        View view;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.fruitImage = view.findViewById(R.id.fruit_image);
            viewHolder.fruitName = view.findViewById(R.id.fruit_name);
            view.setTag(viewHolder);  //todo 将viewHolder 存储到 view
        }else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.fruitImage.setImageResource(fruit.getImageId());
        viewHolder.fruitName.setText(fruit.getName());
        return view;
    }


    class ViewHolder{
        ImageView fruitImage;
        TextView fruitName;

    }
}
