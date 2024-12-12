package com.example.coursehubapplication;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NotificationDao {
    @Insert
    void insertNotification(Notification notification);

    @Query("SELECT * FROM Notification")
    LiveData<List<Notification>>  getAllNotifications();

    @Query("SELECT * FROM Notification WHERE userId = :userId")
    LiveData<Notification> getNotificationsByUserId(int userId);

    @Query("SELECT * FROM Notification WHERE courseId = :courseId")
    LiveData<Notification> getNotificationsByCourseId(int courseId);

    @Query("SELECT * FROM Notification WHERE lessonId = :lessonId")
    LiveData<Notification> getNotificationsByLessonId(int lessonId);

    @Query("SELECT * FROM Notification WHERE userId = :userId AND isRead = 0")
    LiveData<Notification> getUnreadNotificationsForUser(int userId);


}
