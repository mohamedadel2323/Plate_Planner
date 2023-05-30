package com.example.plateplanner.network;

import com.example.plateplanner.model.AuthModel;
import com.example.plateplanner.model.MealPojo;
import com.example.plateplanner.model.PlanMeal;

import java.util.List;

public interface FirebaseSource {
    public void signIn(AuthModel authModel, FirebaseDelegate firebaseDelegate);
    public void signup(AuthModel authModel, FirebaseDelegate FirebaseDelegate);

    void uploadMeals(String email, List<PlanMeal> planMeals, List<MealPojo> favMeals, FirebaseDelegate firebaseDelegate);
    void downloadMeals(String email, FirebaseDelegate firebaseDelegate);
}
