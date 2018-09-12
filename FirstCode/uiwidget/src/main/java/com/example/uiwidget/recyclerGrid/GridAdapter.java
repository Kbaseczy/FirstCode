package com.example.uiwidget.recyclerGrid;

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


public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder>{
    private List<Fruit> mFruitList;

    public GridAdapter(List<Fruit> fruitList) {
        mFruitList = fruitList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView fruitImage;
        TextView fruitName;
        View fruitView;

        public ViewHolder( View itemView) {
            super(itemView);
            fruitView = itemView;
            fruitImage = itemView.findViewById(R.id.fruit_image_grid);
            fruitName = itemView.findViewById(R.id.fruit_name_grid);
        }
    }
    @Override
    public GridAdapter.ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.fruit_item_grid,viewGroup,false);

        final ViewHolder holder = new ViewHolder(view);
        holder.fruitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Fruit fruit = mFruitList.get(position);
                Toast.makeText(view.getContext(),"you clicked view "+fruit.getName(),Toast.LENGTH_LONG).show();
            }
        });
        holder.fruitImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Fruit fruit = mFruitList.get(position);
                Toast.makeText(view.getContext(),"you clicked image "+fruit.getName(),Toast.LENGTH_LONG).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder( GridAdapter.ViewHolder viewHolder, int i) {
        Fruit fruit = mFruitList.get(i);
        viewHolder.fruitImage.setImageResource(fruit.getImageId());
        viewHolder.fruitName.setText(fruit.getName());
    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }
}
