package com.example.plateplanner.startactivity.model;

import com.example.plateplanner.network.FirebaseDelegate;
import com.example.plateplanner.network.NetworkDelegate;

public interface RepositoryInterface {
    public void setLoginStatus(Boolean isLogin);
    public boolean getLoginStatus();
    public void signIn(AuthModel authModel ,FirebaseDelegate firebaseDelegate);
    public void SignUp(AuthModel authModel ,FirebaseDelegate firebaseDelegate);
    public void getDailyInspiration(NetworkDelegate networkDelegate);
    public void getCategories(NetworkDelegate networkDelegate);
    public void getAreas(NetworkDelegate networkDelegate);
}
