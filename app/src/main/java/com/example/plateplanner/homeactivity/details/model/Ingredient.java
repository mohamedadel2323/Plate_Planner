package com.example.plateplanner.homeactivity.details.model;

public class Ingredient {
    private String ingredientName;
    private String ingredientMeasure;
    private String ingredientUri;

    public Ingredient(String ingredientName, String ingredientMeasure) {
        this.ingredientName = ingredientName;
        this.ingredientMeasure = ingredientMeasure;
    }

    public Ingredient(String ingredientName, String ingredientMeasure, String ingredientUri) {
        this.ingredientName = ingredientName;
        this.ingredientMeasure = ingredientMeasure;
        this.ingredientUri = ingredientUri;
    }

    public String getIngredientMeasure() {
        return ingredientMeasure;
    }

    public void setIngredientMeasure(String ingredientMeasure) {
        this.ingredientMeasure = ingredientMeasure;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getIngredientUri() {
        return ingredientUri;
    }

    public void setIngredientUri(String ingredientUri) {
        this.ingredientUri = ingredientUri;
    }
}
