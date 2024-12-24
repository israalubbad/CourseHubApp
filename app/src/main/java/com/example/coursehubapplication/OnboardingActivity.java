package com.example.coursehubapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.coursehubapplication.databinding.ActivityOnboardingBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class OnboardingActivity extends AppCompatActivity {
ActivityOnboardingBinding binding;
int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivityOnboardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ArrayList<Fragment> fragments=new ArrayList<>();
        fragments.add(new Onboarding1Fragment());
        fragments.add(new Onboarding2Fragment());
        fragments.add(new Onboarding3Fragment());

        OnboardingAdapter onboardingAdapter = new OnboardingAdapter(OnboardingActivity.this, fragments);
        binding.viewPager.setAdapter(onboardingAdapter);

        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                i=position;
                if (position == 2) {
                    binding.nextBT.setVisibility(View.GONE);
                    binding.skipBT.setVisibility(View.GONE);
                } else {
                    binding.nextBT.setVisibility(View.VISIBLE);
                    binding.skipBT.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.nextBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getItem(0) < 2)
                    binding.viewPager.setCurrentItem(getItem(1), true);
                else {
                    Intent i = new Intent(getBaseContext(), Onboarding3Fragment.class);
                    startActivity(i);
                    finish();
                }
            }
        });



        binding.skipBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        }
    private int getItem(int i) {
        return binding.viewPager.getCurrentItem() + i;
    }
}