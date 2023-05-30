package com.example.plateplanner.model;

import java.util.List;

public class MealResponse {
    private List<MealPojo> meals;

    public MealResponse() {
    }

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
