package com.example.plateplanner.network;

import com.example.plateplanner.startactivity.model.AreaResponse;
import com.example.plateplanner.startactivity.model.CategoryPojo;
import com.example.plateplanner.startactivity.model.CategoryResponse;
import com.example.plateplanner.startactivity.model.RandomResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("random.php/meals")
    Call<RandomResponse> getDailyInspiration();

    @GET("categories.php")
    Call<CategoryResponse> getCategories();

    @GET("list.php?a=list")
    Call<AreaResponse> getAreas();
}
