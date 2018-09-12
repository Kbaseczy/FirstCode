package com.example.uiwidget.recyclerStagger;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uiwidget.R;
import com.example.uiwidget.fruiLlist.Fruit;

import java.util.List;
//todo err:on a null object reference 没有在监听中出问题，没有初始化或没有绑定ID
public class RecyclerStaggerAdapter extends RecyclerView.Adapter<RecyclerStaggerAdapter.ViewHolder>{

    private List<Fruit> mFruitList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        View fruitView;
        ImageView fruitImage;
        TextView fruitName;
        public ViewHolder( View itemView) {
            super(itemView);
            fruitView = itemView;
            fruitImage =  itemView.findViewById(R.id.fruit_image_stagger);
            fruitName  = itemView.findViewById(R.id.fruit_name_stagger);
        }
    }

    public RecyclerStaggerAdapter(List<Fruit> fruitList) {
        mFruitList = fruitList;
    }

    @Override
    public RecyclerStaggerAdapter.ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.fruit_item_stagger,viewGroup,false);
        final ViewHolder holder = new ViewHolder(view);
        //todo 为每个子项添加监听器，区别listView
        holder.fruitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Fruit fruit = mFruitList.get(position);
                Toast.makeText(view.getContext(),"you clicked view"+fruit.getName()
                        ,Toast.LENGTH_LONG).show();
            }
        });
        holder.fruitImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Fruit fruit = mFruitList.get(position);
                Toast.makeText(view.getContext(),"you clicked image"+fruit.getName()
                        ,Toast.LENGTH_LONG).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerStaggerAdapter.ViewHolder viewHolder, int i) {
        Fruit fruit = mFruitList.get(i);
        viewHolder.fruitImage.setImageResource(fruit.getImageId());
        viewHolder.fruitName.setText(fruit.getName());
    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }
}

