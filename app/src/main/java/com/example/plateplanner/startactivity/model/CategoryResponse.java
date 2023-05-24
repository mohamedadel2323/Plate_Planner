package com.example.plateplanner.startactivity.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryResponse {
    @SerializedName("categories")
    private List<CategoryPojo> mealCategories;

    public List<CategoryPojo> getMealCategories() {
        return mealCategories;
    }

    public void setMealCategories(List<CategoryPojo> mealCategories) {
        this.mealCategories = mealCategories;
    }
}
