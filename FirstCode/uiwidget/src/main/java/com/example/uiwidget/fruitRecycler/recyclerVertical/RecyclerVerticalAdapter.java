package com.example.uiwidget.fruitRecycler.recyclerVertical;

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

public class RecyclerVerticalAdapter extends RecyclerView.Adapter<RecyclerVerticalAdapter.ViewHolder>{

    private List<Fruit> mFruitList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        View fruitView;
        ImageView fruitImage;
        TextView fruitName;
        public ViewHolder( View itemView) {
            super(itemView);
            fruitView = itemView;
            fruitImage =  itemView.findViewById(R.id.fruit_image_vertical);
            fruitName  = itemView.findViewById(R.id.fruit_name_vertical);
        }
    }

    public RecyclerVerticalAdapter(List<Fruit> fruitList) {
        mFruitList = fruitList;
    }

    @Override
    public RecyclerVerticalAdapter.ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.fruit_item_vertical,viewGroup,false);
        final ViewHolder holder = new ViewHolder(view);

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
    public void onBindViewHolder(RecyclerVerticalAdapter.ViewHolder viewHolder, int i) {
        Fruit fruit = mFruitList.get(i);
        viewHolder.fruitImage.setImageResource(fruit.getImageId());
        viewHolder.fruitName.setText(fruit.getName());

    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }
}

