package com.example.coursehubapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursehubapplication.HomeScreen.CourseDetailsActivity;
import com.example.coursehubapplication.HomeScreen.HomeActivity;
import com.example.coursehubapplication.HomeScreen.MyLessonActivity;
import com.example.coursehubapplication.RoomDatabase.Bookmark;
import com.example.coursehubapplication.RoomDatabase.Course;
import com.example.coursehubapplication.RoomDatabase.Lesson;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;
import com.example.coursehubapplication.RoomDatabase.UserCourseEnrolled;
import com.example.coursehubapplication.Utils;
import com.example.coursehubapplication.databinding.MyCoursesItemBinding;

import java.util.List;

public class MyCoursesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<UserCourseEnrolled> userCourseEnrolled;
    Context context;
    MyCoursesItemBinding binding;
    int totalLessons;



    public MyCoursesAdapter(List<UserCourseEnrolled> userCourseEnrolled, Context context) {
        this.userCourseEnrolled = userCourseEnrolled;
        this.context = context;
    }


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
        UserCourseEnrolled courseEnrolled = userCourseEnrolled.get(position);
        int courseId = courseEnrolled.getCourseId();
        viewModel.getCourseById(courseId).observe((HomeActivity) context, listCourse -> {
            viewHolder.binding.courseTitle.setText(listCourse.getCourseTitle());
            viewHolder.binding.courseHoursTv.setText(listCourse.getCourseHours() + " Hours");
            viewHolder.binding.coursePhotoTV.setImageBitmap(listCourse.getCourseImage());

            viewModel.getLessonsByCourseId(courseId).observe((HomeActivity) context, lessonList -> {
                totalLessons = lessonList.size();

                viewHolder.binding.progressBar.setProgress((int) ((float) courseEnrolled.getProgressIndicator() / totalLessons * 100));
                viewHolder.binding.progressNumber.setText(courseEnrolled.getProgressIndicator() + " / " + totalLessons);
            });

            viewHolder.binding.deleteCourseTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String textMassage="Are you sure you want to remove this Course?";
                    String key="joinCourse";
                    AlertDialog.Builder builder = Utils.getBuilder(viewModel,userCourseEnrolled.get(position),textMassage,key, context);
                    AlertDialog dialog = builder.create();
                    dialog.setCancelable(true);
                    dialog.show();

                }
            });


            viewHolder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MyLessonActivity.class);
                    intent.putExtra("courseId", courseEnrolled.getCourseId());
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


}

