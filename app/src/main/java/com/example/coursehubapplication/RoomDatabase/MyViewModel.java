package com.example.coursehubapplication.RoomDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MyViewModel extends AndroidViewModel {
    MyRepository repository;

    public MyViewModel(@NonNull Application application) {
        super(application);
        repository = new MyRepository(application);
    }

    //// User
    public void userInsert(User user) {
        repository.userInsert(user);
    }

    public void userUpdate(User user) {
        repository.userUpdate(user);
    }

    public void userDelete(User user) {
        repository.userDelete(user);
    }

    public LiveData<List<User>> geAllUser() {
        return repository.geAllUser();
    }

    public LiveData<User> getUserByEmailAndPassword(String email, String password) {
        return repository.getUserByEmailAndPassword(email, password);
    }


    public LiveData<User> getUserId(int userId) {
        return repository.getUserId(userId);
    }

    public LiveData<User> getUserByEmail(String email) {
        return repository.getUserByEmail(email);
    }

    // Category
    public boolean categoryInsert(Category category) {
        repository.categoryInsert(category);
        return false;
    }

    public boolean categoryUpdate(Category category) {
        repository.categoryUpdate(category);
        return false;
    }

    public void deleteCategory(Category category) {
        repository.deleteCategory(category);
    }


    public LiveData<List<Category>> getAllCategories() {
        return repository.getAllCategories();
    }

    public LiveData<Category> getCategoryById(int categoryId) {
        return repository.getCategoryById(categoryId);
    }

    // Course
    public boolean insertCourse(Course course) {
        repository.insertCourse(course);
        return false;
    }

    public boolean updateCourse(Course course) {
        repository.updateCourse(course);
        return false;
    }

    public void deleteCourse(Course course) {
        repository.deleteCourse(course);
    }

    public void updateCoursesFromCategory(int oldCategoryId, int newCategoryId) {

        repository.updateCoursesFromCategory(oldCategoryId,newCategoryId);
    }

    public LiveData<List<Course>> getAllCourses() {
        return repository.getAllCourses();
    }

    public LiveData<Course> getCourseById(int courseId) {
        return repository.getCourseById(courseId);
    }

    public LiveData<List<Course>> getCoursesByCategoryId(int categoryId) {
        return repository.getCoursesByCategoryId(categoryId);
    }



    // Lesson
    public boolean insertLesson(Lesson lesson) {
        repository.insertLesson(lesson);
        return false;
    }

    public boolean updateLesson(Lesson lesson) {
        repository.updateLesson(lesson);
        return false;
    }

    public void deleteLesson(Lesson lesson) {
        repository.deleteLesson(lesson);
    }

    public LiveData<List<Lesson>> getAllLessons() {
        return repository.getAllLessons();
    }

    public LiveData<Lesson> getLessonById(int lessonId) {
        return repository.getLessonById(lessonId);
    }

    public LiveData<List<Lesson>> getLessonsByCourseId(int courseId) {
        return repository.getLessonsByCourseId(courseId);
    }



    // Enrollment
    public void insertEnrollUserInCourse(UserCourseEnrolled enrollment) {
        repository.insertEnrollUserInCourse(enrollment);
    }

    public void updateEnrollUserInCourse(UserCourseEnrolled enrollment) {
        repository.updateEnrollUserInCourse(enrollment);
    }

    public void deleteEnrollUserInCourse(UserCourseEnrolled enrollment) {
        repository.deleteEnrollUserInCourse(enrollment);
    }

    public void deleteUserFromCourse(int userId, int courseId) {
        repository.deleteUserFromCourse(userId, courseId);
    }

    public   LiveData<List<UserCourseEnrolled>> getUsersByCourseId(int courseId) {
        return repository.getUsersByCourseId(courseId);
    }
    public LiveData<Boolean> isAlreadyEnrolled(int userId, int courseId) {
        return repository.isUserEnrolledInCourse(userId, courseId);
    }

    public LiveData<List<UserCourseEnrolled>> getCoursesByUserIdList(int userId) {
        return repository.getCoursesByUserIdList(userId);
    }

    public LiveData<UserCourseEnrolled> getUserEnrolledInCourse(int userId, int courseId) {
        return repository.getUserEnrolledInCourse(userId, courseId);
    }


    // Bookmark
    public void insertBookmark(Bookmark bookmark) {
        repository.insertBookmark(bookmark);
    }

    public void deleteBookmark(Bookmark bookmark) {
        repository.deleteBookmark(bookmark);
    }

    public LiveData<List<Bookmark>> getAllBookmarks() {
        return repository.getAllBookmarks();
    }

    public LiveData<List<Bookmark>> getBookmarkByUserId(int userId) {
        return repository.getBookmarkByUserId(userId);
    }


    public LiveData<Bookmark> getBookmarkByUserIdAndCourse(int userId, int courseId) {
        return repository.getBookmarkByUserIdAndCourse(userId, courseId);
    }

    public LiveData<Boolean> getIsBookmark(int courseId, int userId) {
        return repository.getIsBookmark(courseId, userId);
    }

    public void deleteBookmarkByUserAndCourse(int userId, int courseId) {

        repository.deleteBookmarkByUserAndCourse(userId, courseId);

    }

    ////////////////////
    public void insertLessonUser(LessonUser lessonUser) {
        repository.insertLessonUser(lessonUser);

    }

    public void deleteLessonUser(LessonUser lessonUser) {
        repository.deleteLessonUser(lessonUser);


    }

    public void updateLessonUser(LessonUser lessonUser) {
        repository.updateLessonUser(lessonUser);

    }

    public LiveData<LessonUser> getLessonUser(int enrolledCourseId, int lessonId) {
        return repository.getLessonUser(enrolledCourseId, lessonId);
    }

    public LiveData<Boolean> getIsCompleted(int enrolledCourseId, int lessonId) {
        return repository.getIsCompleted(enrolledCourseId, lessonId);
    }


    public LiveData<List<LessonUser>> getCompletedLesson(int enrolledCourseId){
        return repository.getCompletedLesson(enrolledCourseId);
    }


}


