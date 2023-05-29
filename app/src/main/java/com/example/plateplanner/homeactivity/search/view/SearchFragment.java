package com.example.plateplanner.homeactivity.search.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.SearchView;

import com.example.plateplanner.R;
import com.example.plateplanner.datebase.ConcreteLocalSource;
import com.example.plateplanner.homeactivity.details.model.Ingredient;
import com.example.plateplanner.homeactivity.search.presenter.SearchFragmentPresenter;
import com.example.plateplanner.network.ApiClient;
import com.example.plateplanner.network.FirebaseCalls;
import com.example.plateplanner.startactivity.model.AreaResponse;
import com.example.plateplanner.startactivity.model.AuthSharedPreferences;
import com.example.plateplanner.startactivity.model.CategoryPojo;
import com.example.plateplanner.startactivity.model.IngredientResponse;
import com.example.plateplanner.startactivity.model.MealPojo;
import com.example.plateplanner.startactivity.model.Repository;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;

public class SearchFragment extends Fragment implements SearchFragmentViewInterface, SearchAdapter.OnMealCardClickListener, SearchAdapter.OnCategoryCardClickListener, SearchAdapter.OnCountryCardClickListener, SearchAdapter.OnIngredientCardClickListener {
    private final String TAG = "SearchFragment";
    SearchView searchView;
    RadioGroup radioGroup;
    RecyclerView mealsRecyclerView;
    SearchFragmentPresenter searchFragmentPresenter;
    SearchAdapter mealsSearchAdapter;
    SearchAdapter categorySearchAdapter;
    SearchAdapter countrySearchAdapter;
    SearchAdapter ingredientsSearchAdapter;
    ArrayList<MealPojo> meals = new ArrayList<>();
    ArrayList<CategoryPojo> categories = new ArrayList<>();
    ArrayList<AreaResponse.AreaPojo> countries = new ArrayList<>();
    ArrayList<IngredientResponse.IngredientPojo> ingredients = new ArrayList<>();
    ArrayList<IngredientResponse.IngredientPojo> searchIngredients = new ArrayList<>();
    Disposable ingredientSearch;

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

