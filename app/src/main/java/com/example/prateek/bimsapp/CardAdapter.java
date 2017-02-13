package com.example.prateek.bimsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Created by prateek on 14/2/17.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {

    private List<Food> foodList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView food, price;
        public ImageView foodItemIcon;

        public MyViewHolder(View view) {
            super(view);
            food = (TextView) view.findViewById(R.id.textViewFood);
            //price = (TextView) view.findViewById(R.id.price);
            foodItemIcon = (ImageView) view.findViewById(R.id.imageViewCard);
        }
    }


    public CardAdapter(List<Food> foodList) {
        this.foodList = foodList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.food.setText(food.getFood());
        //holder.price.setText(food.getPrice()+" Rs");


        Picasso.with(holder.foodItemIcon.getContext())
                .load(food.getImageUrl())
                .into(holder.foodItemIcon);
    }


    @Override
    public int getItemCount() {
        return foodList.size();
    }



}