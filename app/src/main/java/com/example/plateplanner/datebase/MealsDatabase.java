package com.example.plateplanner.datebase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.plateplanner.startactivity.model.MealPojo;

@Database(entities = {MealPojo.class}, version = 1)
public abstract class MealsDatabase extends RoomDatabase {
    private static MealsDatabase instance = null;

    public abstract MealDAO mealDAO();

    public static synchronized MealsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), MealsDatabase.class, "mealsDb")
                    .build();
        }
        return instance;
    }
}
