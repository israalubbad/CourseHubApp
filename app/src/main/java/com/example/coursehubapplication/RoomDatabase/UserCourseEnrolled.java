package com.example.coursehubapplication.RoomDatabase;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(entity = User.class,parentColumns = "userId",childColumns = "userId",onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Course.class,parentColumns = "courseId",childColumns = "courseId",onDelete = ForeignKey.CASCADE)
                      })


public class UserCourseEnrolled {
    @PrimaryKey(autoGenerate = true)
    private  int  enrolledCourseId;
    private int userId;

    private int courseId;

    private int progressIndicator;


    public UserCourseEnrolled(int userId, int courseId, int progressIndicator) {
        this.userId = userId;
        this.courseId = courseId;
        this.progressIndicator = progressIndicator;

    }

    public int getEnrolledCourseId() {
        return enrolledCourseId;
    }

    public void setEnrolledCourseId(int enrolledCourseId) {
        this.enrolledCourseId = enrolledCourseId;
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

    public int getProgressIndicator() {
        return progressIndicator;
    }

    public void setProgressIndicator(int progressIndicator) {
        this.progressIndicator = progressIndicator;
    }
}
