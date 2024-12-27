package com.example.coursehubapplication.OnbordingScreen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coursehubapplication.databinding.FragmentOnboarding1Binding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Onboarding1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Onboarding1Fragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Onboarding1Fragment() {
        // Required empty public constructor
    }


    public static Onboarding1Fragment newInstance(String param1, String param2) {
        Onboarding1Fragment fragment = new Onboarding1Fragment();
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
        FragmentOnboarding1Binding binding=FragmentOnboarding1Binding.inflate(inflater,container, false);
        return binding.getRoot();
    }
}