package com.example.plateplanner.homeactivity.favorites.presenter;

import com.example.plateplanner.homeactivity.favorites.view.FavoritesFragmentViewInterface;
import com.example.plateplanner.model.Repository;

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
