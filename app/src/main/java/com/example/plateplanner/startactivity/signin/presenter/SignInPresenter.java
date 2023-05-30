package com.example.plateplanner.startactivity.signin.presenter;

import com.example.plateplanner.model.AuthModel;
import com.example.plateplanner.model.MealPojo;
import com.example.plateplanner.model.PlanMeal;
import com.example.plateplanner.model.RepositoryInterface;
import com.example.plateplanner.network.FirebaseDelegate;
import com.example.plateplanner.startactivity.signin.view.SignInViewInterface;

import java.util.List;

public class SignInPresenter implements FirebaseDelegate {
    SignInViewInterface view;
    RepositoryInterface repositoryInterface;

    public SignInPresenter(SignInViewInterface view , RepositoryInterface repositoryInterface){
        this.view = view;
        this.repositoryInterface = repositoryInterface;
    }



    public void signIn(AuthModel authModel){
        repositoryInterface.signIn(authModel , this);
    }

    @Override
    public void onSuccess() {
        view.onLoginSuccess();
        repositoryInterface.setLoginStatus(true);
    }

    @Override
    public void onFail(String errorMessage) {
        view.onLoginFailed(errorMessage);
    }


    @Override
    public void onDownloadMealsSuccess(List<MealPojo> favMeals, List<PlanMeal> planMeals) {

    }


}
