package com.example.plateplanner.homeactivity.calender.view;

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
import com.example.plateplanner.homeactivity.favorites.view.FavoritesAdapter;
import com.example.plateplanner.startactivity.model.MealPojo;
import com.example.plateplanner.startactivity.model.PlanMeal;

import java.util.List;

public class PlanMealsAdapter extends RecyclerView.Adapter<PlanMealsAdapter.ViewHolder> {
    public final String TAG = "PlanMealsAdapter";
    Context context;
    List<PlanMeal> meals;
    OnPlanMealCardClickListener mealListener;
    OnPlanMealCardLongClickListener longClickListener;

    public PlanMealsAdapter(Context context, List<PlanMeal> meals , OnPlanMealCardClickListener mealListener , OnPlanMealCardLongClickListener longClickListener) {
        this.context = context;
        this.meals = meals;
        this.mealListener = mealListener;
        this.longClickListener = longClickListener;
    }

    public void setMeals(List<PlanMeal> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    public interface OnPlanMealCardClickListener {
        void onPlanMealClick(PlanMeal meal);
    }
    public interface OnPlanMealCardLongClickListener {
        void onMealLongClick(PlanMeal meal);
    }

    @NonNull
    @Override
    public PlanMealsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new PlanMealsAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_custom_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlanMealsAdapter.ViewHolder holder, int position) {
        holder.mealName.setText(meals.get(position).getMealName());
        Glide.with(context)
                .load(meals.get(position).getStrMealThumb())
                .placeholder(R.drawable.loading_img)
                .error(R.drawable.ic_broken_image)
                .into(holder.mealImage);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mealListener.onPlanMealClick(meals.get(holder.getAdapterPosition()));
            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                longClickListener.onMealLongClick(meals.get(holder.getAdapterPosition()));
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mealName;
        private ImageView mealImage;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImage = itemView.findViewById(com.example.plateplanner.R.id.image);
            mealName = itemView.findViewById(com.example.plateplanner.R.id.title);
            cardView = itemView.findViewById(com.example.plateplanner.R.id.categoryCard);
        }
    }
}
