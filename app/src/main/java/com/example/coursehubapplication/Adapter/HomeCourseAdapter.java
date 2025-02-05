package com.example.coursehubapplication.Adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursehubapplication.HomeScreen.CourseDetailsActivity;
import com.example.coursehubapplication.HomeScreen.HomeActivity;
import com.example.coursehubapplication.R;
import com.example.coursehubapplication.RoomDatabase.Bookmark;
import com.example.coursehubapplication.RoomDatabase.Course;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;
import com.example.coursehubapplication.Utils;
import com.example.coursehubapplication.databinding.CourseviewItemBinding;

import java.util.List;

public class HomeCourseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Course> courseList;
    Context context;
    CourseviewItemBinding binding;
    Course course;
    Bookmark bookmarks;

    public HomeCourseAdapter(List<Course> courseList, Context context) {
        this.courseList = courseList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = CourseviewItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new HomeCourseAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewModel viewModel = new ViewModelProvider((HomeActivity) context).get(MyViewModel.class);
        Course course = courseList.get(position);
        HomeCourseAdapter.ViewHolder viewHolder = (HomeCourseAdapter.ViewHolder) holder;
        viewHolder.binding.courseTitle.setText(courseList.get(position).getCourseTitle());
        viewHolder.binding.courseDescription.setText(courseList.get(position).getCourseDescription());
        viewHolder.binding.instructorName.setText(courseList.get(position).getCourseInstructorName());
        viewHolder.binding.coursePhotoTV.setImageBitmap(courseList.get(position).getCourseImage());
        viewModel.getBookmarkByUserIdAndCourse(Utils.USERID,course.getCourseId()).observe((LifecycleOwner) context, Bookmark-> {
           if(Bookmark !=null) {
               bookmarks = Bookmark;
           }
        });

        viewModel.getIsBookmark(course.getCourseId(), Utils.USERID).observe((LifecycleOwner) context, isBookmarked -> {
            if (isBookmarked != null) {
                if (isBookmarked) {
                    viewHolder.binding.bookMarkIv.setImageResource(R.drawable.bookmark);
                } else {
                    viewHolder.binding.bookMarkIv.setImageResource(R.drawable.unbookmark);
                }

                viewHolder.binding.bookMarkIv.setOnClickListener(view -> {
                    if (!isBookmarked) {
                        Bookmark bookmark = new Bookmark(Utils.USERID, course.getCourseId());
                        viewModel.insertBookmark(bookmark);
                        Toast.makeText(context, "Bookmark added", Toast.LENGTH_SHORT).show();
                    } else {
                        String textMassage="Are you sure you want to remove this bookmark?";
                        String key="bookmarks";
                        AlertDialog.Builder builder = Utils.getBuilder(viewModel,null,courseList.get(position).getCourseId(),textMassage,key,context,-1,-1);
                        AlertDialog dialog = builder.create();
                        dialog.setCancelable(true);
                        dialog.show();
                    }
                });
            }
        });

        viewHolder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CourseDetailsActivity.class);
                intent.putExtra("courseId", courseList.get(position).getCourseId());
                context.startActivity(intent);

            }
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CourseviewItemBinding binding;
        ImageView more;

        public ViewHolder(CourseviewItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }


}