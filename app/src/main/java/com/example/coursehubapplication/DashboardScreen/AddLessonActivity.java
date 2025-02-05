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
import androidx.lifecycle.ViewModelProvider;

import com.example.coursehubapplication.R;
import com.example.coursehubapplication.RoomDatabase.Lesson;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;
import com.example.coursehubapplication.databinding.ActivityAddLessonBinding;

public class AddLessonActivity extends AppCompatActivity {
    ActivityAddLessonBinding binding;
    int courseId;
    int lessonId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAddLessonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        courseId = intent.getIntExtra("courseId", -1);

        MyViewModel viewModel = new ViewModelProvider(this).get(MyViewModel.class);

        binding.addLessonBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lessonTitle = binding.lessonTitleET.getText().toString().trim();
                String lessonDescription = binding.lessonDescriptionET.getText().toString().trim();
                String articleLink = binding.articleLinkET.getText().toString().trim();
                String lessonVideo = binding.lessonVideoEt.getText().toString().trim();
                if (lessonTitle.isEmpty()) {
                    binding.lessonTitleET.setError("Please enter Lesson Title");
                    return;
                }
                if (lessonDescription.isEmpty()) {
                    binding.lessonDescriptionET.setError("Please enter Lesson Description");
                    return;
                }
                if (lessonVideo.isEmpty()) {
                    binding.lessonVideoEt.setError("Please enter Lesson Video");
                    return;
                }

                Lesson lesson = new Lesson(lessonTitle, lessonDescription, lessonVideo, articleLink, courseId, System.currentTimeMillis(), true);
                if (viewModel.insertLesson(lesson)) {
                    Toast.makeText(AddLessonActivity.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                    binding.lessonTitleET.setText("");
                    binding.lessonDescriptionET.setText("");
                    binding.lessonVideoEt.setText("");
                    binding.articleLinkET.setText("");
                } else {
                    Toast.makeText(AddLessonActivity.this, "Failed to Add", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (getIntent().getIntExtra("lesson",-1) == 103) {
            lessonId= getIntent().getIntExtra("lessonId",-1);
            viewModel.getLessonById(lessonId).observe(AddLessonActivity.this , lesson -> {
                courseId = lesson.getCourseId();
                binding.articleLinkET.setText(lesson.getArticleLink());
                binding.lessonDescriptionET.setText(lesson.getLessonDescription());
                binding.lessonTitleET.setText(lesson.getLessonTitle());
                binding.lessonVideoEt.setText(lesson.getLessonVideo());
            });
            binding.editLessonBT.setVisibility(View.VISIBLE);
            binding.addLessonBT.setVisibility(View.GONE);
        }


        binding.editLessonBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lessonTitle = binding.lessonTitleET.getText().toString().trim();
                String lessonDescription = binding.lessonDescriptionET.getText().toString().trim();
                String articleLink = binding.articleLinkET.getText().toString().trim();
                String lessonVideo = binding.lessonVideoEt.getText().toString().trim();
                if (lessonTitle.isEmpty()) {
                    binding.lessonTitleET.setError("Please enter Lesson Title");
                    return;
                }
                if (lessonDescription.isEmpty()) {
                    binding.lessonDescriptionET.setError("Please enter Lesson Description");
                    return;
                }
                if (lessonVideo.isEmpty()) {
                    binding.lessonVideoEt.setError("Please enter Lesson Video");
                    return;
                }

                Lesson lesson = new Lesson(lessonTitle, lessonDescription, lessonVideo, articleLink, courseId, System.currentTimeMillis(), true);
                if (viewModel.updateLesson(lesson)) {
                    Toast.makeText(AddLessonActivity.this, "Successfully Edite", Toast.LENGTH_SHORT).show();
                    binding.lessonTitleET.setText("");
                    binding.lessonDescriptionET.setText("");
                    binding.lessonVideoEt.setText("");
                    binding.articleLinkET.setText("");
                } else {
                    Toast.makeText(AddLessonActivity.this, "Failed to Edite", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.back.setOnClickListener(view -> {
            finish();

        });
    }

}
