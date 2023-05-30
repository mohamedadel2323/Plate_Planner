package com.example.plateplanner.model;

import android.content.Context;
import android.content.SharedPreferences;

public class AuthSharedPreferences implements SharedPreferencesInterface {

    private static AuthSharedPreferences authSharedPreferences = null;

    private final String PREFERENCES_NAME = "authState";
    private final String IS_LOGGED_IN = "is_logged-in";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private AuthSharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static synchronized AuthSharedPreferences getInstance(Context context){
        if (authSharedPreferences == null){
            authSharedPreferences = new AuthSharedPreferences(context);
        }
        return authSharedPreferences;
    }

    @Override
    public void setLoginStatus(Boolean isLogin) {
        editor.putBoolean(IS_LOGGED_IN, isLogin);
        editor.commit();
    }

    @Override
    public boolean getLoginStatus() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }


}
