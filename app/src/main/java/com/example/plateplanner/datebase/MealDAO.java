package com.example.plateplanner.datebase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.plateplanner.startactivity.model.MealPojo;
import com.example.plateplanner.startactivity.model.PlanMeal;

import java.util.List;
@Dao
public interface MealDAO {

    @Query("SELECT * FROM meals")
    LiveData<List<MealPojo>> getAllMeals();

    @Query("DELETE FROM meals")
    void clearAllMeals();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMeal(MealPojo meal);

    @Delete
    void deleteMeal(MealPojo meal);

    @Query("SELECT 1 FROM meals WHERE idMeal = :mealId")
    LiveData<Boolean> checkExistence(String mealId);



    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertPlanMeal(PlanMeal planMeal);

    @Delete
    void deletePlanMeal(PlanMeal planMeal);

    @Query("DELETE FROM planMeal")
    void clearAllPlanMeals();

    @Query("SELECT * FROM planMeal WHERE day = :day")
    LiveData<List<PlanMeal>> getAllPlanMealsByDay(String day);


}
