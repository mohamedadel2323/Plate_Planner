package com.example.plateplanner.datebase;

import androidx.lifecycle.LiveData;


import com.example.plateplanner.model.MealPojo;
import com.example.plateplanner.model.PlanMeal;

import java.util.List;

public interface LocalSource {

    void insertMeal(MealPojo meal);


    void insertFavMealList(List<MealPojo> favMeals);
    void insertPlanMealList(List<PlanMeal> planMeals);

    void deleteMeal(MealPojo meal);
    void clearAllFavoriteMeals();
    LiveData<List<MealPojo>> getAllMeals();
    LiveData<Boolean> checkExistence(String mealId);


    void insertPlanMeal(PlanMeal planMeal);
    void deletePlanMeal(PlanMeal planMeal);
    LiveData<List<PlanMeal>> getAllPlanMeals();
    void clearAllPlanMeals();


    LiveData<List<PlanMeal>> getAllPlanMealsByDay(String day);

}
