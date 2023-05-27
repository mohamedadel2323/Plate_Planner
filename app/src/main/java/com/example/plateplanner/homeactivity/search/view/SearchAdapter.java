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
import com.example.plateplanner.startactivity.model.CategoryPojo;
import com.example.plateplanner.startactivity.model.MealPojo;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    public final String TAG = "MealsAdapter";
    Context context;
    List<MealPojo> meals;
    List<CategoryPojo> categories;
    private OnMealCardClickListener mealListener;
    private OnCategoryCardClickListener categoryListener;
    private int mode;

    public SearchAdapter(Context context, List<MealPojo> meals, List<CategoryPojo> categories, OnMealCardClickListener mealListener, OnCategoryCardClickListener categoryListener, int mode) {
        this.context = context;
        this.meals = meals;
        this.categories = categories;
        this.mealListener = mealListener;
        this.categoryListener = categoryListener;
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

    public interface OnMealCardClickListener {
        void onMealClick(MealPojo meal);
    }

    public interface OnCategoryCardClickListener {
        void onCategoryClick(CategoryPojo category);
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

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.category_custom_item, parent, false);
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
        } else {
            holder.title.setText(categories.get(position).getStrCategory());
            Glide.with(context)
                    .load(categories.get(position).getStrCategoryThumb())
                    .placeholder(R.drawable.loading_img)
                    .error(R.drawable.ic_broken_image)
                    .into(holder.image);
            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mode == 0) {
                        categoryListener.onCategoryClick(categories.get(holder.getAdapterPosition()));
                    }
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        if (mode == 0) {
            return meals.size();
        } else {
            return categories.size();
        }
    }
}
