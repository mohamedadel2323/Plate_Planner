package com.example.plateplanner.homeactivity.search.view;

import com.example.plateplanner.startactivity.model.AreaResponse;
import com.example.plateplanner.startactivity.model.CategoryPojo;
import com.example.plateplanner.startactivity.model.IngredientResponse;
import com.example.plateplanner.startactivity.model.MealPojo;

import java.util.List;

public interface SearchFragmentViewInterface {
    public void showSearchResult(List<MealPojo> mealPojoList);

    void showCategoriesResult(List<CategoryPojo> categoryPojos);

    void showCountriesResult(List<AreaResponse.AreaPojo> countries);

    void showIngredientsResult(List<IngredientResponse.IngredientPojo> ingredients);

    public void showErrorMessage(String errorMessage);
    public void goToDetails(List<MealPojo> mealPojoList);
}
