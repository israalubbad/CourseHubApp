package com.example.coursehubapplication.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CourseDao {
    @Insert
    void courseInsert(Course course);

    @Update
    void courseUpdate(Course course);

    @Delete
    void courseDelete(Course course);

    @Query("UPDATE course SET courseCategory = :newCategoryId WHERE courseCategory = :oldCategoryId")
    void updateCoursesFromCategory(int oldCategoryId, int newCategoryId);

    @Query("SELECT * FROM course")
    LiveData<List<Course>> getAllCourse();

    @Query("SELECT * FROM course WHERE courseId = :courseId ")
    LiveData<Course>getCourseId(int courseId);

    @Query("SELECT * FROM course WHERE courseCategory = :categoryId")
    LiveData<List<Course>>getCourseByCategoryId(int categoryId);





}
