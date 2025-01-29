package com.example.coursehubapplication.HomeScreen;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.coursehubapplication.Adapter.MyLessonAdapter;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;
import com.example.coursehubapplication.databinding.ActivityMyLessonBinding;

public class MyLessonActivity extends AppCompatActivity {
    ActivityMyLessonBinding binding;
    int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyLessonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        courseId = getIntent().getIntExtra("courseId", -1);

        MyViewModel viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        viewModel.getCourseById(courseId).observe(this,course -> {
            binding.titleCourseTv.setText(course.getCourseTitle());
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel.getLessonsByCourseId(courseId).observe(this, lessonList -> {
            MyLessonAdapter adapter = new MyLessonAdapter(lessonList, this);
            binding.recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();


        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            finish();

            }
        });


    }


}