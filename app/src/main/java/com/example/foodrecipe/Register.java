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

public class Register extends AppCompatActivity {

    TextInputEditText editEmail, editPw, editPhone, editConPw, editName;
    TextView btn_login_now;
    Button btn_register;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foodrecipe-ltddnc-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editEmail = findViewById(R.id.email);
        editName = findViewById(R.id.fullname);
        editPw = findViewById(R.id.password);
        editConPw = findViewById(R.id.conPassword);
        editPhone = findViewById(R.id.phone);
        btn_login_now = findViewById(R.id.btnLoginNow);
        btn_register = findViewById(R.id.btnRegister);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmail.getText().toString();
                String password = editPw.getText().toString();
                String conPassword = editConPw.getText().toString();
                String phone = editPhone.getText().toString();
                String fullname = editName.getText().toString();

                if(email.isEmpty() || password.isEmpty() || conPassword.isEmpty() || phone.isEmpty() || fullname.isEmpty()){
                    Toast.makeText(Register.this, "Vui lòng điền dầy dủ tông tin", Toast.LENGTH_LONG).show();
                }
                else if (!password.equals(conPassword)){
                    Toast.makeText(Register.this, "Mật khẩu không khớp", Toast.LENGTH_LONG).show();
                }
                else{
                    databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //check phone is registered before
                            if(dataSnapshot.hasChild(phone)){
                                Toast.makeText(Register.this, "Số điện thoại đã đăng ký!", Toast.LENGTH_LONG).show();
                            }else {
                                databaseReference.child("Users").child(phone).child("Fullname").setValue(fullname);
                                databaseReference.child("Users").child(phone).child("Email").setValue(email);
                                databaseReference.child("Users").child(phone).child("Password").setValue(password);
                                databaseReference.child("Users").child(phone).child("PhoneNumber").setValue(phone);

                                Toast.makeText(Register.this, "Đăng ký thành công", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Register.this, Login.class));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        btn_login_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}