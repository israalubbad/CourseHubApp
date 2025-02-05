package com.example.coursehubapplication.Adapter;

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

import com.example.coursehubapplication.DashboardScreen.AddLessonActivity;
import com.example.coursehubapplication.R;
import com.example.coursehubapplication.RoomDatabase.Lesson;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;
import com.example.coursehubapplication.DashboardScreen.ViewLessonActivity;
import com.example.coursehubapplication.Utils;
import com.example.coursehubapplication.databinding.LessonItemBinding;

import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Lesson> lessonList;
    Context context;
    LessonItemBinding binding;

    public LessonAdapter(List<Lesson> lessonList, Context context) {
        this.lessonList = lessonList;
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = LessonItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new LessonAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        LessonAdapter.ViewHolder viewHolder = (LessonAdapter.ViewHolder) holder;
        MyViewModel viewModel = new ViewModelProvider((ViewLessonActivity) context).get(MyViewModel.class);
        viewHolder.binding.lessonTitle.setText(lessonList.get(position).getLessonTitle());
        viewHolder.binding.lessonDescription.setText(lessonList.get(position).getLessonDescription());
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
                            Intent intent = new Intent(context, AddLessonActivity.class);
                            intent.putExtra("lesson", 103);
                            intent.putExtra("lessonId", lessonList.get(position).getLessonId());
                            context.startActivity(intent);
                        }
                        if (itemId == R.id.deleteItem) {
                            String textMassage="Are you sure you want to remove this lesson ?";
                            String key="lesson";
                            AlertDialog.Builder builder = Utils.getBuilder(viewModel,lessonList.get(position),-1,textMassage,key,(ViewLessonActivity)context,-1,-1);
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

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LessonItemBinding binding;
        ImageView more;

        public ViewHolder(LessonItemBinding binding) {
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


