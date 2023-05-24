package com.example.plateplanner.network;

import android.util.Log;

import com.example.plateplanner.startactivity.model.AreaResponse;
import com.example.plateplanner.startactivity.model.CategoryResponse;
import com.example.plateplanner.startactivity.model.RandomResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient implements RemoteSource {
    private final String TAG = "ApiClient";
    ApiService apiService;
    private static ApiClient apiClient = null;
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";

    private ApiClient() {
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public static ApiClient getInstance() {
        if (apiClient == null) {
            apiClient = new ApiClient();
        }
        return apiClient;
    }

    public void startRandomMealCall(NetworkDelegate networkDelegate) {
        Call<RandomResponse> meals = apiService.getDailyInspiration();
        Callback responseCallback = new Callback<RandomResponse>() {


            @Override
            public void onResponse(Call<RandomResponse> call, retrofit2.Response<RandomResponse> response) {
                if (response.isSuccessful()) {
                    networkDelegate.onSuccessResult(response.body().getMeals().get(0));
                }
            }

            @Override
            public void onFailure(Call<RandomResponse> call, Throwable t) {
                networkDelegate.onFailureResult(t.getLocalizedMessage());
            }
        };

        meals.enqueue(responseCallback);
    }

    public void startGetCategoriesCall(NetworkDelegate networkDelegate) {
        Call<CategoryResponse> categories = apiService.getCategories();
        Callback responseCallback = new Callback<CategoryResponse>() {

            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, response.body().getMealCategories().size() + "");
                    networkDelegate.onCategorySuccessResult(response.body().getMealCategories());
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                networkDelegate.onCategoryFailureResult(t.getLocalizedMessage());
            }
        };

        categories.enqueue(responseCallback);
    }

    public void startGetAreasCall(NetworkDelegate networkDelegate) {
        Call<AreaResponse> areas = apiService.getAreas();
        Callback<AreaResponse> responseCallback = new Callback<AreaResponse>() {
            @Override
            public void onResponse(Call<AreaResponse> call, Response<AreaResponse> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "call success");
                    networkDelegate.onAreaSuccessResult(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<AreaResponse> call, Throwable t) {
                Log.i(TAG, "call failed");
                networkDelegate.onCategoryFailureResult(t.getLocalizedMessage());
            }
        };
        areas.enqueue(responseCallback);
    }

    @Override
    public void getMealFromNetwork(NetworkDelegate networkDelegate) {
        ApiClient.apiClient.startRandomMealCall(networkDelegate);
    }

    @Override
    public void getCategories(NetworkDelegate networkDelegate) {
        ApiClient.apiClient.startGetCategoriesCall(networkDelegate);
    }

    @Override
    public void getAreas(NetworkDelegate networkDelegate) {
        ApiClient.apiClient.startGetAreasCall(networkDelegate);
    }
}
