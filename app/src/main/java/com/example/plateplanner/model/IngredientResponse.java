package com.example.plateplanner.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientResponse {
    @SerializedName("meals")
    private List<IngredientPojo> meals;

    public List<IngredientPojo> getMeals() {
        return meals;
    }

    public void setMeals(List<IngredientPojo> meals) {
        this.meals = meals;
    }

    public static class IngredientPojo {
        private String idIngredient;
        private String strIngredient;

        private String strDescription;

        private String strType;

        public String getIdIngredient() {
            return idIngredient;
        }

        public void setIdIngredient(String idIngredient) {
            this.idIngredient = idIngredient;
        }

        public String getStrIngredient() {
            return strIngredient;
        }

        public void setStrIngredient(String strIngredient) {
            this.strIngredient = strIngredient;
        }

        public String getStrDescription() {
            return strDescription;
        }

        public void setStrDescription(String strDescription) {
            this.strDescription = strDescription;
        }

        public String getStrType() {
            return strType;
        }

        public void setStrType(String strType) {
            this.strType = strType;
        }
    }
}
