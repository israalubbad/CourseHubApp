package com.example.coursehubapplication.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookmarkDao {
    @Insert
    void insertBookmark(Bookmark bookmark);


    @Delete
    void deleteBookmark(Bookmark bookmark);

    @Query("SELECT * FROM Bookmark WHERE userId = :userId")
    LiveData<Bookmark> getBookmarkByUserId(int userId);

    @Query("SELECT * FROM Bookmark WHERE courseId = :courseId")
    LiveData<Bookmark> getBookmarkByCourseId(int courseId);

    @Query("SELECT * FROM Bookmark WHERE userId = :userId AND courseId = :courseId ")
    LiveData<Bookmark> getBookmarkByUserIdAndCourse(int userId,int courseId);

    @Query("SELECT * FROM Bookmark WHERE bookmarkId = :bookmarkId")
    LiveData<Bookmark> getBookmarkId(int bookmarkId);

    @Query("SELECT * FROM Bookmark")
    LiveData<List<Bookmark>> getAllBookmark();
//
//    @Query("SELECT * FROM Course WHERE courseId IN (SELECT courseId FROM Bookmark WHERE userId = :userId)")
//    LiveData<List<Bookmark>>getBookmarkCoursesByUser(int userId);
}
