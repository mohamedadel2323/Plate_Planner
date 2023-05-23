package com.example.plateplanner.startactivity.model;

import com.example.plateplanner.startactivity.network.FirebaseDelegate;

public interface RepositoryInterface {
    public void setLoginStatus(Boolean isLogin);
    public boolean getLoginStatus();
    public void signIn(AuthModel authModel ,FirebaseDelegate firebaseDelegate);
    public void SignUp(AuthModel authModel ,FirebaseDelegate firebaseDelegate);
}
