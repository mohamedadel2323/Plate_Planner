package com.example.plateplanner.network;

import com.example.plateplanner.model.MealPojo;
import com.example.plateplanner.model.PlanMeal;

import java.util.List;

public interface FirebaseDelegate {
    public void onSuccess();
    public void onFail(String errorMessage);
    public void onDownloadMealsSuccess(List<MealPojo> favMeals , List<PlanMeal> planMeals);
}
