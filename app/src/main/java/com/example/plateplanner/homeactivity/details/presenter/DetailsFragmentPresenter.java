package com.example.plateplanner.homeactivity.details.presenter;

import androidx.lifecycle.LiveData;

import com.example.plateplanner.homeactivity.details.view.DetailsFragmentViewInterface;
import com.example.plateplanner.startactivity.model.MealPojo;
import com.example.plateplanner.startactivity.model.Repository;

public class DetailsFragmentPresenter {
    DetailsFragmentViewInterface view;
    Repository repository;
    public DetailsFragmentPresenter(DetailsFragmentViewInterface view , Repository repository){
        this.view = view;
        this.repository = repository;
    }

    public void addMealToFavourites(MealPojo mealPojo){
        repository.addToFavorites(mealPojo);
    }
    public void removeMealFromFavourites(MealPojo mealPojo){
        repository.removeFromFavorites(mealPojo);
    }
    public LiveData<Boolean> checkExistence(String mealId){
       return repository.checkExistence(mealId);
    }
}
