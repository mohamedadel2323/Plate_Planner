package com.example.plateplanner.startactivity.model;

import com.example.plateplanner.network.FirebaseDelegate;
import com.example.plateplanner.network.NetworkDelegate;
import com.example.plateplanner.network.SearchNetworkDelegate;

public interface RepositoryInterface {
    public void setLoginStatus(Boolean isLogin);
    public boolean getLoginStatus();
    public void signIn(AuthModel authModel ,FirebaseDelegate firebaseDelegate);
    public void SignUp(AuthModel authModel ,FirebaseDelegate firebaseDelegate);
    public void getDailyInspiration(NetworkDelegate networkDelegate);
    public void getCategories(NetworkDelegate networkDelegate);
    public void getAreas(NetworkDelegate networkDelegate);
    public void searchByLetter(String searchQuery , SearchNetworkDelegate networkDelegate);
    public void searchByName(String searchQuery , SearchNetworkDelegate networkDelegate);

    void filterByCountry(String filterQuery, SearchNetworkDelegate networkDelegate);

    void filterByCategory(String filterQuery, SearchNetworkDelegate networkDelegate);

    void filterByIngredient(String filterQuery, SearchNetworkDelegate networkDelegate);
}
