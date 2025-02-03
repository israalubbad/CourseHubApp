package com.example.coursehubapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;

import com.example.coursehubapplication.Adapter.MyLessonAdapter;
import com.example.coursehubapplication.DashboardScreen.DashboardActivity;
import com.example.coursehubapplication.DashboardScreen.ViewCoursesActivity;
import com.example.coursehubapplication.DashboardScreen.ViewLessonActivity;
import com.example.coursehubapplication.HomeScreen.MyLessonActivity;
import com.example.coursehubapplication.RoomDatabase.Bookmark;
import com.example.coursehubapplication.RoomDatabase.Category;
import com.example.coursehubapplication.RoomDatabase.Course;
import com.example.coursehubapplication.RoomDatabase.Lesson;
import com.example.coursehubapplication.RoomDatabase.LessonUser;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;
import com.example.coursehubapplication.RoomDatabase.User;
import com.example.coursehubapplication.RoomDatabase.UserCourseEnrolled;
import com.example.coursehubapplication.databinding.DialogBinding;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static int USERID = -1;
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
        viewModel.insertLesson(new Lesson("HTML Ba77sics", "Learn the basics of HTML syntax and structure.", "https://youtu.be/SpqsP8yM_As?si=i5lGJFA11FmsCv5F", null, 1,false));
        viewModel.insertLesson(new Lesson("HTML Ba77sics", "Learn the basics of HTML syntax and structure.", "https://youtu.be/SpqsP8yM_As?si=i5lGJFA11FmsCv5F", null, 1,false));
        viewModel.insertLesson(new Lesson("CSS St77yling", "Learn how to apply styles to HTML elements using CSS.", "https://youtu.be/SpqsP8yM_As?si=i5lGJFA11FmsCv5F", "https://stackoverflow.com/questions/63990692/add-to-favorite-using-room-database", 2,false));

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


    public static AlertDialog.Builder getBuilder(MyViewModel viewModel, Object data, String textMassage, String key, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirmation");
        builder.setMessage(textMassage);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(key.equals("joinCourse")) {
                    viewModel.deleteEnrollUserInCourse((UserCourseEnrolled) data);
                    Toast.makeText(context, "Delete Course", Toast.LENGTH_SHORT).show();
                }else if(key.equals("bookmark")){
                    viewModel.deleteBookmark((Bookmark) data);
                    Toast.makeText(context, "Bookmark deleted", Toast.LENGTH_SHORT).show();
                }else if(key.equals("lesson")){
                    Toast.makeText((ViewLessonActivity) context, "category deleted", Toast.LENGTH_SHORT).show();
                    viewModel.deleteLesson((Lesson) data);
                }else if(key.equals("course")){
                    Toast.makeText((ViewCoursesActivity) context, "course deleted", Toast.LENGTH_SHORT).show();
                    viewModel.deleteCourse((Course) data );
                }else if(key.equals("category")){
                    viewModel.deleteCategory((Category) data);
                    Toast.makeText((DashboardActivity) context, "Category deleted", Toast.LENGTH_SHORT).show();
                }
                else if(key.equals("userLesson")){
                    viewModel.deleteLessonUser((LessonUser) data);
                    Toast.makeText(context, "unChecked lesson", Toast.LENGTH_SHORT).show();

                }
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


    // customary dialog
    public static AlertDialog getAlertDialog(MyViewModel viewModel, int position, Context context, List<Category> categoryList) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        DialogBinding binding = DialogBinding.inflate(LayoutInflater.from(context), null, false);
        builder.setView(binding.getRoot());
        binding.titeleTV.setText("Confirmation");
        binding.teMassage.setText("Are you sure you want to delete this category?");
        binding.selectCategory.setText("Please select category to assign courses:");
        binding.actionBt.setText("OK");
        // اعمل ليست للسبينر
        List<String> categoryNames = new ArrayList<>();
        Category category = categoryList.get(position);
        for (Category categorys : categoryList) {
            // علشان ما يضيف على اليستا الكتوجري يلي بدي احذفه
            if (!categorys.getCategoryName().equals(category.getCategoryName())) {
                categoryNames.add(categorys.getCategoryName());
            }
        }

        AlertDialog dialog = builder.create();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, categoryNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.categorySpenner.setAdapter(adapter);

        binding.actionBt.setOnClickListener(view -> {
            // اجيب يلي اختاره مستخدم
            String selected = binding.categorySpenner.getSelectedItem().toString();
            // اجبره انو يختار من السبينر
            if (selected.isEmpty()) {
                Toast.makeText(context, "Please Select Category", Toast.LENGTH_SHORT).show();
            } else {
                int selectedCategoryId = -1;
                for (Category categorys : categoryList) {
                    // اجيب id  للتصنيف
                    if (categorys.getCategoryName().equals(selected)) {
                        selectedCategoryId = categorys.getCategoryId();
                        break;
                    }
                }

                if (selectedCategoryId != -1) {
                    // علشان اعدل الكورسات من الكتوجري القديم ل كتوجري جديد اختاره الادمن
                    viewModel.updateCoursesFromCategory(category.getCategoryId(), selectedCategoryId);
                    viewModel.deleteCategory(category);
                    Toast.makeText(context, "Category Deleted", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });


        binding.imImageIcon.setOnClickListener(view -> {
            dialog.dismiss();
        });


        dialog.setCancelable(true);
        return dialog;
    }





}




