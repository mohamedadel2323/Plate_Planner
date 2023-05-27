package com.example.plateplanner.homeactivity.search.view;

import com.example.plateplanner.startactivity.model.CategoryPojo;
import com.example.plateplanner.startactivity.model.MealPojo;

import java.util.List;

public interface SearchFragmentViewInterface {
    public void showSearchResult(List<MealPojo> mealPojoList);

    void showCategoriesResult(List<CategoryPojo> categoryPojos);

    public void showErrorMessage(String errorMessage);
}
