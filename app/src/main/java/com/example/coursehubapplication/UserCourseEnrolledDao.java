package com.example.coursehubapplication;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserCourseEnrolledDao {
    @Insert
    void insertEnrollUserInCourse(UserCourseEnrolled enrollment);

    @Update
    void updateEnrollUserInCourse(UserCourseEnrolled enrollment);

    @Delete
    void deleteEnrollUserInCourse(UserCourseEnrolled enrollment);

    @Query("DELETE FROM UserCourseEnrolled WHERE userId = :userId AND courseId = :courseId")
    void deleteUserFromCourse(int userId, int courseId);

    @Query("SELECT * FROM UserCourseEnrolled")
    LiveData<List<UserCourseEnrolled>> getAllEnrollments();

    @Query("SELECT * FROM UserCourseEnrolled WHERE enrolledCourseId = :enrolledCourseId")
    LiveData<UserCourseEnrolled> getEnrolledId(int enrolledCourseId);

    @Query("SELECT * FROM UserCourseEnrolled WHERE userId = :userId")
    LiveData<UserCourseEnrolled> getCoursesByUserId(int userId);

    @Query("SELECT * FROM UserCourseEnrolled WHERE courseId = :courseId")
    LiveData<UserCourseEnrolled> getUsersByCourseId(int courseId);




}
