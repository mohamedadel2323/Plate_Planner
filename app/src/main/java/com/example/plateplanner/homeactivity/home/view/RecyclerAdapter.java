package com.example.plateplanner.homeactivity.home.view;


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
import com.example.plateplanner.model.AreaResponse;
import com.example.plateplanner.model.AreaResponse.AreaPojo;
import com.example.plateplanner.model.CategoryPojo;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    public final String TAG = "RecyclerAdapter";
    Context context;
    List<CategoryPojo> categories;
    List<AreaResponse.AreaPojo> areas;
    private OnCountryCardClickListener countryListener;
    private OnAreaCardClickListener areaListener;
    private int mode;

    public RecyclerAdapter(Context context, List<CategoryPojo> categories, List<AreaResponse.AreaPojo> areas, OnCountryCardClickListener countryListener, OnAreaCardClickListener areaListener, int mode) {
        this.context = context;
        this.categories = categories;
        this.countryListener = countryListener;
        this.areaListener = areaListener;
        this.mode = mode;
        this.areas = areas;
    }

    public void setCategories(List<CategoryPojo> products) {
        this.categories = products;
        this.notifyDataSetChanged();
    }

    public void setAreas(List<AreaPojo> areas) {
        this.areas = areas;
        this.notifyDataSetChanged();
    }

    public interface OnCountryCardClickListener {
        void onCountryClick(CategoryPojo category);
    }

    public interface OnAreaCardClickListener {
        void onAreaClick(AreaPojo area);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView countryTitle;
        private ImageView image;
        private CardView card;
        private CardView countryCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            if (mode == 0) {
                image = itemView.findViewById(R.id.image);
                title = itemView.findViewById(R.id.title);
                card = itemView.findViewById(R.id.categoryCard);
            } else {
                countryTitle = itemView.findViewById(R.id.countryTitle);
                countryCard = itemView.findViewById(R.id.countryCard);
            }

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v;
        if (mode == 0) {
            v = inflater.inflate(R.layout.category_custom_item, parent, false);
        } else {
            v = inflater.inflate(R.layout.country_custom_view, parent, false);
        }

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        if (mode == 0) {
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
                        countryListener.onCountryClick(categories.get(holder.getAdapterPosition()));
                    }
                }
            });

        } else {
            holder.countryTitle.setText(areas.get(position).getStrArea());
            holder.countryCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        areaListener.onAreaClick(areas.get(holder.getAdapterPosition()));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mode == 0) {
            return categories.size();
        } else {
            return areas.size();
        }
    }
}
