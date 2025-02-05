package com.example.coursehubapplication.LoginScreen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
import com.example.coursehubapplication.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {
ActivitySignUpBinding binding;

    MyViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewModel= new ViewModelProvider(this).get(MyViewModel.class);

        binding.singUpBt.setOnClickListener(view -> {
            String fullName=binding.userNameEt.getText().toString();
            String email=binding.emailEt.getText().toString();
            String password=binding.passwordEt.getText().toString();
            String confirtPassword=binding.comfortPasswordEt.getText().toString();
            if(fullName.isEmpty()){
                binding.emailEt.setError("Please enter Full Name");
            }else if(password.isEmpty()){
                binding.passwordEt.setError("Please enter Password");
                Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();
            }else if(confirtPassword.isEmpty()){
                binding.passwordEt.setError("Please enter Comfort Password");
                Toast.makeText(this, "Please enter Comfort Password", Toast.LENGTH_SHORT).show();
            }else if(password.isEmpty()){
                binding.emailIL.setError("Please enter Email");
            }else{
                if(password.equals(confirtPassword)){
                    viewModel.getUserByEmail(email).observe(SignUpActivity.this, new Observer<User>() {
                        @Override
                        public void onChanged(User user) {
                            if(user == null){
                                Bitmap userPhoto= BitmapFactory.decodeResource(getResources(), R.drawable.photo);
                                viewModel.userInsert(new User(fullName,email,password,userPhoto,false));
                                finish();
                            }else{
                                Toast.makeText(SignUpActivity.this, "Email already exist", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else{
                    Toast.makeText(SignUpActivity.this, "password and comfort password don't match ", Toast.LENGTH_SHORT).show();
                }
            }
        });


// اختصار

        binding.loginTv.setOnClickListener(view -> {
            //flage علشان يحذف كل اشي تحتيها و فتحت لاساسية
            startActivity(new Intent(getBaseContext(), LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

        });

        binding.backIV.setOnClickListener(view -> {
            finish();// علشان انهي الواجهة

        });



    }
}