package com.example.foodrecipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    Button btn_login;
    TextView btn_register_now;
    TextInputEditText editTextPhone, editTextPw;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foodrecipe-ltddnc-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextPhone = findViewById(R.id.phone_login);
        editTextPw = findViewById(R.id.pw_login);
        btn_login = findViewById(R.id.btnLogin);
        btn_register_now = findViewById(R.id.btnRegisterNow);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneLogin, pwLogin;
                phoneLogin = editTextPhone.getText().toString();
                pwLogin = editTextPw.getText().toString();

                if(phoneLogin.isEmpty() || pwLogin.isEmpty()){
                    Toast.makeText(Login.this, "Please enter your phone or password!", Toast.LENGTH_LONG).show();
                }else{
                    databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            // check phone is exist in firebase
                            if(dataSnapshot.hasChild(phoneLogin)){
                                String getPassword = dataSnapshot.child(phoneLogin).child("Password").getValue(String.class);
                                if(getPassword.equals(pwLogin)){
                                    Toast.makeText(Login.this, "Successfully loged in!", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(Login.this, MainActivity.class));
                                }else{
                                    Toast.makeText(Login.this, "Wrong password!", Toast.LENGTH_LONG).show();
                                }
                            }else{
                                Toast.makeText(Login.this, "Wrong phone number!", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        btn_register_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });
    }
}