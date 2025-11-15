package com.devs.frutybot.presentation.catalog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devs.frutybot.R;
import com.devs.frutybot.presentation.common.Fruit;

import java.util.ArrayList;
import java.util.List;

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.FruitViewHolder> {

    private List<Fruit> fruits = new ArrayList<>();

    public void submitList(List<Fruit> fruits) {
        this.fruits = fruits;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FruitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fruit, parent, false);
        return new FruitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FruitViewHolder holder, int position) {
        holder.bind(fruits.get(position));
    }

    @Override
    public int getItemCount() {
        return fruits.size();
    }

    static class FruitViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, colorTextView, originTextView;

        public FruitViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textName);
            colorTextView = itemView.findViewById(R.id.textColor);
            originTextView = itemView.findViewById(R.id.textOrigin);
        }

        public void bind(Fruit fruit) {
            nameTextView.setText(fruit.getName());
            colorTextView.setText("Color: " + fruit.getColor());
        }
    }
}