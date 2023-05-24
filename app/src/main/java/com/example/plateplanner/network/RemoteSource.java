package com.example.plateplanner.network;

public interface RemoteSource {
    public void getMealFromNetwork(NetworkDelegate networkDelegate);
    public void getCategories(NetworkDelegate networkDelegate);
    public void getAreas(NetworkDelegate networkDelegate);

}
