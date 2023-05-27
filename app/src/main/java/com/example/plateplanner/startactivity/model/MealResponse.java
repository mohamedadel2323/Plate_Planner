package com.example.plateplanner.startactivity.model;

import java.util.List;

public class MealResponse {
    private List<MealPojo> meals;

    public MealResponse(List<MealPojo> meals) {
        this.meals = meals;
    }

    public List<MealPojo> getMeals() {
        return meals;
    }

    public void setMeals(List<MealPojo> meals) {
        this.meals = meals;
    }
}
