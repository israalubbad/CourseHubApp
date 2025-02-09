package com.example.coursehubapplication.Adapter;

import android.content.Context;

import android.content.Intent;

import android.view.LayoutInflater;

import android.view.ViewGroup;

import android.widget.ImageView;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LifecycleOwner;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursehubapplication.HomeScreen.LessonDetailsActivity;
import com.example.coursehubapplication.HomeScreen.MyLessonActivity;

import com.example.coursehubapplication.R;

import com.example.coursehubapplication.RoomDatabase.Lesson;
import com.example.coursehubapplication.RoomDatabase.LessonUser;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;
import com.example.coursehubapplication.RoomDatabase.UserCourseEnrolled;
import com.example.coursehubapplication.Utils;

import com.example.coursehubapplication.databinding.MyLessonItemBinding;

import java.util.List;

public class MyLessonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Lesson> lessonList;
    Context context;
    MyLessonItemBinding binding;
    int enrolledId;

    public MyLessonAdapter(List<Lesson> lessonList, Context context) {
        this.lessonList = lessonList;
        this.context = context;

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

        viewModel.getUserEnrolledInCourse(Utils.USERID, lessonList.get(position).getCourseId()).observe((LifecycleOwner) context, userCourseEnrolled -> {
            if (userCourseEnrolled != null) {

                viewModel.getIsCompleted(userCourseEnrolled.getEnrolledCourseId(), lessonList.get(position).getLessonId()).observe((LifecycleOwner) context, isCompleted -> {
                    if (isCompleted != null) {
                        if (isCompleted) {
                            viewHolder.binding.checkBox.setImageResource(R.drawable.check);
                        } else {
                                viewHolder.binding.checkBox.setImageResource(R.drawable.check_mark);
                            }

                    }
                });

                viewModel.getLessonUser(userCourseEnrolled.getEnrolledCourseId(), lessonList.get(position).getLessonId()).observe((LifecycleOwner) context,lessonUser -> {
                    viewHolder.binding.checkBox.setOnClickListener(view ->{
                        if (lessonUser == null) {
                            viewModel.insertLessonUser(new LessonUser(userCourseEnrolled.getEnrolledCourseId(), lessonList.get(position).getLessonId()));
                            Toast.makeText(context, "Checked lesson", Toast.LENGTH_SHORT).show();
                        } else{
                            String textMassage = "Are you sure you want to unChecked lesson ?";
                            String key = "userLesson";
                            AlertDialog.Builder builder = Utils.getBuilder(viewModel, lessonUser, textMassage, key, context);
                            AlertDialog dialog = builder.create();
                            dialog.setCancelable(true);
                            dialog.show();
                            return;
                        }
                        viewModel.getCompletedLesson(userCourseEnrolled.getEnrolledCourseId()).observe((LifecycleOwner) context, completed -> {
                            if (completed != null) {
                                int total=lessonList.size();
                                int progress = (int) ((completed.size() / (float) total) * 100);
                                UserCourseEnrolled updatedEnrollment = new UserCourseEnrolled(userCourseEnrolled.getEnrolledCourseId(), Utils.USERID, lessonList.get(position).getCourseId(), progress,System.currentTimeMillis());
                                viewModel.updateEnrollUserInCourse(updatedEnrollment);
                            }

                        });

                    });
                });

            }
        });





        viewHolder.binding.getRoot().setOnClickListener(view -> {
            Intent intent = new Intent(context, LessonDetailsActivity.class);
            intent.putExtra("lessonId", lessonList.get(position).getLessonId());
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

}

