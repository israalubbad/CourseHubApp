package com.example.coursehubapplication.DashboardScreen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.coursehubapplication.R;
import com.example.coursehubapplication.RoomDatabase.Category;
import com.example.coursehubapplication.RoomDatabase.Course;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;
import com.example.coursehubapplication.databinding.ActivityAddCourseBinding;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddCourseActivity extends AppCompatActivity {
    ActivityAddCourseBinding binding;
    Bitmap bitmap;
    int categoryId;
    int courseHours;
    double coursePrise;
    int courseId;
    List<String> categoryNames;
    List<Category> categoryList;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                binding.imageCourseIV.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAddCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        MyViewModel viewModel = new ViewModelProvider(this).get(MyViewModel.class);

        Intent intent = getIntent();
        categoryId = intent.getIntExtra("categoryId", -1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.addCategorySp.setAdapter(adapter);
// احط التصنيفات بالسبينر
        viewModel.getAllCategories().observe(this, categories -> {
            categoryList = categories;
            categoryNames = new ArrayList<>();
            for (Category category : categories) {
                categoryNames.add(category.getCategoryName());
            }
            adapter.clear();
            adapter.addAll(categoryNames);
            adapter.notifyDataSetChanged();

            if (getIntent().getIntExtra("course",-1) ==102) {
                binding.editCourseBT.setVisibility(View.VISIBLE);
                binding.addCourseBT.setVisibility(View.GONE);
                courseId = getIntent().getIntExtra("courseId",-1);
                viewModel.getCourseById(courseId).observe(AddCourseActivity.this, course -> {
                    binding.nameCourseET.setText(course.getCourseTitle());
                    binding.courseDescriptionET.setText(course.getCourseDescription());
                    binding.instructorNameEt.setText(course.getCourseInstructorName());
                    binding.courseHoursET.setText(String.valueOf(course.getCourseHours()));
                    binding.coursePriseET.setText(String.valueOf(course.getCoursePrice()));
                    binding.imageCourseIV.setImageBitmap(course.getCourseImage());
                    bitmap=course.getCourseImage();
                    categoryId = course.getCourseCategory();
                        });


            }
            // علشان يختاره يلي انا دخلت عليه
            if (categoryId != -1) {
                for (int i = 0; i < categories.size(); i++) {
                    if (categories.get(i).getCategoryId() == categoryId) {
                        binding.addCategorySp.setSelection(i);
                        break;
                    }
                }
            }


            binding.addCategorySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedCategoryName = parent.getItemAtPosition(position).toString();
                    for (Category category : categories) {
                        if (category.getCategoryName().equals(selectedCategoryName)) {
                          // بجيب يلي اختاره المستخدم
                            categoryId = category.getCategoryId();
                            break;
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    categoryId = -1;
                }
            });
        });


        binding.imageCourseIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });


        binding.addCourseBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String courseTitle = binding.nameCourseET.getText().toString().trim();
                String courseDescription = binding.courseDescriptionET.getText().toString().trim();
                String instructorName = binding.instructorNameEt.getText().toString().trim();
                String hours = binding.courseHoursET.getText().toString().trim();
                String price = binding.coursePriseET.getText().toString().trim();

                if (courseTitle.isEmpty()) {
                    binding.nameCourseET.setError("Please enter Course Title");
                    return;
                }
                if (courseDescription.isEmpty()) {
                    binding.courseDescriptionET.setError("Please enter Course Description");
                    return;
                }
                if (instructorName.isEmpty()) {
                    binding.instructorNameEt.setError("Please enter Instructor Name");
                    return;
                }
                if (hours.isEmpty()) {
                    binding.courseHoursET.setError("Please enter Course Hours");
                    return;
                }
                if (price.isEmpty()) {
                    binding.coursePriseET.setError("Please enter Course Price");
                    return;
                }
                if (bitmap == null) {
                    Toast.makeText(AddCourseActivity.this, "Please select a Course Photo", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    courseHours = Integer.parseInt(hours);
                } catch (NumberFormatException e) {
                    binding.courseHoursET.setError("Invalid number format for Course Hours");
                    return;
                }

                try {
                    coursePrise = Double.parseDouble(price);
                } catch (NumberFormatException e) {
                    binding.coursePriseET.setError("Invalid number format for Course Price");
                    return;
                }

                Course course = new Course(courseTitle, courseDescription, instructorName, bitmap, coursePrise, courseHours, categoryId);

                if (viewModel.insertCourse(course)) {
                    Toast.makeText(AddCourseActivity.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                    binding.nameCourseET.setText("");
                    binding.courseDescriptionET.setText("");
                    binding.instructorNameEt.setText("");
                    binding.courseHoursET.setText("");
                    binding.coursePriseET.setText("");
                    binding.imageCourseIV.setImageResource(R.drawable.baseline_camera_alt_24);
                } else {
                    Toast.makeText(AddCourseActivity.this, "Failed to Add", Toast.LENGTH_SHORT).show();
                }

            }});
                binding.editCourseBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseTitle = binding.nameCourseET.getText().toString().trim();
                String courseDescription = binding.courseDescriptionET.getText().toString().trim();
                String instructorName = binding.instructorNameEt.getText().toString().trim();
                String hours = binding.courseHoursET.getText().toString().trim();
                String price = binding.coursePriseET.getText().toString().trim();

                if (courseTitle.isEmpty()) {
                    binding.nameCourseET.setError("Please enter Course Title");
                    return;
                }
                if (courseDescription.isEmpty()) {
                    binding.courseDescriptionET.setError("Please enter Course Description");
                    return;
                }
                if (instructorName.isEmpty()) {
                    binding.instructorNameEt.setError("Please enter Instructor Name");
                    return;
                }
                if (hours.isEmpty()) {
                    binding.courseHoursET.setError("Please enter Course Hours");
                    return;
                }
                if (price.isEmpty()) {
                    binding.coursePriseET.setError("Please enter Course Price");
                    return;
                }
                if (bitmap == null) {
                    Toast.makeText(AddCourseActivity.this, "Please enter Course Photo", Toast.LENGTH_SHORT).show();
                    return;
                }
                int courseHours;
                double coursePrise;

                try {
                    courseHours = Integer.parseInt(hours);
                } catch (NumberFormatException e) {
                    binding.courseHoursET.setError("Invalid format for Course Hours");
                    return;
                }

                try {
                    coursePrise = Double.parseDouble(price);
                } catch (NumberFormatException e) {
                    binding.coursePriseET.setError("Invalid format for Course Price");
                    return;
                }

                Course course = new Course(courseId, courseTitle, courseDescription, instructorName, bitmap, coursePrise, courseHours, categoryId);

                if (viewModel.updateCourse(course)) {
                    Toast.makeText(AddCourseActivity.this, "Successfully Edited", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddCourseActivity.this, "Failed to Edit", Toast.LENGTH_SHORT).show();
                }}
        });

        binding.back.setOnClickListener(view -> {
            finish();

        });

    }

}




