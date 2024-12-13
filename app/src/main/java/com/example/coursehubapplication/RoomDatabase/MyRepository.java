package com.example.coursehubapplication.RoomDatabase;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MyRepository {
    private UserDao userDao;
    private CategoryDao categoryDao;
    private CourseDao courseDao;
    private LessonDao lessonDao;
    private BookmarkDao bookmarkDao;
    private UserCourseEnrolledDao userCourseEnrolledDao;

    public MyRepository(Application application) {
        CourseDatabase db = CourseDatabase.getDatabase(application);
        userDao = db.userDao();
        categoryDao = db.categoryDao();
        courseDao=db.courseDao();
        lessonDao=db.lessonDao();
        bookmarkDao=db.bookmarkDao();
        userCourseEnrolledDao= db.userCourseEnrolledDao();
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
    LiveData<User>getUserByEmailAndPassword(String email , String password){
        return userDao.getUserByEmailAndPassword(email,password);
    }

    public LiveData<User> getUserByPassword(String password) {
        return userDao.getUserByPassword(password);
    }

    LiveData<User>getUserId(int userId){
        return userDao.getUserId(userId);
    }

    LiveData<User>getUserByEmail(String email){
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
    void categoryDelete(Category category) {
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
            userCourseEnrolledDao.deleteUserFromCourse(userId,courseId);
        });
    }

    public LiveData<List<UserCourseEnrolled>> getAllEnrollments() {
        return userCourseEnrolledDao.getAllEnrollments();
    }

    public LiveData<UserCourseEnrolled> getCoursesByUserId(int userId) {
        return userCourseEnrolledDao.getCoursesByUserId(userId);
    }

    LiveData<UserCourseEnrolled> getEnrolledId(int enrolledCourseId){
        return userCourseEnrolledDao.getEnrolledId(enrolledCourseId);
    }


    LiveData<UserCourseEnrolled> getUsersByCourseId(int courseId){
        return userCourseEnrolledDao.getUsersByCourseId(courseId);
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

    LiveData<Bookmark> getBookmarkByUserId(int userId){
        return bookmarkDao.getBookmarkByUserId(userId);
    }


    LiveData<Bookmark> getBookmarkByCourseId(int courseId){
        return bookmarkDao.getBookmarkByCourseId(courseId);
    }


    LiveData<Bookmark> getBookmarkByUserIdAndCourse(int userId,int courseId){
        return bookmarkDao.getBookmarkByUserIdAndCourse(userId,courseId);
    }


    LiveData<Bookmark> getBookmarkId(int bookmarkId){
        return bookmarkDao.getBookmarkByCourseId(bookmarkId);
    }

}
