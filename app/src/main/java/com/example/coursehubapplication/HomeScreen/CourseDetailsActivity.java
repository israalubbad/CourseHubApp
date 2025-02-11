package com.example.coursehubapplication.HomeScreen;

import android.os.Bundle;

import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.coursehubapplication.R;
import com.example.coursehubapplication.RoomDatabase.Bookmark;
import com.example.coursehubapplication.RoomDatabase.Category;
import com.example.coursehubapplication.RoomDatabase.Course;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;
import com.example.coursehubapplication.RoomDatabase.UserCourseEnrolled;
import com.example.coursehubapplication.Utils;
import com.example.coursehubapplication.databinding.ActivityCourseDetailsBinding;
import java.util.List;

public class CourseDetailsActivity extends AppCompatActivity {
    ActivityCourseDetailsBinding binding;
    int categoryId;
    int id;
    boolean joined = false;
    Bookmark bookmarks;
    UserCourseEnrolled userCourseEnrolled1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityCourseDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        int courseId = getIntent().getIntExtra("courseId", -1);

        MyViewModel viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        viewModel.getBookmarkByUserIdAndCourse(Utils.USERID,courseId).observe(CourseDetailsActivity.this,bookmark -> {
            if(bookmark != null){
                bookmarks=bookmark;}
        });


        viewModel.getCourseById(courseId).observe(this, new Observer<Course>() {
            @Override
            public void onChanged(Course course) {
                binding.coursePhotoIV.setImageBitmap(course.getCourseImage());
                binding.courseDescriptionTv.setText(course.getCourseDescription());
                binding.courseTitleTV.setText(course.getCourseTitle());
                binding.instructorNameTV.setText(course.getCourseInstructorName());
                binding.courseHoursTV.setText(course.getCourseHours() + " Hours");
                binding.coursePriseTV.setText("$ " + course.getCoursePrice());
                viewModel.getCategoryById(course.getCourseCategory()).observe(CourseDetailsActivity.this, new Observer<Category>() {
                    @Override
                    public void onChanged(Category category) {
                        if (courseId != -1 && category != null) {
                            binding.categeryTV.setText(category.getCategoryName());
                        }
                    }
                });
            }
        });


        viewModel.getUsersByCourseId(courseId).observe(this, new Observer<List<UserCourseEnrolled>>() {
            @Override
            public void onChanged(List<UserCourseEnrolled> userCourseEnrolleds) {
                if (userCourseEnrolleds != null) {
                    binding.nomberofStudentTV.setText(userCourseEnrolleds.size() + " Students");
                } else {
                    binding.nomberofStudentTV.setText(0 + " Students");
                }

            }
        });

        viewModel.getIsBookmark(courseId, Utils.USERID).observe((LifecycleOwner) this, isBookmarked -> {
            if (isBookmarked != null) {
                if (isBookmarked) {
                    binding.bookMarkIv.setImageResource(R.drawable.bookmark);
                } else {
                    binding.bookMarkIv.setImageResource(R.drawable.unbookmark);
                }
            }
            binding.bookMarkIv.setOnClickListener(view -> {
                if (!isBookmarked) {
                    Bookmark bookmark = new Bookmark(Utils.USERID, courseId);
                    viewModel.insertBookmark(bookmark);
                    Toast.makeText(this, "Bookmark added", Toast.LENGTH_SHORT).show();
                } else {
                    String textMassage="Are you sure you want to remove this bookmark?";
                    String key="bookmark";
                    AlertDialog.Builder builder = Utils.getBuilder(viewModel,bookmarks,textMassage,key,CourseDetailsActivity.this);
                    AlertDialog dialog = builder.create();
                    dialog.setCancelable(true);
                    dialog.show();
                    return;
                }
            });
        });
        viewModel.getUserEnrolledInCourse(Utils.USERID, courseId).observe(CourseDetailsActivity.this, userCourseEnrolled -> {
            if(userCourseEnrolled != null) {
                userCourseEnrolled1 = userCourseEnrolled;
            } });

        viewModel.isAlreadyEnrolled(Utils.USERID, courseId).observe(CourseDetailsActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isEnrolled) {
                int enrolledColor = ContextCompat.getColor(CourseDetailsActivity.this, R.color.yourThumbColor);
                int dropColor = ContextCompat.getColor(CourseDetailsActivity.this, R.color.primaryColor);

                if (isEnrolled != null && isEnrolled) {
                    binding.enrollCourseBT.setBackgroundColor(enrolledColor);
                    binding.enrollCourseBT.setText("Drop out Course");
                }else{
                    binding.enrollCourseBT.setText("Enroll Course");
                    binding.enrollCourseBT.setBackgroundColor(dropColor);

                }

                binding.enrollCourseBT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (! isEnrolled) {
                            UserCourseEnrolled enrolled = new UserCourseEnrolled(Utils.USERID, courseId, 0,System.currentTimeMillis());
                            viewModel.insertEnrollUserInCourse(enrolled);
                            Toast.makeText(CourseDetailsActivity.this, "Enrolled Course", Toast.LENGTH_SHORT).show();
                            joined =true;
                        } else if (isEnrolled){
                    String textMassage="Are you sure you want to remove this Course?";
                    String key="joinCourse";
                    AlertDialog.Builder builder = Utils.getBuilder(viewModel,userCourseEnrolled1, textMassage,key,CourseDetailsActivity.this);
                    AlertDialog dialog = builder.create();
                    dialog.setCancelable(true);
                    dialog.show();

                            }
                    }
                });

            }
        });



        binding.backIV.setOnClickListener(view ->{
            finish();
        } );

    }





}