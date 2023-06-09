package com.example.plateplanner.homeactivity.profile.presenter;

import androidx.lifecycle.LiveData;

import com.example.plateplanner.homeactivity.profile.view.ProfileFragmentViewInterface;
import com.example.plateplanner.model.MealPojo;
import com.example.plateplanner.model.PlanMeal;
import com.example.plateplanner.model.Repository;
import com.example.plateplanner.network.FirebaseDelegate;

import java.util.List;

public class ProfileFragmentPresenter implements FirebaseDelegate {

    ProfileFragmentViewInterface view;
    Repository repository;
    int logoutIndicator = -1;

    public ProfileFragmentPresenter(ProfileFragmentViewInterface view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }

    public LiveData<List<MealPojo>> getCashedFavMeals() {
        return repository.getCachedMeals();
    }

    public LiveData<List<PlanMeal>> getCashedPlanMeals() {
        return repository.getAllPlanMeals();
    }

    public void uploadMeals(String email, List<PlanMeal> planMeals, List<MealPojo> favMeals , int logout) {
        logoutIndicator = logout;
        repository.uploadMeals(email, planMeals, favMeals, this);
    }

    public void clearLocalDatabase(){
        repository.clearAllFavoriteMeals();
        repository.clearAllPlanMeals();
    }

    @Override
    public void onSuccess() {
        if (logoutIndicator == 1){
            clearLocalDatabase();
            view.logout();
        }else if (logoutIndicator == 0){
            view.stopAnimation();
            view.showErrorMessage("Uploaded Successfully");
        }
    }

    @Override
    public void onFail(String errorMessage) {
        view.showErrorMessage(errorMessage);
    }

    @Override
    public void onDownloadMealsSuccess(List<MealPojo> favMeals, List<PlanMeal> planMeals) {
    }


}
