package com.example.plateplanner.datebase;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.plateplanner.model.MealPojo;
import com.example.plateplanner.model.PlanMeal;

import java.util.List;

public class ConcreteLocalSource implements LocalSource {


    private MealDAO mealDAO;
    private static ConcreteLocalSource concreteLocalSource = null;

    private ConcreteLocalSource(Context context) {
        mealDAO = MealsDatabase.getInstance(context).mealDAO();
    }

    public static synchronized ConcreteLocalSource getInstance(Context context) {

        if (concreteLocalSource == null) {
            concreteLocalSource = new ConcreteLocalSource(context);
        }
        return concreteLocalSource;
    }

    @Override
    public void insertMeal(MealPojo meal) {
        new Thread() {
            public void run() {
                mealDAO.insertMeal(meal);
            }
        }.start();
    }

    @Override
    public void insertFavMealList(List<MealPojo> favMeals) {
        new Thread() {
            public void run() {
                mealDAO.insertFavMealList(favMeals);
            }
        }.start();
    }

    @Override
    public void insertPlanMealList(List<PlanMeal> planMeals) {
        new Thread() {
            public void run() {
                mealDAO.insertPlanMealList(planMeals);
            }
        }.start();
    }

    @Override
    public void deleteMeal(MealPojo meal) {
        new Thread() {
            public void run() {
                mealDAO.deleteMeal(meal);
            }
        }.start();
    }

    @Override
    public void clearAllFavoriteMeals() {
        new Thread() {
            public void run() {
                mealDAO.clearAllMeals();
            }
        }.start();
    }

    @Override
    public LiveData<List<MealPojo>> getAllMeals() {
        return mealDAO.getAllMeals();
    }

    @Override
    public LiveData<Boolean> checkExistence(String mealId) {
        return mealDAO.checkExistence(mealId);
    }

    @Override
    public void insertPlanMeal(PlanMeal planMeal) {
        new Thread() {
            public void run() {
                mealDAO.insertPlanMeal(planMeal);
            }
        }.start();
    }

    @Override
    public void deletePlanMeal(PlanMeal planMeal) {
        new Thread() {
            public void run() {
                mealDAO.deletePlanMeal(planMeal);
            }
        }.start();
    }

    @Override
    public LiveData<List<PlanMeal>> getAllPlanMeals() {
        return mealDAO.getAllPlanMeals();
    }

    @Override
    public void clearAllPlanMeals() {
        new Thread() {
            public void run() {
                mealDAO.clearAllPlanMeals();
            }
        }.start();
    }

    @Override
    public LiveData<List<PlanMeal>> getAllPlanMealsByDay(String day) {
        return mealDAO.getAllPlanMealsByDay(day);
    }
}
