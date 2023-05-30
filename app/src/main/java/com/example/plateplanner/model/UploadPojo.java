package com.example.plateplanner.model;

import java.util.List;

public class UploadPojo {
    private List<PlanMeal> planMeals;
    private List<MealPojo> favMeals;

    public UploadPojo() {
    }

    public UploadPojo(List<PlanMeal> planMeals, List<MealPojo> favMeals) {
        this.planMeals = planMeals;
        this.favMeals = favMeals;
    }

    public List<PlanMeal> getPlanMeals() {
        return planMeals;
    }

    public void setPlanMeals(List<PlanMeal> planMeals) {
        this.planMeals = planMeals;
    }

    public List<MealPojo> getFavMeals() {
        return favMeals;
    }

    public void setFavMeals(List<MealPojo> favMeals) {
        this.favMeals = favMeals;
    }
}
