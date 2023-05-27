package com.example.plateplanner.homeactivity.home.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.plateplanner.R;
import com.example.plateplanner.homeactivity.home.presenter.HomeFragmentPresenter;
import com.example.plateplanner.homeactivity.view.RecyclerAdapter;
import com.example.plateplanner.network.ApiClient;
import com.example.plateplanner.network.FirebaseCalls;
import com.example.plateplanner.startactivity.model.AreaResponse.AreaPojo;
import com.example.plateplanner.startactivity.model.AuthSharedPreferences;
import com.example.plateplanner.startactivity.model.CategoryPojo;
import com.example.plateplanner.startactivity.model.MealPojo;
import com.example.plateplanner.startactivity.model.Repository;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements HomeFragmentViewInterface, RecyclerAdapter.OnCountryCardClickListener, RecyclerAdapter.OnAreaCardClickListener {
    private final String TAG = "HomeFragment";
    ImageView mealImage;
    TextView mealName;
    HomeFragmentPresenter homeFragmentPresenter;
    RecyclerView categoriesRv;
    TextView categoriesTv;
    RecyclerView countriesRv;
    TextView countriesTv;
    RecyclerAdapter categoriesAdapter;
    RecyclerAdapter areasAdapter;
    ImageButton addToFavoriteBtn;
    LottieAnimationView loading;
    CardView dailyMealCard;
    boolean clicked = false;

    MealPojo dailyMeal;

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
        hideUi();
        clickListeners();
        //getActivity().findViewById(R.id.bottomNavigation).setVisibility(View.GONE);
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

    private void clickListeners() {
        addToFavoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!clicked) {
                    addToFavoriteBtn.setImageResource(R.drawable.solid_heart_icon);
                    clicked = true;
                } else {
                    addToFavoriteBtn.setImageResource(R.drawable.border_heart_icon);
                    clicked = false;
                }
            }
        });

        dailyMealCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(dailyMeal));
            }
        });
    }

    private void initUi(View view) {
        mealImage = view.findViewById(R.id.dailyMealIv);
        mealName = view.findViewById(R.id.dailyMealTv);
        categoriesRv = view.findViewById(R.id.categoriesRv);
        countriesRv = view.findViewById(R.id.countriesRv);
        loading = view.findViewById(R.id.loading);
        categoriesTv = view.findViewById(R.id.categoriesTv);
        countriesTv = view.findViewById(R.id.countriesTv);
        addToFavoriteBtn = view.findViewById(R.id.addToFavoriteBtn);
        dailyMealCard = view.findViewById(R.id.dailyInspirationCard);
    }

    private void hideUi() {
        dailyMealCard.setVisibility(View.GONE);
        mealImage.setVisibility(View.GONE);
        mealName.setVisibility(View.GONE);
        categoriesRv.setVisibility(View.GONE);
        countriesRv.setVisibility(View.GONE);
        categoriesTv.setVisibility(View.GONE);
        countriesTv.setVisibility(View.GONE);
    }

    private void showUi() {
        dailyMealCard.setVisibility(View.VISIBLE);
        mealImage.setVisibility(View.VISIBLE);
        mealName.setVisibility(View.VISIBLE);
        categoriesRv.setVisibility(View.VISIBLE);
        countriesRv.setVisibility(View.VISIBLE);
        categoriesTv.setVisibility(View.VISIBLE);
        countriesTv.setVisibility(View.VISIBLE);
    }

    @Override
    public void showDailyInspiration(MealPojo mealPojo) {
        dailyMeal = mealPojo;
        Log.e(TAG, mealPojo.toString());
        mealName.setText(mealPojo.getMealName());
        Glide.with(getContext())
                .load(mealPojo.getStrMealThumb())
                .placeholder(R.drawable.loading_img)
                .error(R.drawable.ic_broken_image)
                .into(mealImage);
        showUi();
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
        categories.remove(categories.get(6));
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