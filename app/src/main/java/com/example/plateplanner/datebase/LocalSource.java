package com.example.plateplanner.datebase;

import androidx.lifecycle.LiveData;


import com.example.plateplanner.startactivity.model.MealPojo;

import java.util.List;

public interface LocalSource {

    void insertMeal(MealPojo meal);
    void deleteMeal(MealPojo meal);
    LiveData<List<MealPojo>> getAllMeals();
    LiveData<Boolean> checkExistence(String mealId);


}
