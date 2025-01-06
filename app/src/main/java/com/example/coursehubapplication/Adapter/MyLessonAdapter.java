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

import com.example.coursehubapplication.DashboardScreen.AddLessonActivity;
import com.example.coursehubapplication.DashboardScreen.ViewLessonActivity;
import com.example.coursehubapplication.HomeScreen.HomeActivity;
import com.example.coursehubapplication.R;
import com.example.coursehubapplication.RoomDatabase.Lesson;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;
import com.example.coursehubapplication.databinding.LessonItemBinding;
import com.example.coursehubapplication.databinding.MyLessonItemBinding;

import java.util.List;

public class MyLessonAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Lesson> lessonList;
    Context context;
    MyLessonItemBinding binding;

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
        MyViewModel viewModel = new ViewModelProvider((HomeActivity) context).get(MyViewModel.class);
        viewHolder.binding.lessonTitle.setText(lessonList.get(position).getLessonTitle());
        viewHolder.binding.checkBox.setChecked(lessonList.get(position).isCompleted());
//        viewModel.getCompletedLesson(lessonList.get(position).getCourseId(), 0).observe(this, completedLessons -> {
//            db.lessonDao().getTotalLessonCount(courseId, userId).observe(this, totalLessons -> {
//                if () {
//                    int progress = (completedLessons * 100) / totalLessons;
//                    progressBar.setProgress(progress);
//                    progressText.setText(progress + "%");
//                } else {
//                    progressBar.setProgress(0);
//                    progressText.setText("0%");
//                }
//            });
//        });


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

    public interface ClickListener {
        void onClick(int courseId);
    }

}

