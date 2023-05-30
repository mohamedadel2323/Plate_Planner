package com.example.plateplanner.network;

import com.example.plateplanner.model.AreaResponse;
import com.example.plateplanner.model.CategoryResponse;
import com.example.plateplanner.model.IngredientResponse;
import com.example.plateplanner.model.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("random.php/meals")
    Call<MealResponse> getDailyInspiration();

    @GET("categories.php")
    Call<CategoryResponse> getCategories();

    @GET("list.php?a=list")
    Call<AreaResponse> getAreas();

    @GET("list.php?i=list")
    Call<IngredientResponse> getIngredients();

    @GET("search.php/meals")
    Call<MealResponse> search(@Query("f") String searchQuery);

    @GET("search.php/meals")
    Call<MealResponse> searchByName(@Query("s") String searchQuery);

    @GET("filter.php/meals")
    Call<MealResponse> filterByCategory(@Query("c") String filterQuery);

    @GET("filter.php/meals")
    Call<MealResponse> filterByArea(@Query("a") String filterQuery);

    @GET("filter.php/meals")
    Call<MealResponse> filterByIngredient(@Query("i") String filterQuery);

    @GET("lookup.php/meals")
    Call<MealResponse> getMealById(@Query("i") int mealId);
}
