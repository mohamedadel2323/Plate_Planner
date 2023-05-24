package com.example.plateplanner.startactivity.model;

import com.example.plateplanner.network.FirebaseDelegate;
import com.example.plateplanner.network.FirebaseSource;
import com.example.plateplanner.network.NetworkDelegate;
import com.example.plateplanner.network.RemoteSource;

public class Repository implements RepositoryInterface {
    private static Repository repository = null;
    SharedPreferencesInterface sharedSource;
    FirebaseSource firebaseSource;
    RemoteSource remoteSource;

    private Repository(SharedPreferencesInterface sharedSource , FirebaseSource firebaseSource , RemoteSource remoteSource){
        this.sharedSource = sharedSource;
        this.firebaseSource = firebaseSource;
        this.remoteSource = remoteSource;
    }

    public static synchronized Repository getInstance(SharedPreferencesInterface sharedSource , FirebaseSource firebaseSource , RemoteSource remoteSource) {
        if (repository == null){
            repository = new Repository(sharedSource , firebaseSource , remoteSource);
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

    @Override
    public void getDailyInspiration(NetworkDelegate networkDelegate) {
        remoteSource.getMealFromNetwork(networkDelegate);
    }

    @Override
    public void getCategories(NetworkDelegate networkDelegate) {
        remoteSource.getCategories(networkDelegate);
    }

    @Override
    public void getAreas(NetworkDelegate networkDelegate) {
        remoteSource.getAreas(networkDelegate);
    }

}
