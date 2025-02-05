package com.example.coursehubapplication.RoomDatabase;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

public class MyRepository {
    private UserDao userDao;
    private CategoryDao categoryDao;
    private CourseDao courseDao;
    private LessonDao lessonDao;
    private BookmarkDao bookmarkDao;
    private UserCourseEnrolledDao userCourseEnrolledDao;
    private LessonUserDao lessonUserDao;

    public MyRepository(Application application) {
        CourseDatabase db = CourseDatabase.getDatabase(application);
        userDao = db.userDao();
        categoryDao = db.categoryDao();
        courseDao = db.courseDao();
        lessonDao = db.lessonDao();
        bookmarkDao = db.bookmarkDao();
        userCourseEnrolledDao = db.userCourseEnrolledDao();
        lessonUserDao = db.LessonUserDao();
    }

    //// User
    void userInsert(User user) {
        CourseDatabase.databaseWriteExecutor.execute(() -> {
            userDao.userInsert(user);
        });
    }

    void userUpdate(User user) {
        CourseDatabase.databaseWriteExecutor.execute(() -> {
            userDao.userUpdate(user);
        });
    }

    void userDelete(User user) {
        CourseDatabase.databaseWriteExecutor.execute(() -> {
            userDao.userDelete(user);
        });
    }

    LiveData<List<User>> geAllUser() {
        return userDao.getAllUser();
    }

    LiveData<User> getUserByEmailAndPassword(String email, String password) {
        return userDao.getUserByEmailAndPassword(email, password);
    }


    LiveData<User> getUserId(int userId) {
        return userDao.getUserId(userId);
    }

    LiveData<User> getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    /////////////Category
    void categoryInsert(Category category) {
        CourseDatabase.databaseWriteExecutor.execute(() -> {
            categoryDao.categoryInsert(category);
        });
    }

    void categoryUpdate(Category category) {
        CourseDatabase.databaseWriteExecutor.execute(() -> {
            categoryDao.categoryUpdate(category);
        });
    }

    void deleteCategory(Category category) {
        CourseDatabase.databaseWriteExecutor.execute(() -> {
            categoryDao.categoryDelete(category);


        });
    }

    public LiveData<List<Category>> getAllCategories() {
        return categoryDao.getAllCategories();
    }

    public LiveData<Category> getCategoryById(int categoryId) {
        return categoryDao.getCategoryByCategoryId(categoryId);
    }


    ///// Course
    public void insertCourse(Course course) {
        CourseDatabase.databaseWriteExecutor.execute(() -> {
            courseDao.courseInsert(course);
        });
    }

    public void updateCourse(Course course) {
        CourseDatabase.databaseWriteExecutor.execute(() -> {
            courseDao.courseUpdate(course);
        });
    }

    public void deleteCourse(Course course) {
        CourseDatabase.databaseWriteExecutor.execute(() -> {
            courseDao.courseDelete(course);

        });

    }

    public void updateCoursesFromCategory(int oldCategoryId, int newCategoryId) {
        CourseDatabase.databaseWriteExecutor.execute(() -> {
            courseDao.updateCoursesFromCategory(oldCategoryId, newCategoryId);
        });
    }

    public LiveData<List<Course>> getAllCourses() {
        return courseDao.getAllCourse();
    }

    public LiveData<Course> getCourseById(int courseId) {
        return courseDao.getCourseId(courseId);
    }

    public LiveData<List<Course>> getCoursesByCategoryId(int categoryId) {
        return courseDao.getCourseByCategoryId(categoryId);
    }


    ///Lesson

    public void insertLesson(Lesson lesson) {
        CourseDatabase.databaseWriteExecutor.execute(() -> {
            lessonDao.lessonInsert(lesson);
        });
    }

    public void updateLesson(Lesson lesson) {
        CourseDatabase.databaseWriteExecutor.execute(() -> {
            lessonDao.lessonUpdate(lesson);
        });
    }

    public void deleteLesson(Lesson lesson) {
        CourseDatabase.databaseWriteExecutor.execute(() -> {
            lessonDao.lessonDelete(lesson);
        });
    }

    public LiveData<List<Lesson>> getAllLessons() {
        return lessonDao.getAllLesson();
    }

    public LiveData<Lesson> getLessonById(int lessonId) {
        return lessonDao.getLessonId(lessonId);
    }

    public LiveData<List<Lesson>> getLessonsByCourseId(int courseId) {
        return lessonDao.getLessonByCourseId(courseId);
    }
    LiveData<List<Lesson>> getLessonsAfterEnrolled(int courseId, long timeEnrolled){
        return lessonDao.getLessonsAfterEnrolled(courseId,timeEnrolled);
    }




