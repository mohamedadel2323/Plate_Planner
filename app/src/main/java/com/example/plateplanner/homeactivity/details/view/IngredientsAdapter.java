package com.example.plateplanner.homeactivity.details.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.plateplanner.R;
import com.example.plateplanner.homeactivity.details.model.Ingredient;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
    public final String TAG = "IngredientsAdapter";
    Context context;
    List<Ingredient> ingredients;

    public IngredientsAdapter(Context context, List<Ingredient> ingredients) {
        this.context = context;
        this.ingredients = ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView measure;
        private ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.ingredientTitle);
            measure = itemView.findViewById(R.id.measure);
            image = itemView.findViewById(R.id.ingredientImage);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.ingredient_custom_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(ingredients.get(position).getIngredientName());
        holder.measure.setText(ingredients.get(position).getIngredientMeasure());
        Glide.with(context)
                .load("https://www.themealdb.com/images/ingredients/"+ingredients.get(position).getIngredientName()+".png")
                .placeholder(R.drawable.loading_img)
                .error(R.drawable.ic_broken_image)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

}
