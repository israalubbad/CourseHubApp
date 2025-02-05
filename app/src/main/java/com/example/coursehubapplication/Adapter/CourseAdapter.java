package com.example.coursehubapplication.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursehubapplication.DashboardScreen.AddCourseActivity;
import com.example.coursehubapplication.DashboardScreen.DashboardActivity;
import com.example.coursehubapplication.DashboardScreen.ViewCoursesActivity;
import com.example.coursehubapplication.R;
import com.example.coursehubapplication.RoomDatabase.Course;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;
import com.example.coursehubapplication.Utils;
import com.example.coursehubapplication.databinding.CourseItemBinding;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Course> courseList;
    Context context;
    CourseAdapter.ClickListener clickListener;
    CourseItemBinding binding;


    public CourseAdapter(List<Course> courseList, Context context, CourseAdapter.ClickListener clickListener) {
        this.courseList = courseList;
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = CourseItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new CourseAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CourseAdapter.ViewHolder viewHolder = (CourseAdapter.ViewHolder) holder;
        viewHolder.binding.courseTitle.setText(courseList.get(position).getCourseTitle());
        viewHolder.binding.courseDescription.setText(courseList.get(position).getCourseDescription());
        viewHolder.binding.instructorName.setText(courseList.get(position).getCourseInstructorName());
        viewHolder.binding.moreIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(), viewHolder.binding.moreIcon);
                popupMenu.inflate(R.menu.edite_delete_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("NonConstantResourceId")
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int itemId = item.getItemId();
                        if (itemId == R.id.editeItem) {
                            Intent intent = new Intent(context, AddCourseActivity.class);
                            intent.putExtra("course", 102);
                            intent.putExtra("courseId", courseList.get(position).getCourseId());
                            context.startActivity(intent);
                        }
                        if (itemId == R.id.deleteItem) {
                            MyViewModel viewModel = new ViewModelProvider((ViewCoursesActivity) context).get(MyViewModel.class);
                            String textMessage = "Are you sure you want to delete this course?";
                            String key = "course";
                            AlertDialog.Builder builder = Utils.getBuilder(viewModel, courseList.get(position),-1, textMessage, key, (DashboardActivity) context,-1,-1);
                            AlertDialog dialog = builder.create();
                            dialog.setCancelable(true);
                            dialog.show();


                        }
                        return false;
                    }
                });
                popupMenu.show();

            }
        });
        viewHolder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onClick(courseList.get(position).getCourseId());
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CourseItemBinding binding;
        ImageView more;

        public ViewHolder(CourseItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public interface ClickListener {
        void onClick(int courseId);
    }


}
