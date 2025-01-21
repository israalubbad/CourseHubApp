package com.example.coursehubapplication.HomeScreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coursehubapplication.Adapter.HomeCourseAdapter;
import com.example.coursehubapplication.LoginScreen.LoginActivity;
import com.example.coursehubapplication.RoomDatabase.Bookmark;
import com.example.coursehubapplication.RoomDatabase.Course;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;
import com.example.coursehubapplication.RoomDatabase.User;
import com.example.coursehubapplication.databinding.FragmentProfileragmentBinding;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    int i;
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentProfileragmentBinding binding = FragmentProfileragmentBinding.inflate(getLayoutInflater());
        MyViewModel viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        SharedPreferences preferences = requireContext().getSharedPreferences("course", Context.MODE_PRIVATE);
        int userId = preferences.getInt("userId", -1);
        viewModel.getUserId(userId).observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                binding.userNameTV.setText(user.getUserName());
                binding.userEmailTV.setText(user.getUserEmail());

            }
        });

        viewModel.getBookmarkByUserId(userId).observe(getViewLifecycleOwner(), new Observer<List<Bookmark>>() {
            @Override
            public void onChanged(List<Bookmark> bookmarks) {
                ArrayList<Course> courses = new ArrayList<>();
                for (Bookmark bookmark : bookmarks) {
                    viewModel.getCourseById(bookmark.getCourseId()).observe(getViewLifecycleOwner(), new Observer<Course>() {
                        @Override
                        public void onChanged(Course course) {
                            if (course != null) {
                                courses.add(course);
                            }
                            if (courses.size()== bookmarks.size()) {
                                binding.savedCoursesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                HomeCourseAdapter adapter = new HomeCourseAdapter(courses, getContext(), null, userId);
                                binding.savedCoursesRecyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }
        });


        binding.editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditeProfileActivity.class);
                startActivity(intent);
            }
        });

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("course", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("userId");
                editor.apply();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });


        return binding.getRoot();

    }
}