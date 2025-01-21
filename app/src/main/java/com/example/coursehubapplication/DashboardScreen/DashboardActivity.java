package com.example.coursehubapplication.DashboardScreen;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursehubapplication.Adapter.CategoriesAdapter;
import com.example.coursehubapplication.LoginScreen.LoginActivity;
import com.example.coursehubapplication.R;
import com.example.coursehubapplication.RoomDatabase.Category;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;
import com.example.coursehubapplication.databinding.ActivityDashboardBinding;

import java.util.List;

public class DashboardActivity extends AppCompatActivity implements CategoriesAdapter.ClickListener {
    ActivityDashboardBinding binding;
    int categoryID;

    @Override
    public void onClick(int categoryId) {
        categoryID = categoryId;
        Intent intent = new Intent(DashboardActivity.this, ViewCoursesActivity.class);
        intent.putExtra("categoryId", categoryId);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MyViewModel viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        binding.recyclerViewCategoryRv.setLayoutManager(new LinearLayoutManager(DashboardActivity.this,
                RecyclerView.VERTICAL, false));

        viewModel.getAllCategories().observe(DashboardActivity.this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                CategoriesAdapter adapter = new CategoriesAdapter(categories, DashboardActivity.this, DashboardActivity.this::onClick);
                binding.recyclerViewCategoryRv.setAdapter(adapter);

            }

        });
        binding.addCategoryBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, AddCategeryActivity.class);
                startActivity(intent);
            }
        });

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences =getSharedPreferences("course", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("userId");
                editor.apply();
                Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

}