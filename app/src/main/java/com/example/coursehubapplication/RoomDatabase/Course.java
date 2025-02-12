package com.example.coursehubapplication.RoomDatabase;

import android.graphics.Bitmap;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;


@Entity(foreignKeys = @ForeignKey(entity = Category.class, parentColumns = {"categoryId"}, childColumns = {"courseCategory"},onDelete = ForeignKey.SET_NULL)
)
    @TypeConverters(Converters.class)
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int courseId;

    private String courseTitle;

    private String courseDescription;

    private String courseInstructorName;

    private Bitmap courseImage;

    private double coursePrice;

    private int courseHours;

    @Nullable
    private Integer courseCategory;

    public Course( String courseTitle, String courseDescription, String courseInstructorName, Bitmap courseImage, double coursePrice, int courseHours, int courseCategory) {
        this.courseTitle = courseTitle;
        this.courseDescription = courseDescription;
        this.courseInstructorName = courseInstructorName;
        this.courseImage = courseImage;
        this.coursePrice = coursePrice;
        this.courseHours = courseHours;
        this.courseCategory = courseCategory;
    }
    @Ignore
    public Course(int courseId, String courseTitle, String courseDescription, String courseInstructorName, Bitmap courseImage, double coursePrice, int courseHours, Integer courseCategory) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.courseDescription = courseDescription;
        this.courseInstructorName = courseInstructorName;
        this.courseImage = courseImage;
        this.coursePrice = coursePrice;
        this.courseHours = courseHours;
        this.courseCategory = courseCategory;
    }
    @Ignore
    public Course(int courseId, String courseTitle, String courseDescription, String courseInstructorName, Bitmap courseImage, double coursePrice, int courseHours) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.courseDescription = courseDescription;
        this.courseInstructorName = courseInstructorName;
        this.courseImage = courseImage;
        this.coursePrice = coursePrice;
        this.courseHours = courseHours;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCourseInstructorName() {
        return courseInstructorName;
    }

    public void setCourseInstructorName(String courseInstructorName) {
        this.courseInstructorName = courseInstructorName;
    }

    public Bitmap getCourseImage() {
        return courseImage;
    }

    public void setCourseImage(Bitmap courseImage) {
        this.courseImage = courseImage;
    }

    public double getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(double coursePrice) {
        this.coursePrice = coursePrice;
    }

    public int getCourseHours() {
        return courseHours;
    }

    public void setCourseHours(int courseHours) {
        this.courseHours = courseHours;
    }

    public Integer getCourseCategory() {
        return courseCategory;
    }

    public void setCourseCategory(Integer courseCategory) {
        this.courseCategory = courseCategory;
    }

}
