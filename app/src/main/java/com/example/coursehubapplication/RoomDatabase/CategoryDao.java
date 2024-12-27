package com.example.coursehubapplication.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CategoryDao {
    @Insert
    void categoryInsert(Category category);

    @Update
    void categoryUpdate(Category category);

    @Delete
    void categoryDelete(Category category);

    @Query("SELECT * FROM category")
    LiveData<List<Category>> getAllCategories();

    @Query("select * from category WHERE categoryId = :categoryId ")
    LiveData<Category>getCategoryByCategoryId(int categoryId);

    
}
