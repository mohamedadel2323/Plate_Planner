package com.example.plateplanner.homeactivity.profile.view;

import androidx.lifecycle.LiveData;

import com.example.plateplanner.model.MealPojo;
import com.example.plateplanner.network.FirebaseDelegate;

import java.util.List;

public interface ProfileFragmentViewInterface {
    public void logout();

    void stopAnimation();

    public void showErrorMessage(String errorMessage);
}
