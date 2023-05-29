package com.example.plateplanner.datebase;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.example.plateplanner.startactivity.model.MealPojo;
import com.example.plateplanner.startactivity.model.PlanMeal;

import java.util.List;

public interface LocalSource {

    void insertMeal(MealPojo meal);
    void deleteMeal(MealPojo meal);
    void clearAllMeals();
    LiveData<List<MealPojo>> getAllMeals();
    LiveData<Boolean> checkExistence(String mealId);


    void insertPlanMeal(PlanMeal planMeal);
    void deletePlanMeal(PlanMeal planMeal);
    void clearAllPlanMeals();


    LiveData<List<PlanMeal>> getAllPlanMealsByDay(String day);
}
