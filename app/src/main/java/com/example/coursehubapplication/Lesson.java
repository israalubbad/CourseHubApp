package com.example.coursehubapplication;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(entity = Course.class,parentColumns = "courseId",childColumns = "courseId") })
public class Lesson {
    @PrimaryKey(autoGenerate = true)
    private int lessonId;

    private String lessonTitle;

    private String lessonDescription;

    private String lessonVideo;

    private String articleLink;

    private int courseId;

    private boolean isCompleted;

    public Lesson(String lessonTitle, String lessonDescription, String lessonVideo, String articleLink, int courseId, boolean isCompleted) {
        this.lessonTitle = lessonTitle;
        this.lessonDescription = lessonDescription;
        this.lessonVideo = lessonVideo;
        this.articleLink = articleLink;
        this.courseId = courseId;
        this.isCompleted = isCompleted;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public void setLessonTitle(String lessonTitle) {
        this.lessonTitle = lessonTitle;
    }

    public String getLessonDescription() {
        return lessonDescription;
    }

    public void setLessonDescription(String lessonDescription) {
        this.lessonDescription = lessonDescription;
    }

    public String getLessonVideo() {
        return lessonVideo;
    }

    public void setLessonVideo(String lessonVideo) {
        this.lessonVideo = lessonVideo;
    }

    public String getArticleLink() {
        return articleLink;
    }

    public void setArticleLink(String articleLink) {
        this.articleLink = articleLink;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
