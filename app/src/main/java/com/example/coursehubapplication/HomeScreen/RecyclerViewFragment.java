package com.example.coursehubapplication.HomeScreen;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.USER_SERVICE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.coursehubapplication.Adapter.HomeCourseAdapter;
import com.example.coursehubapplication.RoomDatabase.Course;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;
import com.example.coursehubapplication.Utils;
import com.example.coursehubapplication.databinding.FragmentRecyclerViewBinding;


import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecyclerViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecyclerViewFragment extends Fragment {
    private static final String ARG_CATEGORY_ID = "categoryId";


    private int categoryId;


    public RecyclerViewFragment() {
    }

    public static RecyclerViewFragment newInstance(int categoryId ) {

        RecyclerViewFragment fragment = new RecyclerViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CATEGORY_ID, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryId = getArguments().getInt(ARG_CATEGORY_ID);
        }
    }
    HomeCourseAdapter adapter;
    SharedPreferences preferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentRecyclerViewBinding binding = FragmentRecyclerViewBinding.inflate(inflater, container, false);

        binding.recyclerViewCourseRv.setLayoutManager(new LinearLayoutManager(requireContext()));
        MyViewModel viewModel = new ViewModelProvider(this).get(MyViewModel.class);
            viewModel.getAllCourses().observe(getViewLifecycleOwner(), new Observer<List<Course>>() {
                @Override
                public void onChanged(List<Course> courseList) {
                    if (categoryId == 0) {
                        adapter = new HomeCourseAdapter(courseList, requireContext(), null, Utils.USERID);
                        binding.recyclerViewCourseRv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
            });

            viewModel.getCoursesByCategoryId(categoryId).observe(getViewLifecycleOwner(), new Observer<List<Course>>() {
                @Override
                public void onChanged(List<Course> courseList) {
                    adapter = new HomeCourseAdapter(courseList, requireContext(), null,Utils.USERID);
                    binding.recyclerViewCourseRv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }
            });


        return binding.getRoot();
    }
}
