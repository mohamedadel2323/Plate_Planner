package com.example.plateplanner.startactivity.presenter;

import androidx.annotation.NonNull;

import com.example.plateplanner.startactivity.model.AuthModel;
import com.example.plateplanner.startactivity.model.RepositoryInterface;
import com.example.plateplanner.startactivity.network.FirebaseDelegate;
import com.example.plateplanner.startactivity.view.SignUpViewInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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