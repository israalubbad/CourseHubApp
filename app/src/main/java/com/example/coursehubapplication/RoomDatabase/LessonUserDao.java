package com.example.coursehubapplication.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface  LessonUserDao {

    @Insert
    void insertLessonUser(LessonUser lessonUser);

    @Update
    void updateLessonUser(LessonUser lessonUser);

    @Delete
   public void deleteLessonUser(LessonUser lessonUser);

    @Query("SELECT COUNT(*) FROM LessonUser WHERE enrolledCourseId = :enrolledCourseId AND isCompleted = 1")
    LiveData<Integer> getCompletedLessons(int enrolledCourseId);

    @Query("SELECT * FROM LessonUser WHERE enrolledCourseId = :enrolledCourseId AND lessonId = :lessonId ")
    LiveData<LessonUser> getLessonUser(int enrolledCourseId, int lessonId);

    @Query("SELECT COUNT(*) > 0 FROM LessonUser WHERE enrolledCourseId = :enrolledCourseId AND lessonId = :lessonId")
    public LiveData<Boolean> getIsCompleted(int enrolledCourseId, int lessonId);

    @Query("SELECT * FROM LessonUser WHERE enrolledCourseId = :enrolledCourseId")
    public LiveData<List<LessonUser>> getCompletedLesson(int enrolledCourseId);

    @Query("DELETE FROM LessonUser WHERE enrolledCourseId = :enrolledCourseId AND lessonId = :lessonId")
    void deleteLessonUser(int enrolledCourseId, int lessonId);

}
