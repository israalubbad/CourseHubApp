package com.example.coursehubapplication.HomeScreen;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.coursehubapplication.R;
import com.example.coursehubapplication.databinding.ActivityHomeBinding;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {
ActivityHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                new HomeFragment()).commit();


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