package com.example.coursehubapplication.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LessonDao {
    @Insert
    void lessonInsert(Lesson lesson);

    @Update
    void lessonUpdate(Lesson lesson);

    @Delete
    void lessonDelete(Lesson lesson);

    @Query("SELECT * FROM lesson")
    LiveData<List<Lesson>> getAllLesson();

    @Query("SELECT * FROM lesson WHERE lessonId = :lessonId ")
    LiveData<Lesson>getLessonId(int lessonId);

    @Query("SELECT * FROM lesson WHERE courseId = :courseId")
    LiveData<List<Lesson>>getLessonByCourseId(int courseId);
}
