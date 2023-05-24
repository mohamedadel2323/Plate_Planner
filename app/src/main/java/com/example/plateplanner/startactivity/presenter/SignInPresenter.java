package com.example.plateplanner.startactivity.presenter;

import com.example.plateplanner.startactivity.model.AuthModel;
import com.example.plateplanner.startactivity.model.RepositoryInterface;
import com.example.plateplanner.network.FirebaseDelegate;
import com.example.plateplanner.startactivity.view.SignInViewInterface;

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


}
