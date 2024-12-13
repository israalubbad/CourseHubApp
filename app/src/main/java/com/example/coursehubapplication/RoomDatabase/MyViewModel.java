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

    public LiveData<User> getUserByPassword(String password) {
        return repository.getUserByPassword(password);
    }

    public LiveData<User> getUserId(int userId) {
        return repository.getUserId(userId);
    }

    public LiveData<User> getUserByEmail(String email) {
        return repository.getUserByEmail(email);
    }

    // Category
    public void categoryInsert(Category category) {
        repository.categoryInsert(category);
    }

    public void categoryUpdate(Category category) {
        repository.categoryUpdate(category);
    }

    public void categoryDelete(Category category) {
        repository.categoryDelete(category);
    }

    public LiveData<List<Category>> getAllCategories() {
        return repository.getAllCategories();
    }

    public LiveData<Category> getCategoryById(int categoryId) {
        return repository.getCategoryById(categoryId);
    }

    // Course
    public void insertCourse(Course course) {
        repository.insertCourse(course);
    }

    public void updateCourse(Course course) {
        repository.updateCourse(course);
    }

    public void deleteCourse(Course course) {
        repository.deleteCourse(course);
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
    public void insertLesson(Lesson lesson) {
        repository.insertLesson(lesson);
    }

    public void updateLesson(Lesson lesson) {
        repository.updateLesson(lesson);
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

    public LiveData<List<UserCourseEnrolled>> getAllEnrollments() {
        return repository.getAllEnrollments();
    }

    public LiveData<UserCourseEnrolled> getCoursesByUserId(int userId) {
        return repository.getCoursesByUserId(userId);
    }

    public LiveData<UserCourseEnrolled> getEnrolledId(int enrolledCourseId) {
        return repository.getEnrolledId(enrolledCourseId);
    }

    public LiveData<UserCourseEnrolled> getUsersByCourseId(int courseId) {
        return repository.getUsersByCourseId(courseId);
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

    public LiveData<Bookmark> getBookmarkByUserId(int userId) {
        return repository.getBookmarkByUserId(userId);
    }

    public LiveData<Bookmark> getBookmarkByCourseId(int courseId) {
        return repository.getBookmarkByCourseId(courseId);
    }

    public LiveData<Bookmark> getBookmarkByUserIdAndCourse(int userId, int courseId) {
        return repository.getBookmarkByUserIdAndCourse(userId, courseId);
    }

    public LiveData<Bookmark> getBookmarkId(int bookmarkId) {
        return repository.getBookmarkId(bookmarkId);
    }
}


