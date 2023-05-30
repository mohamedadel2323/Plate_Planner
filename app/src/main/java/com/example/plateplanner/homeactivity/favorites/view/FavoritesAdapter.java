package com.example.plateplanner.homeactivity.favorites.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.plateplanner.R;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plateplanner.model.MealPojo;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {
    public final String TAG = "FavoritesAdapter";
    Context context;
    List<MealPojo> meals;
    OnFavoriteMealCardClickListener mealListener;

    public FavoritesAdapter(Context context, List<MealPojo> meals , OnFavoriteMealCardClickListener mealListener) {
        this.context = context;
        this.meals = meals;
        this.mealListener = mealListener;
    }

    public void setMeals(List<MealPojo> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    public interface OnFavoriteMealCardClickListener {
        void onFavoriteMealClick(MealPojo meal);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_custom_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesAdapter.ViewHolder holder, int position) {
        holder.mealName.setText(meals.get(position).getMealName());
        Glide.with(context)
                .load(meals.get(position).getStrMealThumb())
                .placeholder(R.drawable.loading_img)
                .error(R.drawable.ic_broken_image)
                .into(holder.mealImage);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mealListener.onFavoriteMealClick(meals.get(holder.getAdapterPosition()));
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
