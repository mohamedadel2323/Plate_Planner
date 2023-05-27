package com.example.plateplanner.homeactivity.search.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.SearchView;

import com.example.plateplanner.R;
import com.example.plateplanner.homeactivity.search.presenter.SearchFragmentPresenter;
import com.example.plateplanner.network.ApiClient;
import com.example.plateplanner.network.FirebaseCalls;
import com.example.plateplanner.startactivity.model.AuthSharedPreferences;
import com.example.plateplanner.startactivity.model.CategoryPojo;
import com.example.plateplanner.startactivity.model.MealPojo;
import com.example.plateplanner.startactivity.model.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchFragment extends Fragment implements SearchFragmentViewInterface, SearchAdapter.OnMealCardClickListener , SearchAdapter.OnCategoryCardClickListener {
    SearchView searchView;
    RadioGroup radioGroup;
    RecyclerView mealsRecyclerView;
    SearchFragmentPresenter searchFragmentPresenter;
    SearchAdapter mealsSearchAdapter;
    SearchAdapter categorySearchAdapter;
    ArrayList<MealPojo> meals = new ArrayList<>();
    ArrayList<CategoryPojo> categories = new ArrayList<>();

    public SearchFragment() {
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
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchFragmentPresenter = new SearchFragmentPresenter(this, Repository.getInstance(AuthSharedPreferences.getInstance(getContext()), FirebaseCalls.getInstance(), ApiClient.getInstance()));
        mealsSearchAdapter = new SearchAdapter(getContext(), meals , categories, this , this , 0);
        categorySearchAdapter = new SearchAdapter(getContext(), meals , categories, this , this , 0);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        initUi(view);
        mealsRecyclerView.setAdapter(mealsSearchAdapter);
        listeners();

    }

    private void initUi(View view) {
        searchView = view.findViewById(R.id.searchView);
        radioGroup = view.findViewById(R.id.radioGroup);
        mealsRecyclerView = view.findViewById(R.id.mealsRv);
    }

    private void listeners() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (Objects.equals(query, "")) {
                    return false;
                }
                searchFragmentPresenter.searchByName(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (Objects.equals(newText, "")) {
                    return false;
                }
                mealsRecyclerView.swapAdapter(mealsSearchAdapter , true);
                searchFragmentPresenter.searchByLetter(newText);
                return true;
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.categoryRb:
                        searchFragmentPresenter.getCategories();
                        break;
                    case R.id.CountryRb:
                        searchFragmentPresenter.getCountries();
                        break;
                    case R.id.ingredientRb:

                        break;
                }
            }
        });
    }


    @Override
    public void showSearchResult(List<MealPojo> mealPojoList) {
        mealsSearchAdapter.setMeals(mealPojoList);
    }

    @Override
    public void showCategoriesResult(List<CategoryPojo> categoryPojos) {
        mealsRecyclerView.swapAdapter(categorySearchAdapter , true);
        categorySearchAdapter.setCategories(categoryPojos);
    }

    @Override
    public void showErrorMessage(String errorMessage) {
    }

    @Override
    public void onMealClick(MealPojo meal) {
        Navigation.findNavController(getView()).navigate(SearchFragmentDirections.actionSearchFragmentToDetailsFragment(meal));
    }

    @Override
    public void onCategoryClick(CategoryPojo category) {

    }
}