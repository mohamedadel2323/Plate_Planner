package com.example.plateplanner.homeactivity.favorites.presenter;

import androidx.lifecycle.LiveData;

import com.example.plateplanner.homeactivity.favorites.view.FavoritesFragmentViewInterface;
import com.example.plateplanner.startactivity.model.MealPojo;
import com.example.plateplanner.startactivity.model.Repository;

public class FavoritesFragmentPresenter {
    FavoritesFragmentViewInterface view;
    Repository repository;

    public FavoritesFragmentPresenter(FavoritesFragmentViewInterface view, Repository repository) {
        this.view = view;
         this.repository = repository;
    }


    public void getAllFavoriteMeals(){
        view.getAllFavoriteMeals(repository.getAllFavoriteMeals());
    }

}
