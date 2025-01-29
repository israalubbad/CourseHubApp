package com.example.coursehubapplication.RoomDatabase;

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

    @Query("SELECT * FROM UserCourseEnrolled WHERE userId = :userId")
    LiveData<UserCourseEnrolled> getCoursesByUserId(int userId);

    @Query("SELECT * FROM UserCourseEnrolled WHERE userId = :userId")
    LiveData<List<UserCourseEnrolled>> getCoursesByUserIdList(int userId);

    @Query("SELECT * FROM UserCourseEnrolled WHERE courseId = :courseId")
    LiveData<List<UserCourseEnrolled>> getUsersByCourseId(int courseId);

    @Query("SELECT COUNT(*) FROM UserCourseEnrolled WHERE userId = :userId AND courseId = :courseId")
    LiveData<Boolean> isUserEnrolledInCourse(int userId, int courseId);

    @Query("SELECT * FROM UserCourseEnrolled WHERE userId = :userId AND courseId = :courseId")
    LiveData<UserCourseEnrolled> getUserEnrolledInCourse(int userId, int courseId);




}
