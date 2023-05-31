package com.example.plateplanner.network;


import com.example.plateplanner.model.AreaResponse;
import com.example.plateplanner.model.CategoryResponse;
import com.example.plateplanner.model.IngredientResponse;
import com.example.plateplanner.model.MealResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
        Call<MealResponse> meals = apiService.getDailyInspiration();
        Callback responseCallback = new Callback<MealResponse>() {

            @Override
            public void onResponse(Call<MealResponse> call, retrofit2.Response<MealResponse> response) {
                if (response.isSuccessful()) {
                    networkDelegate.onSuccessResult(response.body().getMeals().get(0));
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
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
                    networkDelegate.onAreaSuccessResult(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<AreaResponse> call, Throwable t) {
                networkDelegate.onCategoryFailureResult(t.getLocalizedMessage());
            }
        };
        areas.enqueue(responseCallback);
    }

    public void startGetIngredient(NetworkDelegate networkDelegate) {
        Call<IngredientResponse> meals = apiService.getIngredients();
        meals.enqueue(new Callback<IngredientResponse>() {

            @Override
            public void onResponse(Call<IngredientResponse> call, Response<IngredientResponse> response) {
                if (response.isSuccessful()) {
                    networkDelegate.onIngredientSuccessResult(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<IngredientResponse> call, Throwable t) {
                    networkDelegate.onIngredientFailureResult(t.getLocalizedMessage());
            }
        });
    }

    public void startSearchCall(String searchQuery, SearchNetworkDelegate networkDelegate) {
        Call<MealResponse> meals = apiService.search(searchQuery);
        meals.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful()) {
                    networkDelegate.onSearchSuccess(response.body().getMeals());

                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                networkDelegate.onSearchFailure(t.getLocalizedMessage());
            }
        });
    }

    public void startSearchByNameCall(String searchQuery, SearchNetworkDelegate networkDelegate) {
        Call<MealResponse> meals = apiService.searchByName(searchQuery);
        meals.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful()) {
                    networkDelegate.onSearchSuccess(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                networkDelegate.onSearchFailure(t.getLocalizedMessage());
            }
        });
    }

    public void startFilterByCategory(String filterQuery, SearchNetworkDelegate networkDelegate) {
        Call<MealResponse> meals = apiService.filterByCategory(filterQuery);
        meals.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful()) {
                    networkDelegate.onSearchSuccess(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                networkDelegate.onSearchFailure(t.getLocalizedMessage());
            }
        });
    }

    public void startFilterByCountry(String filterQuery, SearchNetworkDelegate networkDelegate) {
        Call<MealResponse> meals = apiService.filterByArea(filterQuery);
        meals.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful()) {
                    networkDelegate.onSearchSuccess(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                networkDelegate.onSearchFailure(t.getLocalizedMessage());
            }
        });
    }

    public void startFilterByIngredient(String filterQuery, SearchNetworkDelegate networkDelegate) {
        Call<MealResponse> meals = apiService.filterByIngredient(filterQuery);
        meals.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful()) {
                    networkDelegate.onSearchSuccess(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                networkDelegate.onSearchFailure(t.getLocalizedMessage());
            }
        });
    }

    public void startGetMealById(int mealId, SearchNetworkDelegate networkDelegate) {
        Call<MealResponse> meals = apiService.getMealById(mealId);
        meals.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful()) {
                    networkDelegate.onGetMealByIdSuccess(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                networkDelegate.onSearchFailure(t.getLocalizedMessage());
            }
        });
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

    @Override
    public void getIngredients(NetworkDelegate networkDelegate) {
        ApiClient.apiClient.startGetIngredient(networkDelegate);
    }

    @Override
    public void searchByLetter(String searchQuery, SearchNetworkDelegate networkDelegate) {
        ApiClient.apiClient.startSearchCall(searchQuery, networkDelegate);
    }

    @Override
    public void searchByName(String searchQuery, SearchNetworkDelegate networkDelegate) {
        ApiClient.apiClient.startSearchByNameCall(searchQuery, networkDelegate);
    }

    @Override
    public void getMealById(int mealId, SearchNetworkDelegate networkDelegate) {
        ApiClient.apiClient.startGetMealById(mealId , networkDelegate);
    }

    @Override
    public void filterByCategory(String filterQuery, SearchNetworkDelegate networkDelegate) {
        ApiClient.apiClient.startFilterByCategory(filterQuery, networkDelegate);
    }

    @Override
    public void filterByCountry(String filterQuery, SearchNetworkDelegate networkDelegate) {
        ApiClient.apiClient.startFilterByCountry(filterQuery, networkDelegate);
    }

    @Override
    public void filterByIngredient(String filterQuery, SearchNetworkDelegate networkDelegate) {
        startFilterByIngredient(filterQuery, networkDelegate);
    }

}
