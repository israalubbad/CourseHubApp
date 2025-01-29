package com.example.coursehubapplication.RoomDatabase;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(entity = Course.class,parentColumns = "courseId",childColumns = "courseId",onDelete = ForeignKey.CASCADE) })
public class Lesson {

    @PrimaryKey(autoGenerate = true)
    private int lessonId;

    private String lessonTitle;

    private String lessonDescription;

    private String lessonVideo;
    @Nullable
    private String articleLink;

    private int courseId;

    @ColumnInfo(name = "isAdminAdded")
    private boolean isAdminAdded;

    @Ignore
    public Lesson( String lessonTitle, String lessonDescription, String lessonVideo, @Nullable String articleLink, int courseId, boolean isAdminAdded) {
        this.lessonTitle = lessonTitle;
        this.lessonDescription = lessonDescription;
        this.lessonVideo = lessonVideo;
        this.articleLink = articleLink;
        this.courseId = courseId;
        this.isAdminAdded = isAdminAdded;
    }

    public Lesson(String lessonTitle, String lessonDescription, String lessonVideo, String articleLink, int courseId) {
        this.lessonTitle = lessonTitle;
        this.lessonDescription = lessonDescription;
        this.lessonVideo = lessonVideo;
        this.articleLink = articleLink;
        this.courseId = courseId;

    }
    @Ignore
    public Lesson(int lessonId,String lessonTitle, String lessonDescription, String lessonVideo, String articleLink, int courseId) {
        this.lessonId=lessonId;
        this.lessonTitle = lessonTitle;
        this.lessonDescription = lessonDescription;
        this.lessonVideo = lessonVideo;
        this.articleLink = articleLink;
        this.courseId = courseId;

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

    public boolean isAdminAdded() {
        return isAdminAdded;
    }

    public void setAdminAdded(boolean adminAdded) {
        isAdminAdded = adminAdded;
    }
}
