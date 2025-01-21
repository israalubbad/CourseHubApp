package com.example.coursehubapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;

import com.example.coursehubapplication.RoomDatabase.Bookmark;
import com.example.coursehubapplication.RoomDatabase.Category;
import com.example.coursehubapplication.RoomDatabase.Course;
import com.example.coursehubapplication.RoomDatabase.Lesson;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;
import com.example.coursehubapplication.RoomDatabase.User;
import com.example.coursehubapplication.RoomDatabase.UserCourseEnrolled;

import java.util.List;

public class Utils {
    public static boolean showPassword(boolean isPasswordViseble, EditText et){
        if(isPasswordViseble) {

           et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            isPasswordViseble=false;
        }else{
         et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            isPasswordViseble=true;
        }

        et.setSelection(et.getText().length());
        return isPasswordViseble;
    }

    public static void inertUser(MyViewModel viewModel, Resources resources){
        Bitmap userPhoto= BitmapFactory.decodeResource(resources, R.drawable.photo);
        viewModel.userInsert(new User("Admin", "admin@gmail.com", "admin123", userPhoto, true));
        viewModel.userInsert(new User("Ahmad ALi","ahmad@gmail.com","123456" ,userPhoto,false ));
        viewModel.userInsert(new User("Israa Lubbad","israa@gmail.com","123456" ,userPhoto,false ));
        viewModel.userInsert(new User("Sarah Lubbad","sarah@gmail.com","123456" ,userPhoto,false ));

    }

    public static void inertCategory(MyViewModel viewModel){
        viewModel.categoryInsert(new Category("Web"));
        viewModel.categoryInsert(new Category("Android"));
        viewModel.categoryInsert(new Category("Design"));
        viewModel.categoryInsert(new Category("Java"));
    }

    public static void inertCourses(MyViewModel viewModel, Resources resources){
        Bitmap photoCourse= BitmapFactory.decodeResource(resources, R.drawable.web);
        Bitmap photoCourse2= BitmapFactory.decodeResource(resources, R.drawable.android_);
        Bitmap photoCourse3= BitmapFactory.decodeResource(resources, R.drawable.ux_ui);
        Bitmap photoCourse4= BitmapFactory.decodeResource(resources, R.drawable.java);
        viewModel.insertCourse(new Course("Web Development Basics", "Learn the basics of HTML, CSS, and JavaScript.", "Moamen Al Asawi", photoCourse, 99.99, 20, 1 ));
        viewModel.insertCourse(new Course("Android App Development", "Master the fundamentals of Android development.", "Hashem Saqqa", photoCourse2, 149.99, 40, 2));
        viewModel.insertCourse(new Course("UI/UX Design", "Understand the principles of user experience and interface design.", "Ezz Aldeen Abu Jabal", photoCourse3, 79.99, 30, 3 ));
        viewModel.insertCourse(new Course("Java Programming", "Learn Java from scratch and build robust applications.", "Jaafar Al Agha", photoCourse4, 89.99, 35, 4));

    }
    public static void inertLesson(MyViewModel viewModel){
        viewModel.insertLesson(new Lesson("HTML Basics", "Learn the basics of HTML syntax and structure.", "https://youtu.be/SpqsP8yM_As?si=i5lGJFA11FmsCv5F", null, 1,false));
        viewModel.insertLesson(new Lesson("CSS Styling", "Learn how to apply styles to HTML elements using CSS.", "https://youtu.be/SpqsP8yM_As?si=i5lGJFA11FmsCv5F", "https://stackoverflow.com/questions/63990692/add-to-favorite-using-room-database", 2,true));
        viewModel.insertLesson(new Lesson("JavaScript Fundamentals", "Understand basic JavaScript concepts.", "https://youtu.be/SpqsP8yM_As?si=i5lGJFA11FmsCv5F", null, 3,true));

    }

    public static void insertBookmark(MyViewModel viewModel){
        viewModel.insertBookmark(new Bookmark(2,1));
        viewModel.insertBookmark(new Bookmark(2,3));
        viewModel.insertBookmark(new Bookmark(4,2));
        viewModel.insertBookmark(new Bookmark(3,1));
        viewModel.insertBookmark(new Bookmark(3,2));

    }


    public static void inertEnrollUserInCourse(MyViewModel viewModel){
        viewModel.insertEnrollUserInCourse(new UserCourseEnrolled(2,1,0));
        viewModel.insertEnrollUserInCourse(new UserCourseEnrolled(2,2,0));
        viewModel.insertEnrollUserInCourse(new UserCourseEnrolled(3,3,0));
        viewModel.insertEnrollUserInCourse(new UserCourseEnrolled(3,2,0));
        viewModel.insertEnrollUserInCourse(new UserCourseEnrolled(4,1,0));

    }


    public static void updateCourseProgress( MyViewModel viewModel, int userId, UserCourseEnrolled courseEnrolled, int courseId , LifecycleOwner LifecycleOwner,String key) {
        viewModel.getLessonsByCourseId(courseId).observe(LifecycleOwner, lessonList -> {
        int totalLessons = lessonList.size();
        viewModel.getCompletedLesson(courseEnrolled.getEnrolledCourseId()).observe(LifecycleOwner, completed -> {
            if (totalLessons > 0) {
                int  progress = (int) ((completed.size() / (float) totalLessons) * 100);
                courseEnrolled.setProgressIndicator(progress);
            }

        });
        });

    }




//    public void showDialog(Context context, String title, String message, ViewModel viewModel) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle(title)
//                .setMessage(message)
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                })
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(context, "Cancel Clicked", Toast.LENGTH_SHORT).show();
//                        dialog.dismiss();
//                    }
//                })
//                .setCancelable(false);
//
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }

}

