package com.example.plateplanner.startactivity.model;

import java.util.List;

public class RandomResponse {
    private List<MealPojo> meals;

    public RandomResponse(List<MealPojo> meals) {
        this.meals = meals;
    }

    public List<MealPojo> getMeals() {
        return meals;
    }

    public void setMeals(List<MealPojo> meals) {
        this.meals = meals;
    }
}
