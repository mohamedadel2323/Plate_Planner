package com.example.plateplanner.network;

import com.example.plateplanner.startactivity.model.IngredientResponse;
import com.example.plateplanner.startactivity.model.MealPojo;

import java.util.List;

public interface SearchNetworkDelegate {
    public void onSearchSuccess(List<MealPojo> meals);
    public void onSearchFailure(String errorMessage);
    public void onGetMealByIdSuccess(List<MealPojo> meals);
}
