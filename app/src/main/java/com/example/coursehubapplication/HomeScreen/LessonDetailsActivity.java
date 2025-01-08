package com.example.coursehubapplication.HomeScreen;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.coursehubapplication.R;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;
import com.example.coursehubapplication.databinding.ActivityLessonDetailsBinding;

public class LessonDetailsActivity extends AppCompatActivity {
ActivityLessonDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivityLessonDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        int lessonId = getIntent().getIntExtra("lessonId",-1);
        MyViewModel viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        viewModel.getLessonById(lessonId).observe(this,lesson -> {
            binding.lessonTitleTv.setText(lesson.getLessonTitle());
            binding.descriptionLessonTV.setText(lesson.getLessonDescription());
            binding.youtubeLink.setText(lesson.getLessonVideo());
            if(lesson.getArticleLink() != null){
                binding.articelLink.setVisibility(View.VISIBLE);
                binding.articelLink.setText(lesson.getArticleLink());
            }else {
                binding.articelLink.setVisibility(View.GONE);
            }
        });

    }
}