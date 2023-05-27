package com.example.plateplanner.homeactivity.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.plateplanner.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    NavController navController;
    BottomNavigationView bottomNavigationView;
    private final String BOTTOM_VISIBILITY = "bottomVisibility";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        navController = Navigation.findNavController(this , R.id.home_nav_host);
        NavigationUI.setupWithNavController(bottomNavigationView , navController);
        if (savedInstanceState !=null){
            bottomNavigationView.setVisibility(savedInstanceState.getInt(BOTTOM_VISIBILITY));
        }
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BOTTOM_VISIBILITY , bottomNavigationView.getVisibility());
    }
}