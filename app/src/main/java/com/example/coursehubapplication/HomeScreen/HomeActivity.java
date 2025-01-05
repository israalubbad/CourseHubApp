package com.example.coursehubapplication.HomeScreen;

import static com.example.coursehubapplication.Utils.inertCategory;
import static com.example.coursehubapplication.Utils.inertCourses;
import static com.example.coursehubapplication.Utils.inertEnrollUserInCourse;
import static com.example.coursehubapplication.Utils.inertUser;
import static com.example.coursehubapplication.Utils.insertBookmark;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.coursehubapplication.MainActivity;
import com.example.coursehubapplication.R;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;
import com.example.coursehubapplication.databinding.ActivityHomeBinding;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences=getSharedPreferences("course",MODE_PRIVATE);
        int userId=sharedPreferences.getInt("userId",-1);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
              HomeFragment.newInstance(userId)).commit();
        MyViewModel viewModel= new ViewModelProvider(this).get(MyViewModel.class);


        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.homeItem) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                            new HomeFragment()).commit();
                } else if (item.getItemId() == R.id.myCourseItem) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                          new MyCourseFragment()).commit();
                } else if (item.getItemId() == R.id.profileItem) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                           new ProfileFragment()).commit();
                }

                return true;

            }
        });

    }




}