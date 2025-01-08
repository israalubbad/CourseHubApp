package com.example.coursehubapplication.Adapter;

import static android.content.Context.MODE_PRIVATE;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursehubapplication.HomeScreen.CourseDetailsActivity;
import com.example.coursehubapplication.HomeScreen.HomeActivity;
import com.example.coursehubapplication.R;
import com.example.coursehubapplication.RoomDatabase.Bookmark;
import com.example.coursehubapplication.RoomDatabase.Course;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;
import com.example.coursehubapplication.databinding.CourseviewItemBinding;

import java.util.List;

public class BookMarkAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        List<Bookmark> bookmarkList;
    Context context;
    CourseviewItemBinding binding;
    int userId;
    int getCourseCategory;
    HomeCourseAdapter.ClickListener clickListener;
    boolean isBookMark = false;

    public BookMarkAdapter(List<Bookmark> bookmarkList, Context context, CourseAdapter.ClickListener clickListener, int userId) {
        this.bookmarkList = bookmarkList;
        this.context = context;
        this.userId = userId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = CourseviewItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new BookMarkAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewModel viewModel = new ViewModelProvider((HomeActivity) context).get(MyViewModel.class);
        Bookmark bookmark1 = bookmarkList.get(position);
        BookMarkAdapter.ViewHolder viewHolder = (BookMarkAdapter.ViewHolder) holder;
        viewModel.getCourseById(bookmark1.getCourseId()).observe((HomeActivity)context, new Observer<Course>() {
            @Override
            public void onChanged(Course course) {
                getCourseCategory=course.getCourseCategory();
                viewHolder.binding.courseTitle.setText(course.getCourseTitle());
                viewHolder.binding.courseDescription.setText(course.getCourseDescription());
                viewHolder.binding.instructorName.setText(course.getCourseInstructorName());
                viewHolder.binding.coursePhotoTV.setImageBitmap(course.getCourseImage());
                viewModel.getIsBookmark(course.getCourseId(), userId).observe((LifecycleOwner) context, isBookmarked -> {
                    if (isBookmarked != null) {
                        if (isBookmarked) {
                            viewHolder.binding.bookMarkIv.setImageResource(R.drawable.bookmark);
                        } else {
                            viewHolder.binding.bookMarkIv.setImageResource(R.drawable.unbookmark);
                        }
                        viewHolder.binding.bookMarkIv.setOnClickListener(view -> {
                            if (!isBookmarked) {
                                Bookmark bookmark = new Bookmark(userId, course.getCourseId());
                                viewModel.insertBookmark(bookmark);
                                Toast.makeText(context, "Bookmark added", Toast.LENGTH_SHORT).show();
                            } else {
                                AlertDialog.Builder builder = getBuilder(viewModel, course);
                                AlertDialog dialog = builder.create();
                                dialog.setCancelable(true);
                                dialog.show();
                            }
                        });
                    }
                });
            }
        });



        viewHolder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(context, CourseDetailsActivity.class);
                intent.putExtra("courseId", bookmark1.getCourseId());
                intent.putExtra("userId", userId);
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
        return bookmarkList.size();
    }

    public interface ClickListener {
        void courseClick(Course course);

        void onClick(int courseId);

    }


    public AlertDialog.Builder getBuilder(MyViewModel viewModel, Course course) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to remove this bookmark?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                viewModel.deleteBookmarkByUserAndCourse(userId, course.getCourseId());
                Toast.makeText(context, "Bookmark deleted", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "Canceled", Toast.LENGTH_SHORT).show();
            }
        });
        return builder;
    }
}

