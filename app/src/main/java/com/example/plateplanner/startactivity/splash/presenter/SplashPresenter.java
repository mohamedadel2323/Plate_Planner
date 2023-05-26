package com.example.plateplanner.startactivity.splash.presenter;

import com.example.plateplanner.startactivity.model.RepositoryInterface;
import com.example.plateplanner.startactivity.splash.view.SplashViewInterface;

public class SplashPresenter {
    SplashViewInterface view;
    RepositoryInterface repositoryInterface;

    public SplashPresenter(SplashViewInterface view , RepositoryInterface repositoryInterface){
        this.view =view;
        this.repositoryInterface = repositoryInterface;
    }

    public boolean getLogInStatus(){
        return repositoryInterface.getLoginStatus();
    }

}