    // Enrollment
    public void insertEnrollUserInCourse(UserCourseEnrolled enrollment) {
        CourseDatabase.databaseWriteExecutor.execute(() -> {
            userCourseEnrolledDao.insertEnrollUserInCourse(enrollment);
        });
    }

    public void updateEnrollUserInCourse(UserCourseEnrolled enrollment) {
        CourseDatabase.databaseWriteExecutor.execute(() -> {
            userCourseEnrolledDao.updateEnrollUserInCourse(enrollment);
        });
    }

    public void deleteEnrollUserInCourse(UserCourseEnrolled enrollment) {
        CourseDatabase.databaseWriteExecutor.execute(() -> {
            userCourseEnrolledDao.deleteEnrollUserInCourse(enrollment);
        });
    }

    public void deleteUserFromCourse(int userId, int courseId) {
        CourseDatabase.databaseWriteExecutor.execute(() -> {
            userCourseEnrolledDao.deleteUserFromCourse(userId, courseId);
        });
    }


    public LiveData<List<UserCourseEnrolled>> getAllEnrollments() {
        return userCourseEnrolledDao.getAllEnrollments();
    }

    public LiveData<UserCourseEnrolled> getCoursesByUserId(int userId) {
        return userCourseEnrolledDao.getCoursesByUserId(userId);
    }

    public LiveData<List<UserCourseEnrolled>> getCoursesByUserIdList(int userId) {
        return userCourseEnrolledDao.getCoursesByUserIdList(userId);
    }


    LiveData<List<UserCourseEnrolled>> getUsersByCourseId(int courseId) {
        return userCourseEnrolledDao.getUsersByCourseId(courseId);
    }

    public LiveData<Boolean> isUserEnrolledInCourse(int userId, int courseId) {
        return userCourseEnrolledDao.isUserEnrolledInCourse(userId, courseId);
    }

    LiveData<UserCourseEnrolled> getUserEnrolledInCourse(int userId, int courseId) {
        return userCourseEnrolledDao.getUserEnrolledInCourse(userId, courseId);
    }
    // Bookmark

    public void insertBookmark(Bookmark bookmark) {
        CourseDatabase.databaseWriteExecutor.execute(() -> {
            bookmarkDao.insertBookmark(bookmark);
        });
    }

    public void deleteBookmark(Bookmark bookmark) {
        CourseDatabase.databaseWriteExecutor.execute(() -> {
            bookmarkDao.deleteBookmark(bookmark);
        });
    }

    public LiveData<List<Bookmark>> getAllBookmarks() {
        return bookmarkDao.getAllBookmark();
    }

    LiveData<List<Bookmark>> getBookmarkByUserId(int userId) {
        return bookmarkDao.getBookmarkByUserId(userId);
    }

    public LiveData<Bookmark> getBookmarkByUserIdAndCourse(int userId, int courseId) {
        return bookmarkDao.getBookmarkByUserIdAndCourse(userId, courseId);
    }

    public LiveData<Boolean> getIsBookmark(int courseId, int userId) {
        return bookmarkDao.getIsBookmark(courseId, userId);
    }

    public void deleteBookmarkByUserAndCourse(int userId, int courseId) {
        CourseDatabase.databaseWriteExecutor.execute(() -> {
            bookmarkDao.deleteBookmarkByUserAndCourse(userId, courseId);
        });
    }

    ////////

    void insertLessonUser(LessonUser lessonUser) {
        CourseDatabase.databaseWriteExecutor.execute(() -> {
            lessonUserDao.insertLessonUser(lessonUser);
        });
    }


    void updateLessonUser(LessonUser lessonUser) {
        CourseDatabase.databaseWriteExecutor.execute(() -> {

            lessonUserDao.updateLessonUser(lessonUser);
        });
    }

    void deleteLessonUser(LessonUser lessonUser) {
        CourseDatabase.databaseWriteExecutor.execute(() -> {

            lessonUserDao.deleteLessonUser(lessonUser);
        });
    }


    LiveData<LessonUser> getLessonUser(int enrolledCourseId, int lessonId) {
        return lessonUserDao.getLessonUser(enrolledCourseId, lessonId);
    }

    public LiveData<Boolean> getIsCompleted(int enrolledCourseId, int lessonId) {
        return lessonUserDao.getIsCompleted(enrolledCourseId, lessonId);
    }

    public LiveData<List<LessonUser>> getCompletedLesson(int enrolledCourseId) {
        return lessonUserDao.getCompletedLesson(enrolledCourseId);
    }





}
