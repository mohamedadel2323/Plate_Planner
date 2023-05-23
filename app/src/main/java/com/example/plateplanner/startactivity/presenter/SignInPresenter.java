package com.example.plateplanner.startactivity.presenter;

import androidx.annotation.NonNull;

import com.example.plateplanner.startactivity.model.AuthModel;
import com.example.plateplanner.startactivity.model.RepositoryInterface;
import com.example.plateplanner.startactivity.network.FirebaseDelegate;
import com.example.plateplanner.startactivity.view.SignInViewInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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
    }

    @Override
    public void onFail(String errorMessage) {
        view.onLoginFailed(errorMessage);
    }


}
