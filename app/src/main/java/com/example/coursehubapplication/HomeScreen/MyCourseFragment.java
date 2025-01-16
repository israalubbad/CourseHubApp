package com.example.coursehubapplication.HomeScreen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coursehubapplication.Adapter.ViewPagerAdapter;
import com.example.coursehubapplication.R;
import com.example.coursehubapplication.RoomDatabase.Category;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;
import com.example.coursehubapplication.databinding.FragmentMyCourseBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyCourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyCourseFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyCourseFragment() {
        // Required empty public constructor
    }
    public static MyCourseFragment newInstance(String param1, String param2) {
        MyCourseFragment fragment = new MyCourseFragment();
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
       FragmentMyCourseBinding binding = FragmentMyCourseBinding.inflate(inflater, container, false);
        ArrayList<String> tabs = new ArrayList<>();
        tabs.add("Ongoing");
        tabs.add("Completed");
        ArrayList<Fragment> fragments=new ArrayList<>();
        fragments.add(new OngoingFragment());
        fragments.add(new CompletedFragment());
       ViewPagerAdapter adapter = new ViewPagerAdapter(this, fragments);
       binding.viewPager.setAdapter(adapter);
       adapter.notifyDataSetChanged();

                new TabLayoutMediator(binding.tabLayout, binding.viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        tab.setText(tabs.get(position));
                    }
                }).attach();

       return binding.getRoot();
    }
}