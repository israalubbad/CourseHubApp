package com.example.coursehubapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursehubapplication.HomeScreen.HomeActivity;
import com.example.coursehubapplication.HomeScreen.MyLessonActivity;
import com.example.coursehubapplication.RoomDatabase.Bookmark;
import com.example.coursehubapplication.RoomDatabase.Course;
import com.example.coursehubapplication.RoomDatabase.Lesson;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;
import com.example.coursehubapplication.RoomDatabase.UserCourseEnrolled;
import com.example.coursehubapplication.databinding.MyCoursesItemBinding;

import java.util.List;

public class MyCoursesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<UserCourseEnrolled> userCourseEnrolled;
    Context context;
    MyCoursesItemBinding binding;

    int userId;
    Course course;
    Bookmark bookmark;
    HomeCourseAdapter.ClickListener clickListener;
    boolean isBookMark = false;

    public MyCoursesAdapter(List<UserCourseEnrolled> userCourseEnrolled, Context context, CourseAdapter.ClickListener clickListener, int userId) {
        this.userCourseEnrolled = userCourseEnrolled;
        this.context = context;
        this.userId = userId;
    }
    int completedLessons = 0;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = MyCoursesItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyCoursesAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewModel viewModel = new ViewModelProvider((HomeActivity) context).get(MyViewModel.class);
        MyCoursesAdapter.ViewHolder viewHolder = (MyCoursesAdapter.ViewHolder) holder;
        UserCourseEnrolled course = userCourseEnrolled.get(position);
        int courseId = course.getCourseId();
        viewModel.getCourseById(course.getCourseId()).observe((HomeActivity) context,listCourse -> {

            viewHolder.binding.courseTitle.setText(listCourse.getCourseTitle());
            viewHolder.binding.courseHoursTv.setText(listCourse.getCourseHours()+"");
            viewHolder.binding.coursePhotoTV.setImageBitmap(listCourse.getCourseImage());
            viewHolder.binding.progressBar.setProgress(course.getProgressIndicator());
            viewHolder.binding.progressNumber.setText(course.getProgressIndicator()+ " % " );

            viewHolder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MyLessonActivity.class);
                    intent.putExtra("courseId", course.getCourseId());
                    context.startActivity(intent);

                }
            });
        });

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        MyCoursesItemBinding binding;
        ImageView more;

        public ViewHolder(MyCoursesItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    @Override
    public int getItemCount() {
        return userCourseEnrolled.size();
    }

    public interface ClickListener {
        void courseClick(Course course);

        void onClick(int courseId);

    }


}

