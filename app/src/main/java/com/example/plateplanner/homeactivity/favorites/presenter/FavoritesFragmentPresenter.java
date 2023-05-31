package com.example.plateplanner.homeactivity.favorites.presenter;

import com.example.plateplanner.homeactivity.favorites.view.FavoritesFragmentViewInterface;
import com.example.plateplanner.model.MealPojo;
import com.example.plateplanner.model.PlanMeal;
import com.example.plateplanner.model.Repository;
import com.example.plateplanner.network.FirebaseDelegate;

import java.util.List;

public class FavoritesFragmentPresenter implements FirebaseDelegate {
    FavoritesFragmentViewInterface view;
    Repository repository;

    public FavoritesFragmentPresenter(FavoritesFragmentViewInterface view, Repository repository) {
        this.view = view;
         this.repository = repository;
    }


    public void getAllFavoriteMeals(){
        view.getAllFavoriteMeals(repository.getAllFavoriteMeals());
    }

    public void downloadMeals(String email){
        repository.downloadMeals(email , this);
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFail(String errorMessage) {

    }

    @Override
    public void onDownloadMealsSuccess(List<MealPojo> favMeals, List<PlanMeal> planMeals) {
        repository.insertFavMealList(favMeals);
        getAllFavoriteMeals();
    }
}
