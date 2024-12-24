package com.example.coursehubapplication.Adapter;

import static com.example.coursehubapplication.RoomDatabase.Converters.getBitmapAsByteArray;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import com.example.coursehubapplication.AddCategeryActivity;
import com.example.coursehubapplication.AddCourseActivity;
import com.example.coursehubapplication.DashboardActivity;
import com.example.coursehubapplication.R;
import com.example.coursehubapplication.RoomDatabase.Category;
import com.example.coursehubapplication.RoomDatabase.Converters;
import com.example.coursehubapplication.RoomDatabase.Course;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;
import com.example.coursehubapplication.ViewAllCourseActivity;
import com.example.coursehubapplication.databinding.CategoryItemBinding;
import com.example.coursehubapplication.databinding.CourseItemBinding;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
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
        binding=CourseItemBinding.inflate(LayoutInflater.from(context),parent,false);
        return new CourseAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int courseId = courseList.get(position).getCourseId();
        CourseAdapter.ViewHolder viewHolder= (CourseAdapter.ViewHolder) holder;
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
                            Bundle bundle = new Bundle();
                            bundle.putInt("id",courseList.get(position).getCourseId());
                            bundle.putString("CourseDescription", courseList.get(position).getCourseDescription());
                            bundle.putInt("Category", courseList.get(position).getCourseCategory());
                            bundle.putString("CourseTitle", courseList.get(position).getCourseTitle());
                            bundle.putString("InstructorName", courseList.get(position).getCourseInstructorName());
                            bundle.putString("CourseHours", String.valueOf(courseList.get(position).getCourseHours()));
                            bundle.putString("CoursePrise", String.valueOf(courseList.get(position).getCoursePrice()));
                            bundle.putByteArray("image", Converters.getBitmapAsByteArray(courseList.get(position).getCourseImage()));
                            Intent intent = new Intent(context, AddCourseActivity.class);
                            intent.putExtra("course", bundle);
                            context.startActivity(intent);
                        } if (itemId == R.id.deleteItem) {
                            MyViewModel viewModel = new ViewModelProvider((ViewAllCourseActivity) context).get(MyViewModel.class);

                            AlertDialog.Builder builder = new AlertDialog.Builder((ViewAllCourseActivity) context);
                            builder.setTitle("Confirmation");
                            builder.setMessage("Are you sure you want to delete this category?");
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText((ViewAllCourseActivity) context, "category deleted", Toast.LENGTH_SHORT).show();
                                    viewModel.deleteCourse(courseList.get(position));
                                }
                            });
                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText((ViewAllCourseActivity) context, "canceled", Toast.LENGTH_SHORT).show();
                                }
                            });
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
                clickListener.onClick(courseId);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CourseItemBinding binding;
        ImageView more;
        public ViewHolder(CourseItemBinding binding ) {
            super(binding.getRoot());
            this.binding=binding;

        }}

    @Override
    public int getItemCount() {
        return courseList.size();
    }
    public interface ClickListener {
        void onClick(int courseId);
    }

    }
