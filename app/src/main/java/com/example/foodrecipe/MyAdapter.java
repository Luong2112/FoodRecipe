package com.example.foodrecipe;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.FoodViewHolder>{

    private Context mContext;
    private List<FoodData> myFoodList;

    public MyAdapter(Context mContext, List<FoodData> myFoodList) {
        this.mContext = mContext;
        this.myFoodList = myFoodList;
    }


    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_row_item, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Glide.with(mContext).load(myFoodList.get(position).getItemImage()).into(holder.imageView);
        holder.mTitle.setText(myFoodList.get(position).getItemName());
//        holder.mIngredient.setText(myFoodList.get(position).getItemIngredient());
//        holder.mCook.setText(myFoodList.get(position).getItemCook());

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("Image",myFoodList.get(holder.getAdapterPosition()).getItemImage());
                intent.putExtra("Name", myFoodList.get(holder.getAdapterPosition()).getItemName());
                intent.putExtra("Ingredient", myFoodList.get(holder.getAdapterPosition()).getItemIngredient());
                intent.putExtra("Cook", myFoodList.get(holder.getAdapterPosition()).getItemCook());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myFoodList.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView mTitle;
        CardView mCardView;
        public FoodViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivImage);
            mTitle = itemView.findViewById(R.id.tvName);
//        mCook = itemView.findViewById(R.id.tvDescription);
//        mIngredient = itemView.findViewById(R.id.tvTime);
            mCardView = itemView.findViewById(R.id.myCartView);
        }
    }
}

