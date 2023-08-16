package com.duydv.vn.foodappmvc.constant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.duydv.vn.foodappmvc.activity.AdminActivity;
import com.duydv.vn.foodappmvc.activity.MainActivity;
import com.duydv.vn.foodappmvc.pref.DataStoreManager;

public class GlobalFunction {

    public static void startActivity(Context context, Class<?> mClass){
        Intent intent = new Intent(context,mClass);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, Class<?> mClass, Bundle bundle){
        Intent intent = new Intent(context,mClass);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void startMainActivity(Context context){
        if(DataStoreManager.getUser().isAdmin()){
            Intent intent = new Intent(context, AdminActivity.class);
            context.startActivity(intent);
        }else {
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        }
    }
}
