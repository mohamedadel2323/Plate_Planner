package com.example.plateplanner.homeactivity.search.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.plateplanner.R;
import com.example.plateplanner.homeactivity.details.model.Ingredient;
import com.example.plateplanner.startactivity.model.AreaResponse;
import com.example.plateplanner.startactivity.model.CategoryPojo;
import com.example.plateplanner.startactivity.model.IngredientResponse;
import com.example.plateplanner.startactivity.model.MealPojo;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    public final String TAG = "MealsAdapter";
    public static final int MEALS = 0;
    public static final int CATEGORIES = 1;
    public static final int COUNTRIES = 2;
    public static int INGREDIENTS = 3;
    Context context;
    List<MealPojo> meals;
    List<CategoryPojo> categories;
    List<AreaResponse.AreaPojo> countries;
    List<IngredientResponse.IngredientPojo> ingredients;
    private OnMealCardClickListener mealListener;
    private OnCategoryCardClickListener categoryListener;
    private OnCountryCardClickListener countryListener;
    private OnIngredientCardClickListener ingredientListener;
    private int mode;

    public SearchAdapter(Context context, List<MealPojo> meals, List<CategoryPojo> categories, List<AreaResponse.AreaPojo> countries, List<IngredientResponse.IngredientPojo> ingredients, OnMealCardClickListener mealListener, OnCategoryCardClickListener categoryListener, OnCountryCardClickListener countryListener, OnIngredientCardClickListener ingredientListener, int mode) {
        this.context = context;
        this.meals = meals;
        this.categories = categories;
        this.countries = countries;
        this.ingredients = ingredients;
        this.mealListener = mealListener;
        this.categoryListener = categoryListener;
        this.countryListener = countryListener;
        this.ingredientListener = ingredientListener;
        this.mode = mode;
    }


    public void setMeals(List<MealPojo> meals) {
        this.meals = meals;
        this.notifyDataSetChanged();
    }

    public void setCategories(List<CategoryPojo> categories) {
        this.categories = categories;
        this.notifyDataSetChanged();
    }

    public void setCountries(List<AreaResponse.AreaPojo> countries) {
        this.countries = countries;
        this.notifyDataSetChanged();
    }

    public void setIngredients(List<IngredientResponse.IngredientPojo> ingredients) {
        this.ingredients = ingredients;
        this.notifyDataSetChanged();
    }

    public interface OnMealCardClickListener {
        void onMealClick(MealPojo meal);
    }

    public interface OnCategoryCardClickListener {
        void onCategoryClick(CategoryPojo category);
    }

    public interface OnCountryCardClickListener {
        void oncCountryClick(AreaResponse.AreaPojo country);
    }

    public interface OnIngredientCardClickListener {
        void onIngredientClick(IngredientResponse.IngredientPojo ingredient);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView image;
        private CardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            card = itemView.findViewById(R.id.categoryCard);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        v = inflater.inflate(R.layout.category_custom_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (mode == 0) {
            holder.title.setText(meals.get(position).getMealName());
            Glide.with(context)
                    .load(meals.get(position).getStrMealThumb())
                    .placeholder(R.drawable.loading_img)
                    .error(R.drawable.ic_broken_image)
                    .into(holder.image);
            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mode == 0) {
                        mealListener.onMealClick(meals.get(holder.getAdapterPosition()));
                    }
                }
            });
        } else if (mode == 1) {
            holder.title.setText(categories.get(position).getStrCategory());
            Glide.with(context)
                    .load(categories.get(position).getStrCategoryThumb())
                    .placeholder(R.drawable.loading_img)
                    .error(R.drawable.ic_broken_image)
                    .into(holder.image);
            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    categoryListener.onCategoryClick(categories.get(holder.getAdapterPosition()));
                }
            });
        } else if (mode == 2) {
            holder.title.setText(countries.get(position).getStrArea());
            Glide.with(context)
                    .load(R.drawable.ares)
                    .placeholder(R.drawable.loading_img)
                    .error(R.drawable.ic_broken_image)
                    .into(holder.image);
            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    countryListener.oncCountryClick(countries.get(holder.getAdapterPosition()));
                }
            });
        } else if (mode == 3) {
            holder.title.setText(ingredients.get(position).getStrIngredient());
            Glide.with(context)
                    .load("https://www.themealdb.com/images/ingredients/" + ingredients.get(position).getStrIngredient() + ".png")
                    .placeholder(R.drawable.loading_img)
                    .error(R.drawable.ic_broken_image)
                    .into(holder.image);
            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ingredientListener.onIngredientClick(ingredients.get(holder.getAdapterPosition()));
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        if (mode == 0) {
            return meals != null ? meals.size() : 0;
        } else if (mode == 1) {
            return categories.size();
        } else if (mode == 2) {
            return countries.size();
        } else {
            return ingredients.size();
        }
    }
}
