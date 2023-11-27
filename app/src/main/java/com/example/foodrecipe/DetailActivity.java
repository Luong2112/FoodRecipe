package com.example.foodrecipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    TextView txtName, txtIngredient, txtCook;
    ImageView FoodImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        txtName = findViewById(R.id.txtName);
        txtIngredient = findViewById(R.id.txtIngredient);
        txtCook =findViewById(R.id.txtCook);
        FoodImage = findViewById(R.id.ivDetailImage);

        Intent intent = getIntent();

        if(intent != null){
            try {
                Glide.with(DetailActivity.this)
                        .load(intent.getStringExtra("Image"))
                        .into(this.FoodImage);
            } catch (Exception e) {
                Toast.makeText(this,  e.getMessage(), Toast.LENGTH_LONG).show();
            }

            txtName.setText(intent.getStringExtra("Name"));
            txtIngredient.setText(intent.getStringExtra("Ingredient"));
            txtCook.setText(intent.getStringExtra("Cook"));
        }
    }
}