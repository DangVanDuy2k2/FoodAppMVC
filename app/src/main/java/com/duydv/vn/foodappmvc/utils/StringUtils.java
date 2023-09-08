package com.duydv.vn.foodappmvc.utils;

import android.util.Patterns;

public class StringUtils {
    public static boolean isEmty(String value){



        return value == null || value.isEmpty() || ("").equals(value.trim());
    }

    public static boolean isValidEmail(String email){
        if(email == null){
            return true;
        }
        return !Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static String getDoubleNumber(int number){
        if(number < 10){
            return "0" + number;
        }
        return number + "";
    }
}
