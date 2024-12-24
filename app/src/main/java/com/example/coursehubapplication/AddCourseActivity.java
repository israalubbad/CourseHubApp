package com.example.coursehubapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.coursehubapplication.RoomDatabase.Category;
import com.example.coursehubapplication.RoomDatabase.Course;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;
import com.example.coursehubapplication.databinding.ActivityAddCategeryBinding;
import com.example.coursehubapplication.databinding.ActivityAddCourseBinding;
import com.example.coursehubapplication.databinding.ActivityViewAllCourseBinding;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddCourseActivity extends AppCompatActivity {
ActivityAddCourseBinding binding;
Bitmap bitmap;
int categoryId;
int courseHours;
double coursePrise;
int courseId;

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
        }}



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivityAddCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        MyViewModel viewModel= new ViewModelProvider(this).get(MyViewModel.class);

        Intent intent=  getIntent();
        categoryId =intent.getIntExtra("categoryId",-1);
        Toast.makeText(this, "ca"+ categoryId, Toast.LENGTH_SHORT).show();
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
                String courseTitle = binding.nameCourseET.getText().toString();
                String courseDescription=binding.courseDescriptionET.getText().toString();
                String  instructorName=binding.instructorNameEt.getText().toString();
                String hours =binding.courseHoursET.getText().toString();
                String prise =binding.coursePriseET.getText().toString();
                try {
                    courseHours =Integer.parseInt(hours);
                    coursePrise =Double.parseDouble(prise);
                } catch (NumberFormatException e) {
                    System.out.println("Failed to parse the number: " + e.getMessage());
                    e.printStackTrace();
                }

                if (courseTitle.isEmpty()) {
                    Toast.makeText(AddCourseActivity.this, "Please enter Course Title", Toast.LENGTH_SHORT).show();
                }  if (courseDescription.isEmpty()) {
                    Toast.makeText(AddCourseActivity.this, "Please enter Course Description", Toast.LENGTH_SHORT).show();
                }  if (instructorName.isEmpty()) {
                    Toast.makeText(AddCourseActivity.this, "Please enter Course instructor Name", Toast.LENGTH_SHORT).show();
                }  if (hours.isEmpty()) {
                    Toast.makeText(AddCourseActivity.this, "Please enter Course Hours", Toast.LENGTH_SHORT).show();
                }  if (prise.isEmpty()) {
                    Toast.makeText(AddCourseActivity.this, "Please enter Course Prise", Toast.LENGTH_SHORT).show();
                }if (bitmap == null) {
                    Toast.makeText(AddCourseActivity.this, "Please select a Course Photo", Toast.LENGTH_SHORT).show();
                }else {
                    Course course =new Course(courseTitle,courseDescription,instructorName,bitmap,coursePrise,courseHours,categoryId);
                    if(!viewModel.insertCourse(course)) {
                        Toast.makeText(AddCourseActivity.this, "Successfully Add", Toast.LENGTH_SHORT).show();
                        binding.nameCourseET.setText("");
                        binding.instructorNameEt.setText("");
                        binding.courseDescriptionET.setText("");
                        binding.courseHoursET.setText("");
                        binding.coursePriseET.setText("");
                        binding.imageCourseIV.setImageResource(R.drawable.baseline_camera_alt_24);
                    }else{
                        Toast.makeText(AddCourseActivity.this, "Failed Add", Toast.LENGTH_SHORT).show();
                    }
                }}
        });

        if(getIntent().getBundleExtra("course")!=null){
            Bundle bundle=getIntent().getBundleExtra("course");
            courseId=bundle.getInt("id");
            categoryId=bundle.getInt("Category");
            binding.nameCourseET.setText(bundle.getString("CourseTitle"));
            binding.courseDescriptionET.setText(bundle.getString("CourseDescription"));
            binding.instructorNameEt.setText(bundle.getString("InstructorName"));
            binding.coursePriseET.setText(bundle.getString("CoursePrise"));
            binding.courseHoursET.setText(bundle.getString("CourseHours"));
            byte[] img=bundle.getByteArray("image");
            bitmap = BitmapFactory.decodeByteArray(img,0,img.length);
            binding.imageCourseIV.setImageBitmap(bitmap);
            binding.editCourseBT.setVisibility(View.VISIBLE);
            binding.addCourseBT.setVisibility(View.GONE);
        }



        binding.editCourseBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseTitle = binding.nameCourseET.getText().toString();
                String courseDescription = binding.courseDescriptionET.getText().toString();
                String instructorName = binding.instructorNameEt.getText().toString();
                String hours = binding.courseHoursET.getText().toString();
                String prise = binding.coursePriseET.getText().toString();
                try {
                    courseHours = Integer.parseInt(hours);
                    coursePrise = Double.parseDouble(prise);
                } catch (NumberFormatException e) {
                    System.out.println("Failed to parse the number: " + e.getMessage());
                    e.printStackTrace();
                }

                if (courseTitle.isEmpty()) {
                    Toast.makeText(AddCourseActivity.this, "Please enter Course Title", Toast.LENGTH_SHORT).show();
                }
                if (courseDescription.isEmpty()) {
                    Toast.makeText(AddCourseActivity.this, "Please enter Course Description", Toast.LENGTH_SHORT).show();
                }
                if (instructorName.isEmpty()) {
                    Toast.makeText(AddCourseActivity.this, "Please enter Course instructor Name", Toast.LENGTH_SHORT).show();
                }
                if (hours.isEmpty()) {
                    Toast.makeText(AddCourseActivity.this, "Please enter Course Hours", Toast.LENGTH_SHORT).show();
                }
                if (prise.isEmpty()) {
                    Toast.makeText(AddCourseActivity.this, "Please enter Course Prise", Toast.LENGTH_SHORT).show();
                }
                if (bitmap == null) {
                    Toast.makeText(AddCourseActivity.this, "Please enter Course Photo", Toast.LENGTH_SHORT).show();
                } else {
                    Course course = new Course(courseId,courseTitle, courseDescription, instructorName, bitmap, coursePrise, courseHours, categoryId);
                    if (!viewModel.updateCourse(course)) {
                        Toast.makeText(AddCourseActivity.this, "Successfully Edite", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddCourseActivity.this, "Failed Edite", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.back.setOnClickListener(view -> {
            finish();

        });
    }


}

