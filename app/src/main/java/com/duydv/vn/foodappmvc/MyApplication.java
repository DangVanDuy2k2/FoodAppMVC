package com.duydv.vn.foodappmvc;

import android.app.Application;
import android.content.Context;

import com.duydv.vn.foodappmvc.pref.DataStoreManager;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyApplication extends Application {
    private FirebaseDatabase database;

    //Giong nhu phuong thuc khoi tao de lay ra mot doi tuong MyApplication
    public static MyApplication get(Context context){
        return (MyApplication) context.getApplicationContext();
    }
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        DataStoreManager.init(getApplicationContext());
    }

    public DatabaseReference getFoodReference(){
        return database.getReference("food");
    }
    public DatabaseReference getBookingReference(){
        return database.getReference("booking");
    }
    public DatabaseReference getFeedbackReference(){
        return database.getReference("feedback");
    }
}
