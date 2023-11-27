package com.example.foodrecipe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.foodrecipe.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecylerView;
    List<FoodData> myFoodList;
    MyAdapter myAdapter;
    ActivityMainBinding binding;

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Recipes");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigatonView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.favorite_nav){
                startActivity(new Intent(MainActivity.this, FavoriteList.class));
            }else if (item.getItemId() == R.id.user_nav){
                startActivity(new Intent(MainActivity.this, User.class));
            }else if (item.getItemId() == R.id.home_nav){
                startActivity(new Intent(this, MainActivity.class));
            }
            return true;
        });

        mRecylerView = findViewById(R.id.recylerView);
        mRecylerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        mRecylerView.setLayoutManager(gridLayoutManager);

        myFoodList = new ArrayList<>();
        myAdapter = new MyAdapter(this, myFoodList);
        mRecylerView.setAdapter(myAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    FoodData foodData = dataSnapshot.getValue(FoodData.class);
                    myFoodList.add(foodData);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void btn_uploadActivity(View view) {
        startActivity(new Intent(MainActivity.this, Upload_Recipe.class));
    }
}