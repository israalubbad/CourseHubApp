package com.example.coursehubapplication.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBookmark(Bookmark bookmark);


    @Delete
    void deleteBookmark(Bookmark bookmark);

    @Query("SELECT * FROM Bookmark WHERE userId = :userId")
    LiveData<List<Bookmark>> getBookmarkByUserId(int userId);

    @Query("SELECT * FROM Bookmark WHERE courseId = :courseId")
    LiveData<List<Bookmark>> getBookmarkByCourseId(int courseId);

    @Query("SELECT * FROM Bookmark WHERE userId = :userId AND courseId = :courseId ")
    LiveData<Bookmark> getBookmarkByUserIdAndCourse(int userId,int courseId);

    @Query("SELECT * FROM Bookmark")
    LiveData<List<Bookmark>> getAllBookmark();

    @Query("SELECT COUNT(*) > 0 FROM Bookmark WHERE userId = :userId AND courseId = :courseId")
    public LiveData<Boolean> getIsBookmark(int courseId, int userId);

}
