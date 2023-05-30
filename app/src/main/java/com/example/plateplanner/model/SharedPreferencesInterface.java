package com.example.plateplanner.model;

import android.content.Context;

public interface SharedPreferencesInterface {
    public void setLoginStatus(Boolean isLogin);
    public boolean getLoginStatus();
}
