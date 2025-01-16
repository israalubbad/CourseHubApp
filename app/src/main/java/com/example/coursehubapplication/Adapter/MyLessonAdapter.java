package com.example.coursehubapplication.Adapter;

import static com.example.coursehubapplication.Utils.inertCategory;
import static com.example.coursehubapplication.Utils.inertCourses;
import static com.example.coursehubapplication.Utils.inertEnrollUserInCourse;
import static com.example.coursehubapplication.Utils.inertLesson;
import static com.example.coursehubapplication.Utils.inertUser;
import static com.example.coursehubapplication.Utils.insertBookmark;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursehubapplication.DashboardScreen.AddLessonActivity;
import com.example.coursehubapplication.DashboardScreen.ViewLessonActivity;
import com.example.coursehubapplication.HomeScreen.HomeActivity;
import com.example.coursehubapplication.HomeScreen.LessonDetailsActivity;
import com.example.coursehubapplication.HomeScreen.MyLessonActivity;
import com.example.coursehubapplication.R;
import com.example.coursehubapplication.RoomDatabase.Lesson;
import com.example.coursehubapplication.RoomDatabase.LessonUser;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;
import com.example.coursehubapplication.RoomDatabase.UserCourseEnrolled;
import com.example.coursehubapplication.Utils;
import com.example.coursehubapplication.databinding.LessonItemBinding;
import com.example.coursehubapplication.databinding.MyLessonItemBinding;

import java.util.List;

public class MyLessonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Lesson> lessonList;
    Context context;
    MyLessonItemBinding binding;
    int userId;
    int lessonUserId;

    public MyLessonAdapter(List<Lesson> lessonList, Context context, int userId) {
        this.lessonList = lessonList;
        this.context = context;
        this.userId = userId;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = MyLessonItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyLessonAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyLessonAdapter.ViewHolder viewHolder = (MyLessonAdapter.ViewHolder) holder;
        MyViewModel viewModel = new ViewModelProvider((MyLessonActivity) context).get(MyViewModel.class);
        viewHolder.binding.lessonTitle.setText(lessonList.get(position).getLessonTitle());
        int courseId = lessonList.get(position).getCourseId();
        int lessonId = lessonList.get(position).getLessonId();
        int totalLessons = lessonList.size();



        viewModel.getUserEnrolledInCourse(userId, courseId).observe((LifecycleOwner) context, userCourseEnrolled -> {
            if (userCourseEnrolled != null) {
                int enrolledId = userCourseEnrolled.getEnrolledCourseId();
                viewModel.getIsCompleted(enrolledId, lessonId).observe((LifecycleOwner) context, isCompleted -> {
                    if (isCompleted != null) {
                        if (isCompleted) {
                            viewHolder.binding.checkBox.setChecked(isCompleted);
                        }
                        viewHolder.binding.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                            if (! isCompleted && isChecked) {
                                viewModel.insertLessonUser(new LessonUser(enrolledId, lessonId));
                                Toast.makeText(context, "Checked lesson", Toast.LENGTH_SHORT).show();
                            } else {
                                viewModel.deleteLessonUser(enrolledId, lessonId);
                                Toast.makeText(context, "unChecked lesson", Toast.LENGTH_SHORT).show();
                                viewHolder.binding.checkBox.setChecked(false);
                            }

                            viewModel.getCompletedLesson(enrolledId).observe((LifecycleOwner) context, completed -> {
                                if (totalLessons > 0) {
                                    int progress = (int) ((completed.size() / (float) totalLessons) * 100);
                                    viewModel.updateCourseProgress(userId, courseId, progress);
                                }

                            });
                        });
                    } });
            }
        });


        viewHolder.binding.getRoot().setOnClickListener(view -> {
            Intent intent = new Intent(context, LessonDetailsActivity.class);
            intent.putExtra("lessonId", lessonId);
            intent.putExtra("courseId", courseId);
            context.startActivity(intent);
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        MyLessonItemBinding binding;
        ImageView more;

        public ViewHolder(MyLessonItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    @Override
    public int getItemCount() {
        return lessonList.size();
    }
//
//    AlertDialog.Builder builder = new AlertDialog.Builder((MyLessonActivity) context);
//                                builder.setTitle("Confirmation");
//                                builder.setMessage("Are you sure you want to unChecked lesson?");
//                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//        @Override
//        public void onClick(DialogInterface dialogInterface, int i) {
//            viewModel.deleteLessonUser(enrolledId, lessonId);
//            Toast.makeText(context, "unChecked lesson", Toast.LENGTH_SHORT).show();
//            viewHolder.binding.checkBox.setChecked(false);
//        }
//    });
//                                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//        @Override
//        public void onClick(DialogInterface dialogInterface, int i) {
//            Toast.makeText((ViewLessonActivity) context, "canceled", Toast.LENGTH_SHORT).show();
//        }
//    });
//    AlertDialog dialog = builder.create();
//                                dialog.setCancelable(true);
//                                dialog.show();

}

