package com.example.coursehubapplication.HomeScreen;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
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
import com.example.coursehubapplication.Utils;
import com.example.coursehubapplication.databinding.FragmentOngoingBinding;
import com.example.coursehubapplication.databinding.FragmentProgressCourseBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class ProgressCourseFragment extends Fragment {
    FragmentProgressCourseBinding binding;
    private static final String ARG_PARAM1 = "num";
    private int key;
    private String mParam2;
    int userId;
    MyViewModel viewModel;
    MyCoursesAdapter adapter;
    List<UserCourseEnrolled> ongoingCourse;
    List<UserCourseEnrolled> completedCourse;
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
       ongoingCourse = new ArrayList<>();
         completedCourse = new ArrayList<>();
  }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.getCoursesByUserIdList(Utils.USERID).observe(getViewLifecycleOwner(), enrolledCourses -> {
            ongoingCourse.clear();
            completedCourse.clear();

            for (UserCourseEnrolled courseEnrolled : enrolledCourses) {
                viewModel.getLessonsByCourseId(courseEnrolled.getCourseId()).observe(getViewLifecycleOwner(), lessonList -> {
                    viewModel.getCompletedLesson(courseEnrolled.getEnrolledCourseId()).observe(getViewLifecycleOwner(), completedLessons -> {

                        int progress = (int) ((completedLessons.size() / (float) lessonList.size()) * 100);
                        courseEnrolled.setProgressIndicator(progress);

                        if (progress == 100) {
                            completedCourse.add(courseEnrolled);
                        } else {
                            ongoingCourse.add(courseEnrolled);
                        }
                        if (ongoingCourse.size() + completedCourse.size() == enrolledCourses.size()) {
                            if (key == 0) {
                                MyCoursesAdapter newAdapter = new MyCoursesAdapter(ongoingCourse, getContext());
                                binding.recyclerView.setAdapter(newAdapter);
                            } else if (key == 1) {
                                MyCoursesAdapter newAdapter = new MyCoursesAdapter(completedCourse, getContext());
                                binding.recyclerView.setAdapter(newAdapter);
                            }
                        }

                    });
                });
            }
        });

    }


    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProgressCourseBinding.inflate(inflater, container, false);
            viewModel = new ViewModelProvider(this).get(MyViewModel.class);
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            return binding.getRoot();
        }


}
