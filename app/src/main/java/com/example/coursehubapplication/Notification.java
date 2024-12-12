package com.example.coursehubapplication;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(entity = User.class,parentColumns = "userId",childColumns = "userId",onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Course.class,parentColumns = "courseId",childColumns = "courseId",onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Lesson.class,parentColumns = "lessonId",childColumns = "lessonId",onDelete = ForeignKey.CASCADE)
})
public class Notification {
    @PrimaryKey(autoGenerate = true)
    private int notificationId;

    private int userId;

    private int courseId;

    private int lessonId;

    private String lessonTitle;

    private boolean isRead;


    public Notification(int userId, int courseId, int lessonId, String lessonTitle, boolean isRead) {
        this.userId = userId;
        this.courseId = courseId;
        this.lessonId = lessonId;
        this.lessonTitle = lessonTitle;
        this.isRead = isRead;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
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

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public void setLessonTitle(String lessonTitle) {
        this.lessonTitle = lessonTitle;
    }
}
