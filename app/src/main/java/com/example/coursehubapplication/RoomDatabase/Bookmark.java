package com.example.coursehubapplication.RoomDatabase;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(entity = User.class,parentColumns = "userId",childColumns = "userId",onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Course.class,parentColumns = "courseId",childColumns = "courseId",onDelete = ForeignKey.CASCADE)
})
public class Bookmark {
    @PrimaryKey(autoGenerate = true)
    private int bookmarkId;

    private int userId;
    private int courseId;

    public Bookmark( int userId, int courseId) {
        this.userId = userId;
        this.courseId = courseId;
    }

    public int getBookmarkId() {
        return bookmarkId;
    }

    public void setBookmarkId(int bookmarkId) {
        this.bookmarkId = bookmarkId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
