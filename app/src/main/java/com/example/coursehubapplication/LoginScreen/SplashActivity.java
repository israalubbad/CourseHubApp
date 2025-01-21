package com.example.coursehubapplication.LoginScreen;

import static com.example.coursehubapplication.Utils.inertCategory;
import static com.example.coursehubapplication.Utils.inertCourses;
import static com.example.coursehubapplication.Utils.inertEnrollUserInCourse;
import static com.example.coursehubapplication.Utils.inertLesson;
import static com.example.coursehubapplication.Utils.inertUser;
import static com.example.coursehubapplication.Utils.insertBookmark;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.coursehubapplication.HomeScreen.HomeActivity;
import com.example.coursehubapplication.MainActivity;
import com.example.coursehubapplication.OnbordingScreen.OnboardingActivity;
import com.example.coursehubapplication.R;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences sharedPreferences = getSharedPreferences("Onboarding", MODE_PRIVATE);
        boolean isFirst = sharedPreferences.getBoolean("isFirst", true);
        SharedPreferences sharedPreferences2 = getSharedPreferences("course", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences2.edit();;
        boolean isFirstData = sharedPreferences2.getBoolean("isFirstData", true);
        editor.putInt("userId", 4).apply();
        MyViewModel viewModel = new ViewModelProvider(this).get(MyViewModel.class);

        if (isFirstData) {
            insertDatabase(viewModel, getResources());
            editor.putBoolean("isFirstData", false);
            editor.apply();
        }



//        if (isFirst) {
//            startActivity(new Intent(this, OnboardingActivity.class));
//            finish();
//        } else {
//
//            setContentView(R.layout.activity_main);
//        }

        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        new Handler(Looper.getMainLooper()).postDelayed(this::finish, 2000);
    }

    public static void insertDatabase(MyViewModel viewModel, Resources resources) {
        new Thread(() -> {
            inertUser(viewModel, resources);
            inertCategory(viewModel);
            inertCourses(viewModel, resources);
            inertLesson(viewModel);
            insertBookmark(viewModel);
            inertEnrollUserInCourse(viewModel);
        }).start();}
}