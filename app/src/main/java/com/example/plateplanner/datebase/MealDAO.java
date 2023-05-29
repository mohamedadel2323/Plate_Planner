package com.example.plateplanner.datebase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.plateplanner.startactivity.model.MealPojo;

import java.util.List;
@Dao
public interface MealDAO {

    @Query("SELECT * FROM meals")
    LiveData<List<MealPojo>> getAllMeals();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMeal(MealPojo meal);

    @Delete
    void deleteMeal(MealPojo meal);

    @Query("SELECT 1 FROM meals WHERE idMeal = :mealId")
    LiveData<Boolean> checkExistence(String mealId);
}
