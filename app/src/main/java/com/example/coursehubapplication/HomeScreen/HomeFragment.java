package com.example.coursehubapplication.HomeScreen;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.coursehubapplication.Adapter.CourseAdapter;
import com.example.coursehubapplication.Adapter.HomeCourseAdapter;
import com.example.coursehubapplication.Adapter.ViewPagerAdapter;
import com.example.coursehubapplication.DashboardScreen.ViewCoursesActivity;
import com.example.coursehubapplication.DashboardScreen.ViewLessonActivity;
import com.example.coursehubapplication.R;
import com.example.coursehubapplication.RoomDatabase.Category;
import com.example.coursehubapplication.RoomDatabase.Course;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;

import com.example.coursehubapplication.RoomDatabase.User;
import com.example.coursehubapplication.databinding.FragmentHomeBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HomeFragment extends Fragment implements HomeCourseAdapter.ClickListener {
    int pos;
    private static final String ARG_PARAM1 = "userId";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<Integer> categoryId;
    ArrayList<Fragment> fragments;
    ArrayList<String> tabs;
    private int userId;
    private String mParam2;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(int userId) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getInt(ARG_PARAM1);
        }
        tabs = new ArrayList<>();
        fragments = new ArrayList<>();
        tabs.add("All");
        fragments.add(RecyclerViewFragment.newInstance(0));


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentHomeBinding binding = FragmentHomeBinding.inflate(inflater, container, false);
        MyViewModel viewModel = new ViewModelProvider(this).get(MyViewModel.class);

        SharedPreferences preferences = requireContext().getSharedPreferences("course", Context.MODE_PRIVATE);
        int userId = preferences.getInt("userId", -1);
        viewModel.getUserId(userId).observe(getViewLifecycleOwner(), user -> {
            binding.userNameTV.setText(user.getUserName());
            Glide.with(getContext())
                    .load(user.getUserPhoto())
                    .circleCrop()
                    .into(binding.imageUser);

        });


        ViewPagerAdapter adapter = new ViewPagerAdapter(this, fragments);
        binding.viewPager.setAdapter(adapter);
        viewModel.getAllCategories().observe(getViewLifecycleOwner(), categories -> {
            if (categories.size()< 4) {
                binding.tabs.setTabMode(TabLayout.MODE_FIXED);
            } else {
                binding.tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
            }
            if (categories != null) {
                for (Category category : categories) {
                    tabs.add(category.getCategoryName());
                    fragments.add(RecyclerViewFragment.newInstance(category.getCategoryId()));
                }
            }
            adapter.notifyDataSetChanged();

            new TabLayoutMediator(binding.tabs, binding.viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
                @Override
                public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                    tab.setText(tabs.get(position));
                }
            }).attach();

        });


        return binding.getRoot();
    }

    @Override
    public void courseClick(Course course) {

    }

    @Override
    public void onClick(int categoryId) {

    }
}