package com.example.plateplanner.homeactivity.home.presenter;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.plateplanner.homeactivity.home.view.HomeFragmentViewInterface;
import com.example.plateplanner.model.PlanMeal;
import com.example.plateplanner.network.FirebaseDelegate;
import com.example.plateplanner.network.NetworkDelegate;
import com.example.plateplanner.model.AreaResponse.AreaPojo;
import com.example.plateplanner.model.CategoryPojo;
import com.example.plateplanner.model.IngredientResponse;
import com.example.plateplanner.model.MealPojo;
import com.example.plateplanner.model.Repository;

import java.util.List;

public class HomeFragmentPresenter implements NetworkDelegate{
    private final String TAG = "HomeFragmentPresenter";
    HomeFragmentViewInterface view;
    Repository repository;

    public HomeFragmentPresenter(HomeFragmentViewInterface view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void getDailyInspirationMeal() {
        repository.getDailyInspiration(this);
    }

    public void getCategories() {
        repository.getCategories(this);
    }
    public void getCountries() {
        repository.getAreas(this);
    }
    public void addToFavorites(MealPojo mealPojo){
        repository.addToFavorites(mealPojo);
    }
    public void removeFromFavorites(MealPojo mealPojo){
        repository.removeFromFavorites(mealPojo);
    }
    public LiveData<Boolean> checkExistence(String mealId){
        return repository.checkExistence(mealId);
    }

    @Override
    public void onSuccessResult(MealPojo mealPojo) {
        view.showDailyInspiration(mealPojo);
    }

    @Override
    public void onFailureResult(String errorMessage) {
        view.showConnectionError(errorMessage);
    }

    @Override
    public void onCategorySuccessResult(List<CategoryPojo> categories) {
        view.showCategoriesList(categories);
    }


    @Override
    public void onCategoryFailureResult(String errorMessage) {
        view.showConnectionError(errorMessage);
    }

    @Override
    public void onAreaSuccessResult(List<AreaPojo> areas) {
        view.showAreasList(areas);
    }

    @Override
    public void onAreaFailureResult(String errorMessage) {
        view.showConnectionError(errorMessage);
    }

    @Override
    public void onIngredientSuccessResult(List<IngredientResponse.IngredientPojo> ingredients) {

    }

    @Override
    public void onIngredientFailureResult(String errorMessage) {

    }

}
