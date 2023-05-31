package com.example.plateplanner.homeactivity.calender.presenter;

import androidx.lifecycle.LiveData;

import com.example.plateplanner.homeactivity.calender.view.CalenderFragmentViewInterface;
import com.example.plateplanner.model.MealPojo;
import com.example.plateplanner.model.PlanMeal;
import com.example.plateplanner.model.Repository;
import com.example.plateplanner.network.FirebaseDelegate;

import java.util.List;

public class CalenderFragmentPresenter implements FirebaseDelegate {
    private CalenderFragmentViewInterface view;
    Repository repository;

    public CalenderFragmentPresenter(CalenderFragmentViewInterface view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }

    public LiveData<List<PlanMeal>> getMealsByName(String day) {
        return repository.getPlanMealsByDay(day);
    }
    public void deleteFromPlan(PlanMeal meal){
        repository.removeFromPlan(meal);
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
        repository.insertPlanMealList(planMeals);
        getMealsByName("SUNDAY");
    }
}
