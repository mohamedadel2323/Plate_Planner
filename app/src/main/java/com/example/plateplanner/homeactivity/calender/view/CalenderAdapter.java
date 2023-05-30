package com.example.plateplanner.homeactivity.calender.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plateplanner.R;


import java.util.List;

public class CalenderAdapter extends RecyclerView.Adapter<CalenderAdapter.ViewHolder> {
    public final String TAG = "CalenderAdapter";
    Context context;
    List<String> days;
    CalenderAdapter.OnDayCardClickListener dayListener;

    public CalenderAdapter(Context context, List<String> days, CalenderAdapter.OnDayCardClickListener dayListener) {
        this.context = context;
        this.days = days;
        this.dayListener = dayListener;
    }

    public void setDays(List<String> days) {
        this.days = days;
        notifyDataSetChanged();
    }

    public interface OnDayCardClickListener {
        void onDayClick(String day);
    }
    public interface OnDayCardLongClickListener {
        void onDayLongClick(String day);
    }

    @NonNull
    @Override
    public CalenderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new CalenderAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CalenderAdapter.ViewHolder holder, int position) {
        holder.dayName.setText(days.get(position));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dayListener.onDayClick(days.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView dayName;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dayName = itemView.findViewById(com.example.plateplanner.R.id.dayName);
            cardView = itemView.findViewById(com.example.plateplanner.R.id.dayCard);
        }
    }
}
