package com.example.plateplanner.homeactivity.calender.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.plateplanner.R;
import com.example.plateplanner.datebase.ConcreteLocalSource;
import com.example.plateplanner.homeactivity.calender.presenter.CalenderFragmentPresenter;
import com.example.plateplanner.network.ApiClient;
import com.example.plateplanner.network.FirebaseCalls;
import com.example.plateplanner.startactivity.model.AuthSharedPreferences;
import com.example.plateplanner.startactivity.model.MealPojo;
import com.example.plateplanner.startactivity.model.PlanMeal;
import com.example.plateplanner.startactivity.model.Repository;

import java.util.ArrayList;
import java.util.List;


public class CalenderFragment extends Fragment implements CalenderFragmentViewInterface, CalenderAdapter.OnDayCardClickListener, PlanMealsAdapter.OnPlanMealCardClickListener, PlanMealsAdapter.OnPlanMealCardLongClickListener {


    public enum DayOfWeek {
        SUNDAY,
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY
    }

    List<String> weekDays = new ArrayList<>();
    List<PlanMeal> planMeals = new ArrayList<>();
    CalenderAdapter calenderAdapter;
    PlanMealsAdapter planMealsAdapter;
    CalenderFragmentPresenter calenderFragmentPresenter;
    RecyclerView daysRv;
    RecyclerView planMealsRv;

    public CalenderFragment() {
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
        return inflater.inflate(R.layout.fragment_calender, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calenderFragmentPresenter = new CalenderFragmentPresenter(this, Repository.getInstance(AuthSharedPreferences.getInstance(getContext()), FirebaseCalls.getInstance(), ApiClient.getInstance(), ConcreteLocalSource.getInstance(getContext())));
        initUi(view);

    }

    private void initUi(View view) {
        weekDays.add(DayOfWeek.SUNDAY.toString());
        weekDays.add(DayOfWeek.MONDAY.toString());
        weekDays.add(DayOfWeek.TUESDAY.toString());
        weekDays.add(DayOfWeek.WEDNESDAY.toString());
        weekDays.add(DayOfWeek.THURSDAY.toString());
        weekDays.add(DayOfWeek.FRIDAY.toString());
        weekDays.add(DayOfWeek.SATURDAY.toString());
        daysRv = view.findViewById(R.id.planRv);
        planMealsRv = view.findViewById(R.id.mealsPlanRv);
        calenderAdapter = new CalenderAdapter(getContext(), weekDays, this);
        LinearLayoutManager planLayoutManager = new LinearLayoutManager(getContext());
        planLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        daysRv.setLayoutManager(planLayoutManager);
        daysRv.setAdapter(calenderAdapter);
        planMealsAdapter = new PlanMealsAdapter(getContext(), planMeals, this, this);
        planMealsRv.setAdapter(planMealsAdapter);

    }

    @Override
    public void onDayClick(String day) {
        getMealsByDay(day);
    }

    @Override
    public void getMealsByDay(String day) {
        calenderFragmentPresenter.getMealsByName(day).observe(this, new Observer<List<PlanMeal>>() {
            @Override
            public void onChanged(List<PlanMeal> planMeals) {
                planMealsAdapter.setMeals(planMeals);
            }
        });
    }

    @Override
    public void onPlanMealClick(PlanMeal meal) {
        MealPojo mealPojo = new MealPojo(meal.getIdMeal(), meal.getMealName(), meal.getStrDrinkAlternate(), meal.getStrCategory(), meal.getStrArea(), meal.getStrInstructions(), meal.getStrMealThumb(), meal.getStrTags(), meal.getStrYoutube(), meal.getStrIngredient1(), meal.getStrIngredient2(), meal.getStrIngredient3(), meal.getStrIngredient4(), meal.getStrIngredient5(), meal.getStrIngredient6(), meal.getStrIngredient7(), meal.getStrIngredient8(), meal.getStrIngredient9(), meal.getStrIngredient10(), meal.getStrIngredient11(), meal.getStrIngredient12(), meal.getStrIngredient13(), meal.getStrIngredient14(), meal.getStrIngredient15(), meal.getStrIngredient16(), meal.getStrIngredient17(), meal.getStrIngredient18(), meal.getStrIngredient19(), meal.getStrIngredient20(), meal.getStrMeasure1(), meal.getStrMeasure2(), meal.getStrMeasure3(), meal.getStrMeasure4(), meal.getStrMeasure5(), meal.getStrMeasure6(), meal.getStrMeasure7(), meal.getStrMeasure8(), meal.getStrMeasure9(), meal.getStrMeasure10(), meal.getStrMeasure11(), meal.getStrMeasure12(), meal.getStrMeasure13(), meal.getStrMeasure14(), meal.getStrMeasure15(), meal.getStrMeasure16(), meal.getStrMeasure17(), meal.getStrMeasure18(), meal.getStrMeasure19(), meal.getStrMeasure20(), meal.getStrSource(), meal.getStrImageSource(), meal.getStrCreativeCommonsConfirmed(), meal.getDateModified());
        Navigation.findNavController(getView()).navigate(CalenderFragmentDirections.actionCalenderFragmentToDetailsFragment(mealPojo));
    }

    @Override
    public void onMealLongClick(PlanMeal meal) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Confirm Deletion");
        builder.setCancelable(true);

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                calenderFragmentPresenter.deleteFromPlan(meal);
                dialog.cancel();
            }
        });

        builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }
}