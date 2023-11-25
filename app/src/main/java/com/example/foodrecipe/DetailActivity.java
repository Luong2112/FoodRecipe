package com.example.foodrecipe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView FoodDescription;
    ImageView FoodImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        FoodDescription = findViewById(R.id.txtDescription);
        FoodImage = findViewById(R.id.ivImage);

        Bundle mBundle = getIntent().getExtras();

        if(mBundle != null){
            FoodDescription.setText(mBundle.getString("Name"));
            FoodImage.setImageResource(mBundle.getInt("Image"));
        }
    }
}