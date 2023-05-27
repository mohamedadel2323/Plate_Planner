package com.example.plateplanner.network;

public interface RemoteSource {
    public void getMealFromNetwork(NetworkDelegate networkDelegate);
    public void getCategories(NetworkDelegate networkDelegate);
    public void getAreas(NetworkDelegate networkDelegate);
    public void searchByLetter(String searchQuery,SearchNetworkDelegate networkDelegate);
    public void searchByName(String searchQuery,SearchNetworkDelegate networkDelegate);

    void filterByCategory(String filterQuery, SearchNetworkDelegate networkDelegate);
    void filterByCountry(String filterQuery, SearchNetworkDelegate networkDelegate);
    void filterByIngredient(String filterQuery, SearchNetworkDelegate networkDelegate);
}
