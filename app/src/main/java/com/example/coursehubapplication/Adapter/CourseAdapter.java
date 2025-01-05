package com.example.coursehubapplication.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursehubapplication.DashboardScreen.AddCourseActivity;
import com.example.coursehubapplication.DashboardScreen.ViewCoursesActivity;
import com.example.coursehubapplication.R;
import com.example.coursehubapplication.RoomDatabase.Course;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;
import com.example.coursehubapplication.databinding.CourseItemBinding;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Course> courseList;
    Context context;
    CourseAdapter.ClickListener clickListener;
    CourseItemBinding binding;
    Course course;

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
                            AlertDialog.Builder builder = getAlertDialog(viewModel, position);
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

    private AlertDialog.Builder getAlertDialog(MyViewModel viewModel, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to delete this category?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText((ViewCoursesActivity) context, "course deleted", Toast.LENGTH_SHORT).show();
                viewModel.deleteCourse(courseList.get(position));
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText((ViewCoursesActivity) context, "canceled", Toast.LENGTH_SHORT).show();
            }
        });

        return builder;
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
