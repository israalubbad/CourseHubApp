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
import com.example.coursehubapplication.DashboardScreen.DashboardActivity;
import com.example.coursehubapplication.DashboardScreen.ViewCoursesActivity;
import com.example.coursehubapplication.DashboardScreen.ViewLessonActivity;
import com.example.coursehubapplication.HomeScreen.HomeActivity;
import com.example.coursehubapplication.HomeScreen.LessonDetailsActivity;
import com.example.coursehubapplication.HomeScreen.MyLessonActivity;
import com.example.coursehubapplication.R;
import com.example.coursehubapplication.RoomDatabase.Bookmark;
import com.example.coursehubapplication.RoomDatabase.Category;
import com.example.coursehubapplication.RoomDatabase.Course;
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
    public static LessonUser lessonUsers;
    int enrolledId;
    int lessonId;
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
         lessonId = lessonList.get(position).getLessonId();
        int totalLessons = lessonList.size();

        viewModel.getUserEnrolledInCourse(Utils.USERID, courseId).observe((LifecycleOwner) context, userCourseEnrolled -> {
            if (userCourseEnrolled != null) {
                 enrolledId = userCourseEnrolled.getEnrolledCourseId();
                viewModel.getLessonUser(enrolledId, lessonId).observe((MyLessonActivity) context, lessonUser -> {
                    if (lessonUser != null) {
                        lessonUsers = lessonUser;
                    }
                });

                viewModel.getIsCompleted(enrolledId, lessonId).observe((LifecycleOwner) context, isCompleted -> {
                    if (isCompleted != null) {
                        if (isCompleted) {
                            viewHolder.binding.checkBox.setImageResource(R.drawable.check);
                        } else {
                                viewHolder.binding.checkBox.setImageResource(R.drawable.check_mark);
                            }


                        viewHolder.binding.checkBox.setOnClickListener(view ->{
                            if (! isCompleted ) {
                                viewModel.insertLessonUser(new LessonUser(enrolledId, lessonId));
                                Toast.makeText(context, "Checked lesson", Toast.LENGTH_SHORT).show();
                            } else{
                                String textMassage = "Are you sure you want to unChecked lesson ?";
                                String key = "userLesson";
                                AlertDialog.Builder builder = Utils.getBuilder(viewModel, lessonUsers, textMassage, key, context);
                                AlertDialog dialog = builder.create();
                                dialog.setCancelable(true);
                                dialog.show();
                                return;

                            }
                            viewModel.getCompletedLesson(enrolledId).observe((LifecycleOwner) context, completed -> {
                                if (completed != null) {
                                    Toast.makeText(context, "dd"+completed.size(), Toast.LENGTH_SHORT).show();
                                    viewModel.updateEnrollUserInCourse(new UserCourseEnrolled(enrolledId, Utils.USERID, courseId, completed.size()));

                                }

                            });

                        });
                    }
                });
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

}

