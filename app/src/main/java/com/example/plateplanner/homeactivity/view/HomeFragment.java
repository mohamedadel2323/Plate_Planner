package com.example.plateplanner.homeactivity.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.plateplanner.R;
import com.example.plateplanner.RecyclerAdapter;
import com.example.plateplanner.homeactivity.presenter.HomeFragmentPresenter;
import com.example.plateplanner.network.ApiClient;
import com.example.plateplanner.network.FirebaseCalls;
import com.example.plateplanner.startactivity.model.AreaResponse;
import com.example.plateplanner.startactivity.model.AreaResponse.AreaPojo;
import com.example.plateplanner.startactivity.model.AuthSharedPreferences;
import com.example.plateplanner.startactivity.model.CategoryPojo;
import com.example.plateplanner.startactivity.model.MealPojo;
import com.example.plateplanner.startactivity.model.Repository;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements HomeFragmentViewInterface, RecyclerAdapter.OnCountryCardClickListener, RecyclerAdapter.OnAreaCardClickListener {
    private final String TAG = "HomeFragment";
    ImageView mealImage;
    TextView mealName;
    HomeFragmentPresenter homeFragmentPresenter;
    RecyclerView categoriesRv;
    RecyclerView countriesRv;
    RecyclerAdapter categoriesAdapter;
    RecyclerAdapter areasAdapter;
    LottieAnimationView loading;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeFragmentPresenter = new HomeFragmentPresenter(this, Repository.getInstance(AuthSharedPreferences.getInstance(getContext()), FirebaseCalls.getInstance(), ApiClient.getInstance()));
        initUi(view);
        loading.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext());
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoriesRv.setLayoutManager(layoutManager);
        categoriesAdapter = new RecyclerAdapter(getContext(), new ArrayList<CategoryPojo>(), new ArrayList<AreaPojo>(), this, this, 0);
        areasAdapter = new RecyclerAdapter(getContext(), new ArrayList<CategoryPojo>(), new ArrayList<AreaPojo>(), this, this, 1);
        categoriesRv.setAdapter(categoriesAdapter);
        countriesRv.setLayoutManager(layoutManager2);
        countriesRv.setAdapter(areasAdapter);
        homeFragmentPresenter.getDailyInspirationMeal();
        homeFragmentPresenter.getCategories();
        homeFragmentPresenter.getCountries();
    }

    private void initUi(View view) {
        mealImage = view.findViewById(R.id.dailyMealIv);
        mealName = view.findViewById(R.id.dailyMealTv);
        categoriesRv = view.findViewById(R.id.categoriesRv);
        countriesRv = view.findViewById(R.id.countriesRv);
        loading = view.findViewById(R.id.loading);
    }

    @Override
    public void showDailyInspiration(MealPojo mealPojo) {
        Log.e(TAG, mealPojo.toString());
        mealName.setText(mealPojo.getMealName());
        Glide.with(getContext())
                .load(mealPojo.getStrMealThumb())
                .placeholder(R.drawable.loading_img)
                .error(R.drawable.ic_broken_image)
                .into(mealImage);
    }

    @Override
    public void showConnectionError(String errorMessage) {
        mealImage.setImageResource(R.drawable.ic_connection_error);
        Toast.makeText(getContext(), "No Connection", Toast.LENGTH_SHORT).show();
        loading.pauseAnimation();
        loading.setVisibility(View.GONE);
    }

    @Override
    public void showCategoriesList(List<CategoryPojo> categories) {
        Log.i(TAG, categories.toString());
        categoriesAdapter.setCategories(categories);

    }

    @Override
    public void showAreasList(List<AreaPojo> areas) {
        areasAdapter.setAreas(areas);
        loading.pauseAnimation();
        loading.setVisibility(View.GONE);
    }

    @Override
    public void onCountryClick(CategoryPojo category) {
        Toast.makeText(getContext(), category.getStrCategory(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAreaClick(AreaPojo area) {
        Toast.makeText(getContext(), area.getStrArea(), Toast.LENGTH_SHORT).show();
    }
}