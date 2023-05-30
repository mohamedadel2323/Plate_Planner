package com.example.plateplanner.homeactivity.profile.view;

import androidx.lifecycle.LiveData;

import com.example.plateplanner.model.MealPojo;
import com.example.plateplanner.network.FirebaseDelegate;

import java.util.List;

public interface ProfileFragmentViewInterface {
//    public void getCachedFavoriteMeals(LiveData<List<MealPojo>> meals);
    public void uploadFavoriteMeals(String email , List<MealPojo> favMeals, FirebaseDelegate firebaseDelegate);
    public void logout();
    public void showErrorMessage(String errorMessage);
}
