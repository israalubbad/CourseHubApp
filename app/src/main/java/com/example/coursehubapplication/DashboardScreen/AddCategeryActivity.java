package com.example.coursehubapplication.DashboardScreen;

import android.content.Intent;
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

import com.example.coursehubapplication.Adapter.CategoriesAdapter;
import com.example.coursehubapplication.HomeScreen.HomeFragment;
import com.example.coursehubapplication.R;
import com.example.coursehubapplication.RoomDatabase.Category;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;
import com.example.coursehubapplication.databinding.ActivityAddCategeryBinding;

public class AddCategeryActivity extends AppCompatActivity {
    ActivityAddCategeryBinding binding;
    int categoryId;
    int getCategoryId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAddCategeryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MyViewModel viewModel = new ViewModelProvider(this).get(MyViewModel.class);

        binding.addCategoryBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameCategory = binding.nameCategoryET.getText().toString();

                if (nameCategory.isEmpty()) {
                    binding.nameCategoryET.setError("Please enter Category Name");
                } else {

                    Category category = new Category(nameCategory);
                    if (!viewModel.categoryInsert(category)) {
                        Toast.makeText(AddCategeryActivity.this, "Successfully Add", Toast.LENGTH_SHORT).show();
                        binding.nameCategoryET.setText("");

                    } else {
                        Toast.makeText(AddCategeryActivity.this, "Failed Add", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


        if (getIntent().getIntExtra("category",-1) == 101) {
            categoryId=getIntent().getIntExtra("categoryId",-1);
            viewModel.getCategoryById(categoryId).observe(AddCategeryActivity.this, new Observer<Category>() {
                @Override
                public void onChanged(Category category) {
                    binding.nameCategoryET.setText(category.getCategoryName());
                }
            });
            binding.editCategoryBT.setVisibility(View.VISIBLE);
            binding.addCategoryBT.setVisibility(View.GONE);
        }


        binding.editCategoryBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameCategory = binding.nameCategoryET.getText().toString();

                if (nameCategory.isEmpty()) {
                    binding.nameCategoryET.setError("Please enter Category Name");
                } else {

                    Category category = new Category(categoryId, nameCategory);
                    if (!viewModel.categoryUpdate(category)) {
                        Toast.makeText(AddCategeryActivity.this, "Successfully Edite", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(AddCategeryActivity.this, "Failed Edite", Toast.LENGTH_SHORT).show();
                    }

                }
            }

        });


        binding.back.setOnClickListener(view -> {
            Intent intent=new Intent(AddCategeryActivity.this,DashboardActivity.class);
            startActivity(intent);
            finish();// علشان انهي الواجهة

        });


    }


}