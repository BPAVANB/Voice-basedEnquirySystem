package com.rccreation.ravindra.sairamproject;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;


public class SessionHandler {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_EXPIRES = "expires";
    private static final String KEY_EMPTY = "";
    private Context mContext;
    private String Role;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;

    public SessionHandler(Context mContext) {
        this.mContext = mContext;
        mPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.mEditor = mPreferences.edit();
    }


    public void loginUser(String id,String password,String role) {
        mEditor.putString("id", id);
mEditor.putString("password",password);
mEditor.putString("role",role);



        Date date = new Date();
        long millis = date.getTime() + (7 * 24 * 60 * 60 * 1000);
        mEditor.putLong(KEY_EXPIRES, millis);
        mEditor.commit();
    }


    public boolean isLoggedIn() {
        Date currentDate = new Date();

        long millis = mPreferences.getLong(KEY_EXPIRES, 0);


        if (millis == 0) {
            return false;
        }
        Date expiryDate = new Date(millis);


        return currentDate.before(expiryDate);
    }




    public User getUserDetails() {
        if (!isLoggedIn()) {
            return null;
        }
        User user = new User();

        user.setRole(mPreferences.getString("password",""));

     user.setUserid(mPreferences.getString("id",""));
     user.setEmail(mPreferences.getString("email",""));
     user.setSname(mPreferences.getString("name",""));
     user.setRole(mPreferences.getString("role",""));

        user.setSessionExpiryDate(new Date(mPreferences.getLong(KEY_EXPIRES, 0)));

        return user;
    }


    public void logoutUser(){
        mEditor.clear();
        mEditor.commit();

    }

}