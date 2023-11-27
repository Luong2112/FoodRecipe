package com.example.foodrecipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodrecipe.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class User extends AppCompatActivity {
    TextView name, phone;

    List<UserData> userDataList;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foodrecipe-ltddnc-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        name = findViewById(R.id.txtNameUser);
        phone = findViewById(R.id.txtPhoneUser);

        loadData();
    }

    public void btnHome(View view) {
        startActivity(new Intent(User.this, MainActivity.class));
    }

    private void loadData(){
        Intent intent = getIntent();
        String phoneIntent = intent.getStringExtra("Phone");
        reference.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(phoneIntent)){
                    String Name = snapshot.child(phoneIntent).child("Fullname").getValue(String.class);
                    if (Name != null){
                        name.setText(Name);
                        phone.setText(phoneIntent);
                    }else {
                        phone.setText(phoneIntent);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    protected void onResume(){
        super.onResume();
        loadData();
    }

}

