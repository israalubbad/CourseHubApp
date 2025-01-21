package com.example.coursehubapplication.HomeScreen;

import static com.example.coursehubapplication.Utils.inertCategory;
import static com.example.coursehubapplication.Utils.inertCourses;
import static com.example.coursehubapplication.Utils.inertEnrollUserInCourse;
import static com.example.coursehubapplication.Utils.inertUser;
import static com.example.coursehubapplication.Utils.insertBookmark;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.coursehubapplication.MainActivity;
import com.example.coursehubapplication.R;
import com.example.coursehubapplication.RoomDatabase.Lesson;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;
import com.example.coursehubapplication.RoomDatabase.UserCourseEnrolled;
import com.example.coursehubapplication.databinding.ActivityHomeBinding;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;
    SharedPreferences sharedPreferences;
    public static final String CHANNEL_ID="Navigation";
    public static int courseIdSave;
    int coursesId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences("course", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                HomeFragment.newInstance(userId)).commit();
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


        viewModel.getCoursesByUserIdList(userId).observe(HomeActivity.this, userCourseEnrolled -> {
            List<Integer> courseIds = new ArrayList<>();
            for (UserCourseEnrolled course : userCourseEnrolled) {
                courseIds.add(course.getCourseId());
            }


            if (userCourseEnrolled.size() == courseIds.size()) {
                for (int courseId : courseIds) {
                    viewModel.getLessonsByCourseId(courseId).observe(HomeActivity.this, lessons -> {
                        for (Lesson lesson : lessons) {
                            if (lesson.isAdminAdded() && !isNotificationShown(userId,lesson.getLessonId())) {
                                viewModel.getCourseById(courseId).observe(HomeActivity.this, courses -> {
                                    String courseTitle = courses.getCourseTitle();
                                    showNotification(lesson.getLessonTitle(), courseTitle, courseId,lesson.getLessonId());
                                    courseIdSave=courseId;
                                    saveNotificationShown(userId,lesson.getLessonId());
                                });
                            }
                        }
                    });
                }
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

        Toast.makeText(this, courseId +" ff", Toast.LENGTH_SHORT).show();
        Log.d("MyLessonActivity", "Received course" + courseId);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pi = PendingIntent.getActivity(HomeActivity.this,courseId, intent, PendingIntent.FLAG_IMMUTABLE);

        // هو يلي بنشا يبني الاشعار
        NotificationCompat.Builder builder = new NotificationCompat.Builder(HomeActivity.this, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.baseline_camera_alt_24);
        builder.setContentTitle("New Lesson");
        builder.setContentText("Add lesson " + lessonTitle + " at the " + courseTitle);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.addAction(R.drawable.logo_course, "Open", pi);
        builder.setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(HomeActivity.this);
        managerCompat.notify(lessonId, builder.build());

    }
    private boolean isNotificationShown(int userId, int lessonId) {
        SharedPreferences prefs = getSharedPreferences("notifications", MODE_PRIVATE);
        return prefs.getBoolean(userId + "_" + lessonId, false);
    }

    private void saveNotificationShown(int userId, int lessonId) {
        SharedPreferences prefs = getSharedPreferences("notifications", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(userId + "_" + lessonId, true);
        editor.apply();
    }


}