package com.example.plateplanner.homeactivity.favorites.view;

import androidx.lifecycle.LiveData;

import com.example.plateplanner.startactivity.model.MealPojo;

import java.util.List;

public interface FavoritesFragmentViewInterface {
    public void getAllFavoriteMeals(LiveData<List<MealPojo>> meals);
}
