package com.example.plateplanner.model;

import androidx.lifecycle.LiveData;

import com.example.plateplanner.datebase.LocalSource;
import com.example.plateplanner.network.FirebaseDelegate;
import com.example.plateplanner.network.FirebaseSource;
import com.example.plateplanner.network.NetworkDelegate;
import com.example.plateplanner.network.RemoteSource;
import com.example.plateplanner.network.SearchNetworkDelegate;

import java.util.List;

public class Repository implements RepositoryInterface {
    private static Repository repository = null;
    SharedPreferencesInterface sharedSource;
    FirebaseSource firebaseSource;
    RemoteSource remoteSource;
    LocalSource localSource;

    private Repository(SharedPreferencesInterface sharedSource, FirebaseSource firebaseSource, RemoteSource remoteSource, LocalSource localSource) {
        this.sharedSource = sharedSource;
        this.firebaseSource = firebaseSource;
        this.remoteSource = remoteSource;
        this.localSource = localSource;
    }

    public static synchronized Repository getInstance(SharedPreferencesInterface sharedSource, FirebaseSource firebaseSource, RemoteSource remoteSource, LocalSource localSource) {
        if (repository == null) {
            repository = new Repository(sharedSource, firebaseSource, remoteSource, localSource);
        }
        return repository;
    }

    @Override
    public void setLoginStatus(Boolean isLogin) {
        sharedSource.setLoginStatus(isLogin);
    }

    @Override
    public boolean getLoginStatus() {
        return sharedSource.getLoginStatus();
    }

    @Override
    public void signIn(AuthModel authModel, FirebaseDelegate firebaseDelegate) {
        firebaseSource.signIn(authModel, firebaseDelegate);
    }

    @Override
    public void SignUp(AuthModel authModel, FirebaseDelegate firebaseDelegate) {
        firebaseSource.signup(authModel, firebaseDelegate);
    }

    @Override
    public void getDailyInspiration(NetworkDelegate networkDelegate) {
        remoteSource.getMealFromNetwork(networkDelegate);
    }

    @Override
    public void getCategories(NetworkDelegate networkDelegate) {
        remoteSource.getCategories(networkDelegate);
    }

    @Override
    public void getAreas(NetworkDelegate networkDelegate) {
        remoteSource.getAreas(networkDelegate);
    }

    @Override
    public void getIngredients(NetworkDelegate networkDelegate) {
        remoteSource.getIngredients(networkDelegate);
    }

    @Override
    public void searchByLetter(String searchQuery, SearchNetworkDelegate networkDelegate) {
        remoteSource.searchByLetter(searchQuery, networkDelegate);
    }

    @Override
    public void searchByName(String searchQuery, SearchNetworkDelegate networkDelegate) {
        remoteSource.searchByName(searchQuery, networkDelegate);
    }

    @Override
    public void filterByCategory(String filterQuery, SearchNetworkDelegate networkDelegate) {
        remoteSource.filterByCategory(filterQuery, networkDelegate);
    }

    @Override
    public void filterByCountry(String filterQuery, SearchNetworkDelegate networkDelegate) {
        remoteSource.filterByCountry(filterQuery, networkDelegate);
    }

    @Override
    public void filterByIngredient(String filterQuery, SearchNetworkDelegate networkDelegate) {
        remoteSource.filterByIngredient(filterQuery, networkDelegate);
    }

    @Override
    public void getMealById(int mealId, SearchNetworkDelegate networkDelegate) {
        remoteSource.getMealById(mealId, networkDelegate);
    }

    @Override
    public LiveData<List<MealPojo>> getCachedMeals() {
        return localSource.getAllMeals();
    }

    @Override
    public void addToFavorites(MealPojo mealPojo) {
        localSource.insertMeal(mealPojo);
    }

    @Override
    public void removeFromFavorites(MealPojo mealPojo) {
        localSource.deleteMeal(mealPojo);
    }

    @Override
    public LiveData<List<MealPojo>> getAllFavoriteMeals() {
        return localSource.getAllMeals();
    }

    @Override
    public void clearAllFavoriteMeals() {
        localSource.clearAllFavoriteMeals();
    }

    @Override
    public void insertFavMealList(List<MealPojo> favMeals) {
        localSource.insertFavMealList(favMeals);
    }

    @Override
    public LiveData<List<PlanMeal>> getAllPlanMeals() {
        return localSource.getAllPlanMeals();
    }

    @Override
    public void clearAllPlanMeals() {
        localSource.clearAllPlanMeals();
    }

    @Override
    public void insertPlanMealList(List<PlanMeal> planMeals) {
        localSource.insertPlanMealList(planMeals);
    }

    @Override
    public LiveData<Boolean> checkExistence(String mealId) {
        return localSource.checkExistence(mealId);
    }

    @Override
    public void addToPlan(PlanMeal planMeal) {
        localSource.insertPlanMeal(planMeal);
    }

    @Override
    public void removeFromPlan(PlanMeal planMeal) {
        localSource.deletePlanMeal(planMeal);
    }

    @Override
    public LiveData<List<PlanMeal>> getPlanMealsByDay(String day) {
        return localSource.getAllPlanMealsByDay(day);
    }


    @Override
    public void uploadMeals(String email, List<PlanMeal> planMeals, List<MealPojo> favMeals, FirebaseDelegate firebaseDelegate) {
        firebaseSource.uploadMeals(email , planMeals , favMeals, firebaseDelegate);
    }
    @Override
    public void downloadMeals(String email, FirebaseDelegate firebaseDelegate) {
        firebaseSource.downloadMeals(email , firebaseDelegate);
    }

}
