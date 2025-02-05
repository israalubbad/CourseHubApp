package com.example.coursehubapplication.HomeScreen;
import android.content.Intent;
import android.net.Uri;
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

            if(lesson.getArticleLink() != null){
                binding.articelLink.setVisibility(View.VISIBLE);
            }else {
                binding.articelLink.setVisibility(View.GONE);
            }

            binding.youtubeLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String youtubeLink = lesson.getLessonVideo();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink));
                    startActivity(intent);
                }
            });

            binding.articelLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String articleUrl = lesson.getArticleLink();
                    if (articleUrl != null) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(articleUrl));
                        startActivity(intent);
                    }
                }
            });
        });


    }
}