        searchFragmentPresenter = new SearchFragmentPresenter(this, Repository.getInstance(AuthSharedPreferences.getInstance(getContext()), FirebaseCalls.getInstance(), ApiClient.getInstance(), ConcreteLocalSource.getInstance(getContext())));
        mealsSearchAdapter = new SearchAdapter(getContext(), meals, categories, countries, ingredients, this, this, this, this, SearchAdapter.MEALS);
        categorySearchAdapter = new SearchAdapter(getContext(), meals, categories, countries, ingredients, this, this, this, this, SearchAdapter.CATEGORIES);
        countrySearchAdapter = new SearchAdapter(getContext(), meals, categories, countries, ingredients, this, this, this, this, SearchAdapter.COUNTRIES);
        ingredientsSearchAdapter = new SearchAdapter(getContext(), meals, categories, countries, ingredients, this, this, this, this, SearchAdapter.INGREDIENTS);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        initUi(view);
        mealsRecyclerView.setAdapter(mealsSearchAdapter);
        listeners();
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigation);
        if (bottomNavigationView != null) {
            bottomNavigationView.setVisibility(View.GONE);
        }
        int mode = -1;
        String filter = "";
        Bundle args = getArguments();
        if (args != null) {
            filter = args.getString("filter");
            mode = args.getInt("mode");

            if (!filter.isEmpty()) {
                if (mode == 0) {
                    mealsRecyclerView.swapAdapter(mealsSearchAdapter, true);
                    searchFragmentPresenter.filterByCategory(filter);

                } else if (mode == 1) {
                    Log.i("filter", filter);
                    mealsRecyclerView.swapAdapter(mealsSearchAdapter, true);
                    searchFragmentPresenter.filterByCountry(filter);
                    mealsRecyclerView.swapAdapter(mealsSearchAdapter, true);
                }
            }
        }


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
                mealsRecyclerView.swapAdapter(mealsSearchAdapter, true);
                searchFragmentPresenter.searchByLetter(newText);
                return true;
            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.categoryRb:
                        if (ingredientSearch != null){
                            ingredientSearch.dispose();
                            ingredientSearch = null;
                        }
                        mealsRecyclerView.swapAdapter(categorySearchAdapter, true);
                        searchFragmentPresenter.getCategories();
                        break;
                    case R.id.CountryRb:
                        if (ingredientSearch != null){
                            ingredientSearch.dispose();
                            ingredientSearch = null;
                        }
                        mealsRecyclerView.swapAdapter(countrySearchAdapter, true);
                        searchFragmentPresenter.getCountries();
                        break;
                    case R.id.ingredientRb:
                        mealsRecyclerView.swapAdapter(ingredientsSearchAdapter, true);
                        searchFragmentPresenter.getIngredients();
                        ingredientSearch = Observable.create(new ObservableOnSubscribe<String>() {
                                    @Override
                                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                                        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                            @Override
                                            public boolean onQueryTextSubmit(String query) {
                                                return false;
                                            }

                                            @Override
                                            public boolean onQueryTextChange(String newText) {
                                                emitter.onNext(newText);
                                                return false;
                                            }
                                        });
                                    }
                                }).debounce(2, TimeUnit.SECONDS)
                                .subscribe(term -> {
                                    Observable.fromIterable(ingredients)
                                            .subscribeOn(AndroidSchedulers.mainThread())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .filter(s -> s.getStrIngredient().toLowerCase().contains(term.toLowerCase()))
                                            .subscribe(s -> {
                                                searchIngredients.add(s);
                                                ingredientsSearchAdapter.setIngredients(searchIngredients);
                                            });
                                    searchIngredients.clear();
                                });
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
        categories = (ArrayList<CategoryPojo>) categoryPojos;
        categorySearchAdapter.setCategories(categories);
    }

    @Override
    public void showCountriesResult(List<AreaResponse.AreaPojo> countries) {
        this.countries = (ArrayList<AreaResponse.AreaPojo>) countries;
        countrySearchAdapter.setCountries(this.countries);
    }

    @Override
    public void showIngredientsResult(List<IngredientResponse.IngredientPojo> ingredients) {
        this.ingredients = (ArrayList<IngredientResponse.IngredientPojo>) ingredients;
        ingredientsSearchAdapter.setIngredients(this.ingredients);
    }

    @Override
    public void showErrorMessage(String errorMessage) {
    }

    @Override
    public void goToDetails(List<MealPojo> mealPojoList) {
        //todo change false value with accurate one.
        Navigation.findNavController(getView()).navigate(SearchFragmentDirections.actionSearchFragmentToDetailsFragment(mealPojoList.get(0)));
    }

    @Override
    public void onMealClick(MealPojo meal) {
        Log.i(TAG, meal.toString() + "hewewewewewe");
        searchFragmentPresenter.getMealById(Integer.parseInt(meal.getIdMeal()));
    }

    @Override
    public void onCategoryClick(CategoryPojo category) {
        mealsRecyclerView.swapAdapter(mealsSearchAdapter, true);
        searchFragmentPresenter.filterByCategory(category.getStrCategory());
    }

    @Override
    public void oncCountryClick(AreaResponse.AreaPojo country) {
        mealsRecyclerView.swapAdapter(mealsSearchAdapter, true);
        searchFragmentPresenter.filterByCountry(country.getStrArea());
    }

    @Override
    public void onIngredientClick(IngredientResponse.IngredientPojo ingredient) {
        mealsRecyclerView.swapAdapter(mealsSearchAdapter, true);
        searchFragmentPresenter.filterByIngredient(ingredient.getStrIngredient());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigation);
        if (bottomNavigationView != null) {
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
    }
}