package com.example.coursehubapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LifecycleOwner;

import com.example.coursehubapplication.DashboardScreen.DashboardActivity;
import com.example.coursehubapplication.DashboardScreen.ViewCoursesActivity;
import com.example.coursehubapplication.DashboardScreen.ViewLessonActivity;
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

    public static void inertCourses(MyViewModel viewModel, Resources resources) {
        Bitmap photoWeb = BitmapFactory.decodeResource(resources, R.drawable.web);
        Bitmap photoAndroid = BitmapFactory.decodeResource(resources, R.drawable.android_);
        Bitmap photoUIUX = BitmapFactory.decodeResource(resources, R.drawable.ux_ui);
        Bitmap photoJava = BitmapFactory.decodeResource(resources, R.drawable.java);

        viewModel.insertCourse(new Course("Web Development Basics",
                "Learn the essentials of web development.\n"
                        + "Cover HTML, CSS, and JavaScript in depth.\n"
                        + "Understand front-end and back-end concepts.\n"
                        + "Build your first interactive web page.",
                "Moamen Al Asawi", photoWeb, 99.99, 20, 1));

        viewModel.insertCourse(new Course(
                "Android App Development",
                "Master Android development from scratch.\n"
                        + "Learn about activities, fragments, and UI components.\n"
                        + "Work with APIs, databases, and Firebase.\n"
                        + "Develop a fully functional Android application.",
                "Hashem Saqqa", photoAndroid, 149.99, 40, 2));

        viewModel.insertCourse(new Course(
                "UI/UX Design Principles",
                "Understand user interface and experience design.\n"
                        + "Learn about wireframing, prototyping, and testing.\n"
                        + "Work with industry-standard tools like Figma.\n"
                        + "Improve accessibility and usability in designs.",
                "Ezz Aldeen Abu Jabal", photoUIUX, 79.99, 30, 3));

        viewModel.insertCourse(new Course(
                "Java Programming Essentials",
                "Get started with Java from the basics.\n"
                        + "Learn object-oriented programming (OOP).\n"
                        + "Understand data structures and algorithms.\n"
                        + "Build real-world applications using Java.",
                "Jaafar Al Agha", photoJava, 89.99, 35, 4));

    }

    public static void inertLesson(MyViewModel viewModel) {
        long currentTime = System.currentTimeMillis();

        viewModel.insertLesson(new Lesson("Introduction to HTML",
                "Understand the role of HTML in web development.\n"
                        + "Learn about elements, tags, and attributes.\n"
                        + "Explore semantic HTML and its benefits.\n"
                        + "Build a simple webpage with structured content.",
                "https://youtu.be/SpqsP8yM_As?si=i5lGJFA11FmsCv5F", "https://stackoverflow.com/questions/63990692/add-to-favorite-using-room-database", 1, currentTime, false));

        viewModel.insertLesson(new Lesson("CSS Basics",
                "Learn how to style HTML pages using CSS.\n"
                        + "Explore selectors, properties, and values.\n"
                        + "Understand box model and positioning.\n"
                        + "Apply styles to enhance web page aesthetics.",
                "https://youtu.be/SpqsP8yM_As?si=i5lGJFA11FmsCv5F", null, 1, currentTime, false));

        viewModel.insertLesson(new Lesson("JavaScript Basics",
                "Discover JavaScript fundamentals for interactivity.\n"
                        + "Learn about variables, functions, and events.\n"
                        + "Understand loops, conditions, and DOM manipulation.\n"
                        + "Create interactive web applications.",
                "https://youtu.be/SpqsP8yM_As?si=i5lGJFA11FmsCv5F", null, 1, currentTime, false));

        viewModel.insertLesson(new Lesson("Building a Web Page",
                "Combine HTML, CSS, and JavaScript.\n"
                        + "Create a responsive and dynamic page.\n"
                        + "Understand the importance of UI/UX in web design.\n"
                        + "Optimize performance for better user experience.",
                "https://youtu.be/Web_Project", null, 1, currentTime, false));

        viewModel.insertLesson(new Lesson("Responsive Design",
                "Learn how to make websites mobile-friendly.\n"
                        + "Understand flexbox and grid layouts.\n"
                        + "Implement media queries for adaptability.\n"
                        + "Enhance accessibility and usability.",
                "https://youtu.be/SpqsP8yM_As?si=i5lGJFA11FmsCv5F", null, 1, currentTime, false));

        viewModel.insertLesson(new Lesson("Web Deployment",
                "Deploy a website to the internet.\n"
                        + "Use GitHub Pages, Netlify, and Firebase Hosting.\n"
                        + "Understand domain names and hosting services.\n"
                        + "Make your project live and accessible.",
                "https://youtu.be/SpqsP8yM_As?si=i5lGJFA11FmsCv5F", null, 1, currentTime, false));

        viewModel.insertLesson(new Lesson("Getting Started with Android",
                "Introduction to Android development.\n"
                        + "Understand activities and UI components.\n"
                        + "Set up Android Studio and create your first app.\n"
                        + "Explore XML layouts and basic UI interactions.",
                "https://youtu.be/SpqsP8yM_As?si=i5lGJFA11FmsCv5F", null, 2, currentTime, false));

        viewModel.insertLesson(new Lesson("Kotlin Basics for Android",
                "Learn the essentials of Kotlin programming.\n"
                        + "Understand variables, functions, and control flow.\n"
                        + "Explore OOP concepts and class structures.\n"
                        + "Write your first Kotlin-based Android app.",
                "https://youtu.be/SpqsP8yM_As?si=i5lGJFA11FmsCv5F", null, 2, currentTime, false));

        viewModel.insertLesson(new Lesson("Fragments and Navigation",
                "Understand fragments and their role in UI design.\n"
                        + "Learn how to navigate between different screens.\n"
                        + "Use Jetpack Navigation Component for smooth transitions.\n"
                        + "Manage back stack and state preservation.",
                "https://youtu.be/SpqsP8yM_As?si=i5lGJFA11FmsCv5F", null, 2, currentTime, false));

        viewModel.insertLesson(new Lesson("Networking in Android",
                "Learn how to fetch data from APIs.\n"
                        + "Use Retrofit and OkHttp for HTTP requests.\n"
                        + "Handle JSON parsing and display dynamic content.\n"
                        + "Implement caching for offline support.",
                "https://youtu.be/SpqsP8yM_As?si=i5lGJFA11FmsCv5F", null, 2, currentTime, false));

        viewModel.insertLesson(new Lesson("Firebase Integration",
                "Explore Firebase services for Android apps.\n"
                        + "Implement authentication and real-time database.\n"
                        + "Use Cloud Firestore for scalable data management.\n"
                        + "Enable push notifications and cloud storage.",
                "https://youtu.be/SpqsP8yM_As?si=i5lGJFA11FmsCv5F", null, 2, currentTime, false));

        viewModel.insertLesson(new Lesson("Publishing Your App",
                "Prepare your Android app for release.\n"
                        + "Optimize performance and remove debugging logs.\n"
                        + "Generate a signed APK and upload it to Google Play.\n"
                        + "Follow best practices for successful app launch.",
                "https://youtu.be/SpqsP8yM_As?si=i5lGJFA11FmsCv5F", null, 2, currentTime, false));


        viewModel.insertLesson(new Lesson("Introduction to UI/UX Design",
                "Learn the core concepts of UI and UX design.\n"
                        + "Understand the differences between UI and UX.\n"
                        + "Explore user-centered design principles.\n"
                        + "See how UI/UX impacts user satisfaction.",
                "https://youtu.be/SpqsP8yM_As?si=i5lGJFA11FmsCv5F", null, 3, System.currentTimeMillis(), false));

        viewModel.insertLesson(new Lesson("Wireframing & Prototyping",
                "Discover the importance of wireframes in UI design.\n"
                        + "Use tools like Figma, Adobe XD, and Sketch.\n"
                        + "Learn how to create low-fidelity and high-fidelity prototypes.\n"
                        + "Test designs with real users for feedback.",
                "https://youtu.be/SpqsP8yM_As?si=i5lGJFA11FmsCv5F", null, 3, System.currentTimeMillis(), false));

        viewModel.insertLesson(new Lesson("Introduction to Java",
                "Understand the history and purpose of Java.\n"
                        + "Learn about Java syntax, variables, and data types.\n"
                        + "Explore the concept of compiling and running Java code.\n"
                        + "Write your first Java program using a simple structure.",
                "https://youtu.be/SpqsP8yM_As?si=i5lGJFA11FmsCv5F", null, 4, System.currentTimeMillis(), false));

        viewModel.insertLesson(new Lesson("Object-Oriented Programming in Java",
                "Explore the core concepts of OOP in Java.\n"
                        + "Learn about classes, objects, and constructors.\n"
                        + "Understand inheritance, polymorphism, and encapsulation.\n"
                        + "Implement real-world examples using OOP principles.",
                "https://youtu.be/SpqsP8yM_As?si=i5lGJFA11FmsCv5F", null, 4, System.currentTimeMillis(), false));

    }



    public static void insertBookmark(MyViewModel viewModel){
        viewModel.insertBookmark(new Bookmark(2,1));
        viewModel.insertBookmark(new Bookmark(2,3));
        viewModel.insertBookmark(new Bookmark(4,2));
        viewModel.insertBookmark(new Bookmark(3,1));
        viewModel.insertBookmark(new Bookmark(3,2));

    }


    public static void inertEnrollUserInCourse(MyViewModel viewModel){
        viewModel.insertEnrollUserInCourse(new UserCourseEnrolled(2,1,0,System.currentTimeMillis()));
        viewModel.insertEnrollUserInCourse(new UserCourseEnrolled(2,2,0,System.currentTimeMillis()));
        viewModel.insertEnrollUserInCourse(new UserCourseEnrolled(3,3,0,System.currentTimeMillis()));
        viewModel.insertEnrollUserInCourse(new UserCourseEnrolled(3,2,0,System.currentTimeMillis()));
        viewModel.insertEnrollUserInCourse(new UserCourseEnrolled(4,1,0,System.currentTimeMillis()));

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
// هذا الكود ضفته جديد علشان اذا الادمن حذق category
// الاخير يضيف category اخر افتراضي و يحول الكورسات بداخله
    public static AlertDialog getAlertDialog(MyViewModel viewModel, int position, Context context, List<Category> categoryList) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        DialogBinding binding = DialogBinding.inflate(LayoutInflater.from(context), null, false);
        builder.setView(binding.getRoot());

        binding.titeleTV.setText("Confirmation");
        binding.teMassage.setText("Are you sure you want to delete this category?");
        binding.selectCategory.setText("Please select a category to assign courses:");
        binding.actionBt.setText("OK");
        Category categorys = categoryList.get(position);
        List<String> categoryNames = new ArrayList<>();

        // علشان ما يضيف على اليستا الكتوجري يلي بدي احذفه
        for (Category category : categoryList) {
            if (!category.getCategoryName().equals(categorys.getCategoryName())) {
                categoryNames.add(category.getCategoryName());
            }
        }

        AlertDialog dialog = builder.create();

        //اذا السبينر فاضية
        if (categoryNames.isEmpty()) {
            binding.teMassage.setText("Default category will be added ");
            binding.selectCategory.setVisibility(View.GONE);  // بخفي السبينر
        } else {
            // علشان احدث السبينر
            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, categoryNames);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.categorySpenner.setAdapter(adapter);
            binding.categorySpenner.setSelection(0);  // احدد اول تصنيف
            binding.actionBt.setEnabled(true);  // افعل  الزر
        }

        binding.actionBt.setOnClickListener(view -> {
            if (categoryNames.isEmpty()) {
                new Thread(() -> {
                    //اضيف تصنيف افتراضي لما يحذف اخر category
                    Category learnCategory = new Category("Learn");
                    viewModel.categoryInsert(learnCategory);

                    new Handler(Looper.getMainLooper()).post(() -> {

                        viewModel.getAllCategories().observe((LifecycleOwner) context, categories -> {
                                // بجيب ال id
                            int defaultCategoryId = -1;
                            for (Category category : categories) {
                                if (category.getCategoryName().equals("Learn")) {
                                    defaultCategoryId = category.getCategoryId();
                                    break;
                                }
                            }


                            if (defaultCategoryId != -1) {
                                int finalDefaultCategoryId = defaultCategoryId;
                                new Thread(() -> {
                                    // احول الكورسات لل learn
                                    Log.d("TAG", "getAlertDialog: "+finalDefaultCategoryId);
                                    viewModel.updateCoursesFromCategory(categorys.getCategoryId(), finalDefaultCategoryId);
                                    viewModel.deleteCategory(categorys);
                                    dialog.dismiss();
                                }).start();
                            }
                        });
                    });
                }).start();
            } else {
                String selected = binding.categorySpenner.getSelectedItem().toString();
                int selectedCategoryId = -1;

               // اجيب يلي اخختاره المستخدم
                for (Category category : categoryList) {
                    if (category.getCategoryName().equals(selected)) {
                        selectedCategoryId = category.getCategoryId();
                        break;
                    }
                }

                if (selectedCategoryId != -1) {
                    int finalSelectedCategoryId = selectedCategoryId;
                    new Thread(() -> {// الثريد علشان اول اشي يعدل بعدها يحذف
                        viewModel.updateCoursesFromCategory(categorys.getCategoryId(), finalSelectedCategoryId);
                        viewModel.deleteCategory(categorys);
                        dialog.dismiss();
                    }).start();
                }
            }
        });

        binding.imImageIcon.setOnClickListener(view -> dialog.dismiss());

        dialog.setCancelable(true);
        return dialog;
    }


}




