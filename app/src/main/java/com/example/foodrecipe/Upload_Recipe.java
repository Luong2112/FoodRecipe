package com.example.foodrecipe;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;

public class Upload_Recipe extends AppCompatActivity {
    ImageView imgFoodChoose;
    Uri imageUrl;
    EditText edName, edIngredient, edCook;
    ProgressBar progressBar;

    Button btnChooseImage;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Recipes");
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_recipe);

        imgFoodChoose = findViewById(R.id.iv_foodImage);
        edName = findViewById(R.id.txt_recipe_name);
        edIngredient = findViewById(R.id.txt_recipe_ingredient);
        edCook = findViewById(R.id.txt_recipe_cook);
        btnChooseImage = findViewById(R.id.btn_img_choose);

        progressBar = new ProgressBar(this);

        ActivityResultLauncher<Intent> photoPickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            imageUrl = data.getData();
                            imgFoodChoose.setImageURI(imageUrl);
                        }else {
                            Toast.makeText(Upload_Recipe.this, "Không có ảnh!", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                photoPickerLauncher.launch(photoPicker);
            }
        });

    }

    public void uploadImage() {
        if (imageUrl != null){
            String Name = edName.getText().toString();
            String Ingredient = edIngredient.getText().toString();
            String Cook = edCook.getText().toString();

            StorageReference imgReference = storageReference.child(System.currentTimeMillis()+ "." + getFileExtension(imageUrl));
            imgReference.putFile(imageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imgReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            FoodData foodData = new FoodData(Name, Ingredient, Cook, imageUrl.toString());
                            String key = databaseReference.push().getKey();
                            databaseReference.child(key).setValue(foodData);
                            Toast.makeText(Upload_Recipe.this, "Chia sẻ thành công!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Upload_Recipe.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Upload_Recipe.this, "Lỗi!", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }else {
            Toast.makeText(Upload_Recipe.this, "Bạn chưa chọn ảnh!", Toast.LENGTH_LONG).show();
        }
    }

    private String getFileExtension(Uri fileUri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(fileUri));
    }
    public void btnUpload(View view) {
        uploadImage();
    }
}