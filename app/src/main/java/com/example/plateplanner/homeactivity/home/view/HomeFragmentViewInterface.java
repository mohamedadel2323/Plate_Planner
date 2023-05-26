package com.example.plateplanner.homeactivity.home.view;

import com.example.plateplanner.startactivity.model.AreaResponse.AreaPojo;
import com.example.plateplanner.startactivity.model.CategoryPojo;
import com.example.plateplanner.startactivity.model.MealPojo;

import java.util.List;

public interface HomeFragmentViewInterface {
    public void showDailyInspiration(MealPojo mealPojo);
    public void showConnectionError(String errorMessage);
    public void showCategoriesList(List<CategoryPojo> categories);
    public void showAreasList(List<AreaPojo> areas);
}
