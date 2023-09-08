package com.duydv.vn.foodappmvc.constant;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.duydv.vn.foodappmvc.activity.AdminActivity;
import com.duydv.vn.foodappmvc.activity.MainActivity;
import com.duydv.vn.foodappmvc.listener.IDateListener;
import com.duydv.vn.foodappmvc.pref.DataStoreManager;
import com.duydv.vn.foodappmvc.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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

    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.
                    getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    public static void showToastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showDialogPicker(Context context, String currentTime, IDateListener mIDateListener){
        Calendar mCalendar = Calendar.getInstance();
        int currentDay = mCalendar.get(Calendar.DATE);
        int currentMonth = mCalendar.get(Calendar.MONTH);
        int currentYear = mCalendar.get(Calendar.YEAR);
        mCalendar.set(currentYear,currentMonth,currentDay);

        if (!StringUtils.isEmty(currentTime)) {
            String[] split = currentTime.split("/");
            currentDay = Integer.parseInt(split[0]);
            currentMonth = Integer.parseInt(split[1]) - 1;
            currentYear = Integer.parseInt(split[2]);
            mCalendar.set(currentYear,currentMonth,currentDay);
        }
        DatePickerDialog.OnDateSetListener callback = (datePicker, year, month, day) -> {
            String date = StringUtils.getDoubleNumber(day) + "/"
                    + StringUtils.getDoubleNumber(month + 1) + "/"
                    + year;
            mIDateListener.getDate(date);
        };

        DatePickerDialog mDatePickerDialog = new DatePickerDialog(context,callback, currentYear, currentMonth, currentDay);
        mDatePickerDialog.show();
    }
}
