package com.example.foodrecipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecylerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecylerView = findViewById(R.id.recylerView);
        List<FoodData> myFoodList;
        FoodData mFoodData;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        mRecylerView.setLayoutManager(gridLayoutManager);

        myFoodList = new ArrayList<>();

//        mFoodData = new FoodData("Cơm tấm", "", "", R.drawable.img);
//        myFoodList.add(mFoodData);
//
//        mFoodData = new FoodData("Phở bò", "", "", R.drawable.img_1);
//        myFoodList.add(mFoodData);
//
//        mFoodData = new FoodData("Cơm rang", "", "", R.drawable.heart_white);
//        myFoodList.add(mFoodData);
//
//        mFoodData = new FoodData("Xôi xéo","", "", R.drawable.heart_red);
//        myFoodList.add(mFoodData);

        MyAdapter myAdapter = new MyAdapter(MainActivity.this, myFoodList);
        mRecylerView.setAdapter(myAdapter);

    }

    public void btn_uploadActivity(View view) {
        startActivity(new Intent(this, Upload_Recipe.class));
    }
}