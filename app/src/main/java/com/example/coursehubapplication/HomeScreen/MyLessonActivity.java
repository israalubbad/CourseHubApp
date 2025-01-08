package com.example.coursehubapplication.HomeScreen;

import android.content.Context;
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

import com.example.coursehubapplication.Adapter.HomeCourseAdapter;
import com.example.coursehubapplication.Adapter.MyCoursesAdapter;
import com.example.coursehubapplication.Adapter.MyLessonAdapter;
import com.example.coursehubapplication.R;
import com.example.coursehubapplication.RoomDatabase.Course;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;
import com.example.coursehubapplication.databinding.ActivityMyLessonBinding;
import com.example.coursehubapplication.databinding.FragmentRecyclerViewBinding;

import java.util.List;

public class MyLessonActivity extends AppCompatActivity {
    ActivityMyLessonBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyLessonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences preferences = getSharedPreferences("course", Context.MODE_PRIVATE);
        int userId = preferences.getInt("userId", -1);
        int courseId = getIntent().getIntExtra("courseId", -1);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyViewModel viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        viewModel.getLessonsByCourseId(courseId).observe(this, lessonList -> {
            MyLessonAdapter adapter = new MyLessonAdapter(lessonList, this,  userId);
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