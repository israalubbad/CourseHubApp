package com.example.coursehubapplication.RoomDatabase;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(entity = Lesson.class, parentColumns = "lessonId", childColumns = "lessonId", onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = UserCourseEnrolled.class, parentColumns = "enrolledCourseId", childColumns = "enrolledCourseId", onDelete = ForeignKey.CASCADE)
})
public class LessonUser {

    @PrimaryKey(autoGenerate = true)
    private int lessonUserId;
    private int enrolledCourseId;
    private int lessonId;


    public LessonUser( int enrolledCourseId, int lessonId) {
        this.enrolledCourseId = enrolledCourseId;
        this.lessonId = lessonId;

    }
    @Ignore
    public LessonUser(int lessonUserId,int enrolledCourseId, int lessonId) {
        this.lessonUserId=lessonUserId;
        this.enrolledCourseId = enrolledCourseId;
        this.lessonId = lessonId;
    }

    public int getLessonUserId() {
        return lessonUserId;
    }

    public void setLessonUserId(int lessonUserId) {
        this.lessonUserId = lessonUserId;
    }

    public int getEnrolledCourseId() {
        return enrolledCourseId;
    }

    public void setEnrolledCourseId(int enrolledCourseId) {
        this.enrolledCourseId = enrolledCourseId;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

}