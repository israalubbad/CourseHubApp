package com.example.coursehubapplication.DashboardScreen;

import android.content.Intent;
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

import com.example.coursehubapplication.Adapter.CourseAdapter;
import com.example.coursehubapplication.R;
import com.example.coursehubapplication.RoomDatabase.Course;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;
import com.example.coursehubapplication.databinding.ActivityViewCoursesBinding;

import java.util.List;

public class ViewCoursesActivity extends AppCompatActivity implements CourseAdapter.ClickListener {
    ActivityViewCoursesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityViewCoursesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        int categoryId = intent.getIntExtra("categoryId", -1);
        MyViewModel viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        binding.recyclerViewCourseRv.setLayoutManager(new LinearLayoutManager(ViewCoursesActivity.this,
                RecyclerView.VERTICAL, false));

        viewModel.getCoursesByCategoryId(categoryId).observe(ViewCoursesActivity.this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courseList) {
                CourseAdapter adapter = new CourseAdapter(courseList, ViewCoursesActivity.this, ViewCoursesActivity.this::onClick);
                binding.recyclerViewCourseRv.setAdapter(adapter);

            }

        });

        binding.addCourseBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewCoursesActivity.this, AddCourseActivity.class);
                intent.putExtra("categoryId", categoryId);
                startActivity(intent);


            }
        });


    }

    @Override
    public void onClick(int courseId) {
        Intent intent = new Intent(ViewCoursesActivity.this, ViewLessonActivity.class);
        intent.putExtra("course", courseId);
        startActivity(intent);
    }

}