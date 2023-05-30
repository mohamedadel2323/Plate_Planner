package com.example.plateplanner.model;

import androidx.lifecycle.LiveData;

import com.example.plateplanner.network.FirebaseDelegate;
import com.example.plateplanner.network.NetworkDelegate;
import com.example.plateplanner.network.SearchNetworkDelegate;

import java.util.List;

public interface RepositoryInterface {
    public void setLoginStatus(Boolean isLogin);
    public boolean getLoginStatus();
    public void signIn(AuthModel authModel ,FirebaseDelegate firebaseDelegate);
    public void SignUp(AuthModel authModel ,FirebaseDelegate firebaseDelegate);
    public void getDailyInspiration(NetworkDelegate networkDelegate);
    public void getCategories(NetworkDelegate networkDelegate);
    public void getAreas(NetworkDelegate networkDelegate);

    public void getIngredients(NetworkDelegate networkDelegate);

    public void searchByLetter(String searchQuery , SearchNetworkDelegate networkDelegate);
    public void searchByName(String searchQuery , SearchNetworkDelegate networkDelegate);

    public void filterByCountry(String filterQuery, SearchNetworkDelegate networkDelegate);

    public void filterByCategory(String filterQuery, SearchNetworkDelegate networkDelegate);

    public void filterByIngredient(String filterQuery, SearchNetworkDelegate networkDelegate);

    public void getMealById(int mealId , SearchNetworkDelegate networkDelegate);

    public LiveData<List<MealPojo>>getCachedMeals();
    public void addToFavorites(MealPojo mealPojo);
    public void removeFromFavorites(MealPojo mealPojo);

    LiveData<List<MealPojo>> getAllFavoriteMeals();

    void clearAllFavoriteMeals();

    void insertFavMealList(List<MealPojo> favMeals);

    LiveData<List<PlanMeal>> getAllPlanMeals();

    void clearAllPlanMeals();

    void insertPlanMealList(List<PlanMeal> planMeals);

    public LiveData<Boolean> checkExistence(String mealId);


    void addToPlan(PlanMeal planMeal);

    void removeFromPlan(PlanMeal planMeal);

    LiveData<List<PlanMeal>> getPlanMealsByDay(String day);

    void uploadMeals(String email, List<PlanMeal> planMeals, List<MealPojo> favMeals, FirebaseDelegate firebaseDelegate);

    void downloadMeals(String email, FirebaseDelegate firebaseDelegate);
}
