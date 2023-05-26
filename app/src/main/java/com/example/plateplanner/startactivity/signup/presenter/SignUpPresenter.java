package com.example.plateplanner.startactivity.signup.presenter;

import com.example.plateplanner.startactivity.model.AuthModel;
import com.example.plateplanner.startactivity.model.RepositoryInterface;
import com.example.plateplanner.network.FirebaseDelegate;
import com.example.plateplanner.startactivity.signup.view.SignUpViewInterface;

public class SignUpPresenter implements FirebaseDelegate {

    SignUpViewInterface view;
    RepositoryInterface repositoryInterface;

    public SignUpPresenter(SignUpViewInterface view ,RepositoryInterface repositoryInterface) {
        this.view = view;
        this.repositoryInterface = repositoryInterface;
    }

    public void signUp(AuthModel authModel) {
        repositoryInterface.SignUp(authModel , this);
    }

    @Override
    public void onSuccess() {
        view.onSignupSuccess();
        repositoryInterface.setLoginStatus(true);
    }

    @Override
    public void onFail(String errorMessage) {
        view.onSignupFailed(errorMessage);
    }
}
