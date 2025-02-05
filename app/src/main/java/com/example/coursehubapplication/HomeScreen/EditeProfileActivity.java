package com.example.coursehubapplication.HomeScreen;

import android.content.Intent;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.coursehubapplication.R;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;
import com.example.coursehubapplication.RoomDatabase.User;
import com.example.coursehubapplication.Utils;
import com.example.coursehubapplication.databinding.ActivityEditeProfileBinding;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class EditeProfileActivity extends AppCompatActivity {
ActivityEditeProfileBinding binding;
Bitmap bitmap;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                binding.imageUserIV.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivityEditeProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        MyViewModel viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        viewModel.getUserId(Utils.USERID).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                bitmap=user.getUserPhoto();
                binding.userNameEt.setText(user.getUserName());
                binding.emailEt.setText(user.getUserEmail());
                binding.passwordEt.setText(user.getUserPassword());

                binding.imageUserIV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(intent, 1);
                    }
                });


                binding.editeProfileBT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String fullName=binding.userNameEt.getText().toString();
                        String email=binding.emailEt.getText().toString();
                        String Password=binding.passwordEt.getText().toString();
                        if(fullName.isEmpty() ) {
                           binding.userNameEt.setError("Please enter user name");
                        }  if(email.isEmpty() ) {
                            binding.emailEt.setError("Please enter email");
                        }  if(Password.isEmpty() ) {
                            binding.passwordEt.setError("Please enter password");
                        }if(bitmap ==null){
                            Toast.makeText(EditeProfileActivity.this, "please enter photo", Toast.LENGTH_SHORT).show();
                        }else{
                            viewModel.userUpdate(new User(Utils.USERID,fullName,email,Password,bitmap,false));
                            Toast.makeText(EditeProfileActivity.this, "Edit Profile ", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}