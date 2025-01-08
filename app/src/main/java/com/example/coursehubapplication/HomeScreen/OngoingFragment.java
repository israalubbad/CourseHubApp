package com.example.coursehubapplication.HomeScreen;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coursehubapplication.Adapter.BookMarkAdapter;
import com.example.coursehubapplication.Adapter.MyCoursesAdapter;
import com.example.coursehubapplication.R;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;
import com.example.coursehubapplication.RoomDatabase.UserCourseEnrolled;
import com.example.coursehubapplication.databinding.FragmentOnboarding1Binding;
import com.example.coursehubapplication.databinding.FragmentOngoingBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OngoingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OngoingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OngoingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OngoingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OngoingFragment newInstance(String param1, String param2) {
        OngoingFragment fragment = new OngoingFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentOngoingBinding binding = FragmentOngoingBinding.inflate(getLayoutInflater());
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("course", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1);
        MyViewModel viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        viewModel.getCoursesByUserIdList(userId).observe(getViewLifecycleOwner(), enrolledCourses -> {

            List<UserCourseEnrolled> completedCourses = new ArrayList<>();
            completedCourses.clear();
            for (UserCourseEnrolled course : enrolledCourses) {
                if (course.getProgressIndicator() < 100) {
                    completedCourses.add(course);
                }
            }
            MyCoursesAdapter adapter = new MyCoursesAdapter(completedCourses, getContext(), null, userId);
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        });

        return binding.getRoot();
    }

}