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

import com.example.coursehubapplication.Adapter.LessonAdapter;
import com.example.coursehubapplication.R;
import com.example.coursehubapplication.RoomDatabase.Lesson;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;
import com.example.coursehubapplication.databinding.ActivityViewLessonBinding;

import java.util.List;

public class ViewLessonActivity extends AppCompatActivity {
    ActivityViewLessonBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityViewLessonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        int courseId = intent.getIntExtra("course", -1);


        MyViewModel viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        binding.recyclerViewRv.setLayoutManager(new LinearLayoutManager(ViewLessonActivity.this,
                RecyclerView.VERTICAL, false));

        viewModel.getLessonsByCourseId(courseId).observe(ViewLessonActivity.this, new Observer<List<Lesson>>() {
            @Override
            public void onChanged(List<Lesson> lessonList) {

                LessonAdapter adapter = new LessonAdapter(lessonList, ViewLessonActivity.this);
                binding.recyclerViewRv.setAdapter(adapter);

            }

        });

        binding.addBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewLessonActivity.this, AddLessonActivity.class);
                intent.putExtra("courseId", courseId);
                startActivity(intent);

            }
        });

    }
}