package com.example.coursehubapplication.LoginScreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.coursehubapplication.DashboardScreen.DashboardActivity;
import com.example.coursehubapplication.HomeScreen.HomeActivity;
import com.example.coursehubapplication.R;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;
import com.example.coursehubapplication.RoomDatabase.User;
import com.example.coursehubapplication.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
ActivityLoginBinding binding;
    boolean isPasswordViseble=false;
    MyViewModel viewModel;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.rememberSwitch.setChecked(false);

        viewModel= new ViewModelProvider(this).get(MyViewModel.class);

        sharedPreferences=getSharedPreferences("course",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        if(sharedPreferences.getBoolean("remembered",false)){
            Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
            intent.putExtra("userId",sharedPreferences.getInt("userId",-1));
            startActivity(intent);

        }

        binding.loginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=binding.emailEt.getText().toString();
                String password=binding.passwordEt.getText().toString();
                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "fill all fields", Toast.LENGTH_SHORT).show();
                }else {
                    viewModel.getUserByEmailAndPassword(email,password).observe(LoginActivity.this, new Observer<User>() {
                        @Override
                        public void onChanged(User user) {
                            if(user!= null){

                                if(binding.rememberSwitch.isChecked()){
                                  editor.putBoolean("remembered",true).apply();
                                 editor.putInt("userId", user.getUserId()).apply();
                                 editor.putInt("userId", user.getUserId()).apply();

                                }

                                if (user.isAdmin()) {
                                    Intent intent=new Intent(LoginActivity.this, DashboardActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
                                // علشان يعرض بيانات المستخدم الخاصة فيه
                                intent.putExtra("userId",user.getUserId());
                                editor.putInt("userId", user.getUserId()).apply();
                                startActivity(intent);
                                finish();
                                }
                            }else{
                                Toast.makeText(LoginActivity.this, "check email or password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


        binding.signUpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getBaseContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });



    }

    public interface UserLessener {
        void onClick(int userId);
    }

}