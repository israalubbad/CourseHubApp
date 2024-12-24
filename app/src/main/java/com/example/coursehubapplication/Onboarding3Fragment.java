package com.example.coursehubapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coursehubapplication.databinding.FragmentOnboarding1Binding;
import com.example.coursehubapplication.databinding.FragmentOnboarding3Binding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Onboarding3Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Onboarding3Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Onboarding3Fragment() {
        // Required empty public constructor
    }

    public static Onboarding3Fragment newInstance(String param1, String param2) {
        Onboarding3Fragment fragment = new Onboarding3Fragment();
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
        FragmentOnboarding3Binding binding=FragmentOnboarding3Binding.inflate(inflater,container, false);
        binding.startBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
        return binding.getRoot();
    }
}