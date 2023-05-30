package com.example.plateplanner.homeactivity.details.view;

import com.example.plateplanner.model.MealPojo;

public interface DetailsFragmentViewInterface {
    public void addMealToFavorites(MealPojo mealPojo);
    public void removeMealFromFavorites(MealPojo mealPojo);

}
