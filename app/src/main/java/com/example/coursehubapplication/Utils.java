package com.example.coursehubapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.example.coursehubapplication.RoomDatabase.Bookmark;
import com.example.coursehubapplication.RoomDatabase.Category;
import com.example.coursehubapplication.RoomDatabase.Course;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;
import com.example.coursehubapplication.RoomDatabase.User;
import com.example.coursehubapplication.RoomDatabase.UserCourseEnrolled;

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
        viewModel.userInsert(new User("Admin", "admin@example.com", "admin123", userPhoto, true));
        viewModel.userInsert(new User("Ahmad ALi","ahmad@example.com","123456" ,userPhoto,false ));
        viewModel.userInsert(new User("Israa Lubbad","israa@example.com","123456" ,userPhoto,false ));
        viewModel.userInsert(new User("Sarah Lubbad","sarah@example.com","123456" ,userPhoto,false ));

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

