package com.example.plateplanner.homeactivity.search.presenter;

import com.example.plateplanner.homeactivity.search.view.SearchFragmentViewInterface;
import com.example.plateplanner.network.NetworkDelegate;
import com.example.plateplanner.network.SearchNetworkDelegate;
import com.example.plateplanner.startactivity.model.AreaResponse;
import com.example.plateplanner.startactivity.model.CategoryPojo;
import com.example.plateplanner.startactivity.model.IngredientResponse;
import com.example.plateplanner.startactivity.model.MealPojo;
import com.example.plateplanner.startactivity.model.Repository;

import java.util.List;

public class SearchFragmentPresenter implements SearchNetworkDelegate, NetworkDelegate {

    SearchFragmentViewInterface view;
    Repository repository;

    public SearchFragmentPresenter(SearchFragmentViewInterface view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void searchByLetter(String searchQuery) {
        repository.searchByLetter(searchQuery, this);
    }

    public void searchByName(String searchQuery) {
        repository.searchByName(searchQuery, this);
    }

    public void getMealById(int mealId) {
        repository.getMealById(mealId, this);
    }

    public void filterByCategory(String searchQuery) {
        repository.filterByCategory(searchQuery, this);
    }

    public void filterByCountry(String searchQuery) {
        repository.filterByCountry(searchQuery, this);
    }

    public void filterByIngredient(String searchQuery) {
        repository.filterByIngredient(searchQuery, this);
    }

    public void getCategories() {
        repository.getCategories(this);
    }

    public void getCountries() {
        repository.getAreas(this);
    }

    public void getIngredients() {
        repository.getIngredients(this);
    }

    @Override
    public void onSearchSuccess(List<MealPojo> meals) {
        view.showSearchResult(meals);
    }

    @Override
    public void onSearchFailure(String errorMessage) {
        view.showErrorMessage(errorMessage);
    }

    @Override
    public void onGetMealByIdSuccess(List<MealPojo> meals) {
        view.goToDetails(meals);
    }

    @Override
    public void onSuccessResult(MealPojo mealPojo) {

    }

    @Override
    public void onFailureResult(String errorMessage) {

    }

    @Override
    public void onCategorySuccessResult(List<CategoryPojo> categories) {
        view.showCategoriesResult(categories);
    }

    @Override
    public void onCategoryFailureResult(String errorMessage) {

    }

    @Override
    public void onAreaSuccessResult(List<AreaResponse.AreaPojo> areas) {
        view.showCountriesResult(areas);
    }

    @Override
    public void onAreaFailureResult(String errorMessage) {

    }

    @Override
    public void onIngredientSuccessResult(List<IngredientResponse.IngredientPojo> ingredients) {
        view.showIngredientsResult(ingredients);
    }

    @Override
    public void onIngredientFailureResult(String errorMessage) {

    }
}
