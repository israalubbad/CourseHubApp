package com.example.coursehubapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.coursehubapplication.RoomDatabase.Category;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;
import com.example.coursehubapplication.databinding.ActivityAddCategeryBinding;

public class AddCategeryActivity extends AppCompatActivity {
ActivityAddCategeryBinding binding;
int categoryId;
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
                    Toast.makeText(AddCategeryActivity.this, "Please enter Category Name", Toast.LENGTH_SHORT).show();
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


        if(getIntent().getBundleExtra("Category")!=null){
            Bundle bundle=getIntent().getBundleExtra("Category");
            categoryId=bundle.getInt("id");
            binding.nameCategoryET.setText(bundle.getString("name"));
            binding.editCategoryBT.setVisibility(View.VISIBLE);
            binding.addCategoryBT.setVisibility(View.GONE);
        }



        binding.editCategoryBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameCategory = binding.nameCategoryET.getText().toString();

                if (nameCategory.isEmpty()) {
                    Toast.makeText(AddCategeryActivity.this, "Please enter Category Name", Toast.LENGTH_SHORT).show();
                } else {

                    Category category = new Category(categoryId,nameCategory);
                    if (!viewModel.categoryUpdate(category)) {
                        Toast.makeText(AddCategeryActivity.this, "Successfully Edite", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(AddCategeryActivity.this, "Failed Edite", Toast.LENGTH_SHORT).show();
                    }

                }}

        });


        binding.back.setOnClickListener(view -> {
            finish();// علشان انهي الواجهة

        });
    }}