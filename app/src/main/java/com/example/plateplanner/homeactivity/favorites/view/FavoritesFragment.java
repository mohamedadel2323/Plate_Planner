package com.example.plateplanner.homeactivity.favorites.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.plateplanner.R;
import com.example.plateplanner.datebase.ConcreteLocalSource;
import com.example.plateplanner.homeactivity.favorites.presenter.FavoritesFragmentPresenter;
import com.example.plateplanner.network.ApiClient;
import com.example.plateplanner.network.FirebaseCalls;
import com.example.plateplanner.model.AuthSharedPreferences;
import com.example.plateplanner.model.MealPojo;
import com.example.plateplanner.model.Repository;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment implements FavoritesFragmentViewInterface, FavoritesAdapter.OnFavoriteMealCardClickListener {

    RecyclerView recyclerView;
    FavoritesFragmentPresenter favoritesFragmentPresenter;
    FavoritesAdapter favoritesAdapter;
    List<MealPojo>meals = new ArrayList<>();
    public FavoritesFragment() {
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
        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.favoritesRv);
        favoritesAdapter = new FavoritesAdapter(getContext() , meals , this);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext() , 2);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(favoritesAdapter);
        favoritesFragmentPresenter = new FavoritesFragmentPresenter(this , Repository.getInstance(AuthSharedPreferences.getInstance(getContext()), FirebaseCalls.getInstance() , ApiClient.getInstance() , ConcreteLocalSource.getInstance(getContext())));
        favoritesFragmentPresenter.getAllFavoriteMeals();
    }

    @Override
    public void getAllFavoriteMeals(LiveData<List<MealPojo>> meals) {
        meals.observe(getViewLifecycleOwner(), new Observer<List<MealPojo>>() {
            @Override
            public void onChanged(List<MealPojo> mealPojoList) {
                favoritesAdapter.setMeals(mealPojoList);
            }
        });
    }

    @Override
    public void onFavoriteMealClick(MealPojo meal) {
        Navigation.findNavController(getView())
                .navigate(FavoritesFragmentDirections.actionFavouritesFragmentToDetailsFragment(meal));
    }
}