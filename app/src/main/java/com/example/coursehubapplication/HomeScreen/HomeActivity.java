package com.example.coursehubapplication.HomeScreen;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.coursehubapplication.R;
import com.example.coursehubapplication.RoomDatabase.Lesson;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;
import com.example.coursehubapplication.RoomDatabase.UserCourseEnrolled;
import com.example.coursehubapplication.Utils;
import com.example.coursehubapplication.databinding.ActivityHomeBinding;
import com.google.android.material.navigation.NavigationBarView;


public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;
    SharedPreferences sharedPreferences;
    public static final String CHANNEL_ID="Navigation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences("course", MODE_PRIVATE);
       Utils.USERID = sharedPreferences.getInt("userId", -1);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                HomeFragment.newInstance(Utils.USERID)).commit();
        MyViewModel viewModel = new ViewModelProvider(this).get(MyViewModel.class);


        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.homeItem) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                            new HomeFragment()).commit();
                } else if (item.getItemId() == R.id.myCourseItem) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                            new MyCourseFragment()).commit();
                } else if (item.getItemId() == R.id.profileItem) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                            new ProfileFragment()).commit();
                }

                return true;

            }
        });

        SharedPreferences preferences = getSharedPreferences("notifications", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        viewModel.getCoursesByUserIdList(Utils.USERID).observe(HomeActivity.this, userCourseEnrolledList -> {
            for (UserCourseEnrolled enrollment : userCourseEnrolledList) {
                int courseId = enrollment.getCourseId();
                long timeEnrolled = enrollment.getTimeEnrolled();

                viewModel.getLessonsAfterEnrolled(courseId, timeEnrolled).observe(HomeActivity.this, lessons -> {

                    for (Lesson lesson : lessons) {
                        Boolean isShow= preferences.getBoolean(Utils.USERID + "_" + lesson.getLessonId(), true);
                        if (lesson.isAdminAdded() && isShow ) {
                            viewModel.getCourseById(courseId).observe(HomeActivity.this, course -> {
                                String courseTitle = course.getCourseTitle();
                                showNotification(lesson.getLessonTitle(), courseTitle, courseId, lesson.getLessonId());
                                editor.putBoolean(Utils.USERID + "_" + lesson.getLessonId(), false).apply();

                            });
                        }
                    }
                });
            }
        });

    }


    @SuppressLint("MissingPermission")
    private void showNotification (String lessonTitle, String courseTitle, int courseId,int lessonId) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "Channel name", NotificationManager.IMPORTANCE_DEFAULT);
            // بصل لل manager يلي في سيستم
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        // علشان اعلق الانتنت عند ما يضغط على الاشعار يروح على شاشة
        Intent intent = new Intent(getBaseContext(), MyLessonActivity.class);
        intent.putExtra("courseId", courseId);
         PendingIntent pi = PendingIntent.getActivity(HomeActivity.this,courseId, intent, PendingIntent.FLAG_IMMUTABLE);

        // هو يلي بنشا يبني الاشعار
        NotificationCompat.Builder builder = new NotificationCompat.Builder(HomeActivity.this, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.course);
        builder.setContentTitle("New Lesson");
        builder.setContentText("Add lesson " + lessonTitle + " at the " + courseTitle);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.addAction(R.drawable.logo_course, "Open", pi);
        builder.setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(HomeActivity.this);
        managerCompat.notify(lessonId, builder.build());

    }


}