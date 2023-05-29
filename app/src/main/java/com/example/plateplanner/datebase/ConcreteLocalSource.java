package com.example.plateplanner.datebase;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.plateplanner.startactivity.model.MealPojo;

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
    public void deleteMeal(MealPojo meal) {
        new Thread() {
            public void run() {
                mealDAO.deleteMeal(meal);
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
}
