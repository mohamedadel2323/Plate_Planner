package com.example.plateplanner.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AreaResponse {
    @SerializedName("meals")
    private List<AreaPojo> meals;

    public List<AreaPojo> getMeals() {
        return meals;
    }

    public void setMeals(List<AreaPojo> meals) {
        this.meals = meals;
    }

    public static class AreaPojo {
        private String strArea;

        public String getStrArea() {
            return strArea;
        }

        public void setStrArea(String strArea) {
            this.strArea = strArea;
        }
    }
}
