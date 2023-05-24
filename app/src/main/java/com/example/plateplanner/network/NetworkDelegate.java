package com.example.plateplanner.network;

import com.example.plateplanner.startactivity.model.AreaResponse;
import com.example.plateplanner.startactivity.model.AreaResponse.AreaPojo;
import com.example.plateplanner.startactivity.model.CategoryPojo;
import com.example.plateplanner.startactivity.model.MealPojo;

import java.util.List;


public interface NetworkDelegate {
    public void onSuccessResult(MealPojo mealPojo);
    public void onFailureResult(String errorMessage);
    public void onCategorySuccessResult(List<CategoryPojo> categories);
    public void onCategoryFailureResult(String errorMessage);
    public void onAreaSuccessResult(List<AreaPojo> areas);
    public void onAreaFailureResult(String errorMessage);
}
