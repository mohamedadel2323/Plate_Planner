package com.example.plateplanner.network;

import com.example.plateplanner.model.AreaResponse.AreaPojo;
import com.example.plateplanner.model.CategoryPojo;
import com.example.plateplanner.model.IngredientResponse;
import com.example.plateplanner.model.MealPojo;

import java.util.List;


public interface NetworkDelegate {
    public void onSuccessResult(MealPojo mealPojo);
    public void onFailureResult(String errorMessage);
    public void onCategorySuccessResult(List<CategoryPojo> categories);
    public void onCategoryFailureResult(String errorMessage);
    public void onAreaSuccessResult(List<AreaPojo> areas);
    public void onAreaFailureResult(String errorMessage);
    public void onIngredientSuccessResult(List<IngredientResponse.IngredientPojo> ingredients);
    public void onIngredientFailureResult(String errorMessage);


}
