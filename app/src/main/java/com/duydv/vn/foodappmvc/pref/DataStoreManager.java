package com.duydv.vn.foodappmvc.pref;

import android.content.Context;

import com.duydv.vn.foodappmvc.model.User;
import com.google.gson.Gson;

public class DataStoreManager {
    private static final String PREF_OBJECT_USER = "PREF_OBJECT_USER";
    private static DataStoreManager instance;
    private MySharedPreferences mySharedPreferences;

    public static void init(Context context){
        instance = new DataStoreManager();
        instance.mySharedPreferences = new MySharedPreferences(context);
    }

    public static DataStoreManager getInstance(){
        if(instance == null){
            instance = new DataStoreManager();
        }
        return instance;
    }

    public static void setUser(User user){
        Gson gson = new Gson();
        String strJsonUser = gson.toJson(user);
        DataStoreManager.getInstance().mySharedPreferences.putStringValue(PREF_OBJECT_USER, strJsonUser);
    }

    public static User getUser(){
        String strJsonUser = DataStoreManager.getInstance().mySharedPreferences.getStringValue(PREF_OBJECT_USER);
        Gson gson = new Gson();
        User user = gson.fromJson(strJsonUser,User.class);
        return user;
    }
}
