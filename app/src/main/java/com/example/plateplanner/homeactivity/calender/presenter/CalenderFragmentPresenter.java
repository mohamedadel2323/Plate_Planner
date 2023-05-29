package com.example.plateplanner.homeactivity.calender.presenter;

import androidx.lifecycle.LiveData;

import com.example.plateplanner.homeactivity.calender.view.CalenderFragmentViewInterface;
import com.example.plateplanner.startactivity.model.PlanMeal;
import com.example.plateplanner.startactivity.model.Repository;

import java.util.List;

public class CalenderFragmentPresenter {
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
}
