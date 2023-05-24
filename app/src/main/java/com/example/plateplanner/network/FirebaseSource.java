package com.example.plateplanner.network;

import com.example.plateplanner.startactivity.model.AuthModel;

public interface FirebaseSource {
    public void signIn(AuthModel authModel, FirebaseDelegate firebaseDelegate);
    public void signup(AuthModel authModel, FirebaseDelegate FirebaseDelegate);
}
