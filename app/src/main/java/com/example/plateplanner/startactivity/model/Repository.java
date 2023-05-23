package com.example.plateplanner.startactivity.model;

import com.example.plateplanner.startactivity.network.FirebaseDelegate;
import com.example.plateplanner.startactivity.network.FirebaseSource;

public class Repository implements RepositoryInterface {
    private static Repository repository = null;
    SharedPreferencesInterface sharedSource;
    FirebaseSource firebaseSource;

    private Repository(SharedPreferencesInterface sharedSource , FirebaseSource firebaseSource){
        this.sharedSource = sharedSource;
        this.firebaseSource = firebaseSource;
    }

    public static synchronized Repository getInstance(SharedPreferencesInterface sharedSource , FirebaseSource firebaseSource) {
        if (repository == null){
            repository = new Repository(sharedSource , firebaseSource);
        }
        return repository;
    }

    @Override
    public void setLoginStatus(Boolean isLogin) {
        sharedSource.setLoginStatus(isLogin);
    }

    @Override
    public boolean getLoginStatus() {
        return sharedSource.getLoginStatus();
    }

    @Override
    public void signIn(AuthModel authModel ,FirebaseDelegate firebaseDelegate) {
        firebaseSource.signIn(authModel , firebaseDelegate);
    }

    @Override
    public void SignUp(AuthModel authModel ,FirebaseDelegate firebaseDelegate) {
        firebaseSource.signup(authModel , firebaseDelegate);
    }
}
