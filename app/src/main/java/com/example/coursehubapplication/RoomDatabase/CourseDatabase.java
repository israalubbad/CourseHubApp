package com.example.coursehubapplication.RoomDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {User.class, Category.class, Course.class, UserCourseEnrolled.class, Bookmark.class, Lesson.class,LessonUser.class}, version = 3, exportSchema = false)
@TypeConverters({Converters.class})
 public abstract class CourseDatabase extends RoomDatabase {

        public abstract UserDao userDao();
        public abstract CategoryDao categoryDao();
        public abstract CourseDao courseDao();
        public abstract LessonDao lessonDao();
        public abstract BookmarkDao bookmarkDao();
        public abstract UserCourseEnrolledDao userCourseEnrolledDao();
        public abstract LessonUserDao LessonUserDao();

        private static volatile CourseDatabase INSTANCE;

        private static final int NUMBER_OF_THREADS = 4;

        public static final ExecutorService databaseWriteExecutor =
                Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        static CourseDatabase getDatabase(final Context context) {

            if (INSTANCE == null)
                synchronized (CourseDatabase.class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                        CourseDatabase.class, "CourseHub_database")
                                .fallbackToDestructiveMigration()//علشان لو عدلنا على version
                                .build();
                    }
                }

            return INSTANCE;
        }
    }

