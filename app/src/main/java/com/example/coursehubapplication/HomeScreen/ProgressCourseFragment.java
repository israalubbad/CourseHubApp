package com.example.coursehubapplication.HomeScreen;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coursehubapplication.Adapter.MyCoursesAdapter;
import com.example.coursehubapplication.R;
import com.example.coursehubapplication.RoomDatabase.Lesson;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;
import com.example.coursehubapplication.RoomDatabase.UserCourseEnrolled;
import com.example.coursehubapplication.databinding.FragmentOngoingBinding;
import com.example.coursehubapplication.databinding.FragmentProgressCourseBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class ProgressCourseFragment extends Fragment {

    private static final String ARG_PARAM1 = "num";
    private int key;
    private String mParam2;
    int userId;
    MyViewModel viewModel;
    MyCoursesAdapter adapter;
    int i = 0;
    int totalLessons=0;
    int completedLesson=0;

    public ProgressCourseFragment() {

    }

    public static ProgressCourseFragment newInstance(int key) {
        ProgressCourseFragment fragment = new ProgressCourseFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, key);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            key = getArguments().getInt(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentProgressCourseBinding binding = FragmentProgressCourseBinding.inflate(inflater, container, false);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("course", MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);
        viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewModel.getCoursesByUserIdList(userId).observe(getViewLifecycleOwner(), enrolledCourses -> {
            List<UserCourseEnrolled> ongoingCourse = new ArrayList<>();
            List<UserCourseEnrolled> completedCourse = new ArrayList<>();
            for (UserCourseEnrolled courseEnrolled : enrolledCourses) {
                int courseId = courseEnrolled.getCourseId();
                viewModel.getLessonsByCourseId(courseId).observe(getViewLifecycleOwner(), lessonList -> {
                    if (lessonList != null) {
                        totalLessons = lessonList.size();
                    }

                    viewModel.getCompletedLesson(courseEnrolled.getEnrolledCourseId()).observe(getViewLifecycleOwner(), completedLessons -> {
                        if (completedLessons != null) {
                            completedLesson = completedLessons.size();
                        }

                        int progress = (int) ((completedLesson / (float) totalLessons) * 100);
                        courseEnrolled.setProgressIndicator(progress);
                        if (progress == 100) {
                            completedCourse.add(courseEnrolled);
                        } else {
                            ongoingCourse.add(courseEnrolled);
                        }
                        i++;
                        if (i == enrolledCourses.size()) {
                            if (key == 0) {
                                MyCoursesAdapter newAdapter = new MyCoursesAdapter(ongoingCourse, getContext(), userId);
                                binding.recyclerView.setAdapter(newAdapter);
                            } else if (key == 1) {
                                MyCoursesAdapter newAdapter = new MyCoursesAdapter(completedCourse, getContext(), userId);
                                binding.recyclerView.setAdapter(newAdapter);
                            }

                        }
                    });
                });
            }
        });

        return binding.getRoot();
    }
}