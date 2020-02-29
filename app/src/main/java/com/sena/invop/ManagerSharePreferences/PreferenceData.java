package com.sena.invop.ManagerSharePreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.sena.invop.Login.Model.User;


public class PreferenceData {
    private final SharedPreferences mPrefs;
    private boolean mIsLoggedIn = false;
    public  final String PREFS_NAME = "DATA_USER_INVOP";


    public static final String PREF_USERNAME = "PREF_USERNAME";
    public static final String PREF_PASSWORD = "PREF_PASSWORD";
    private static PreferenceData INSTANCE;


    public static PreferenceData get(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new PreferenceData(context.getApplicationContext());
        }
        return INSTANCE;
    }
    private PreferenceData(Context context) {
        mPrefs = context.getApplicationContext()
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        mIsLoggedIn = !TextUtils.isEmpty(mPrefs.getString(PREF_PASSWORD, null));

    }

    public  void setUer(User uer,String pass){
        SharedPreferences.Editor editor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(uer);
        editor.putString("user", json);
        editor.putString(PREF_PASSWORD,pass);
        mIsLoggedIn = true;
        editor.apply();


    }

    public boolean isLoggedIn() {
        return mIsLoggedIn;
    }

    public  User getUser(){
        Gson gson = new Gson();
        String json = mPrefs.getString("user", "");
        return gson.fromJson(json, User.class);
    }
    public String getValue(String key){
        return  mPrefs.getString(key,null);
    }
    public void setValue(String value, String key){
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(key,value);
        editor.apply();
    }
    public void logout(){
        mIsLoggedIn = false;
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.clear();
        editor.apply();
    }

}
