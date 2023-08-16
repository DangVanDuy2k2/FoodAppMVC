package com.duydv.vn.foodappmvc.activity;

import android.os.Bundle;
import android.os.Handler;

import com.duydv.vn.foodappmvc.R;
import com.duydv.vn.foodappmvc.constant.GlobalFunction;
import com.duydv.vn.foodappmvc.pref.DataStoreManager;
import com.duydv.vn.foodappmvc.utils.StringUtils;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash);

        Handler handler = new Handler();
        handler.postDelayed(this::nextActivity,2500);
    }

    private void nextActivity() {
        if(DataStoreManager.getUser() != null && !StringUtils.isEmty(DataStoreManager.getUser().getEmail())){
            //Da dang nhap
            GlobalFunction.startMainActivity(this);
        }else {
            //Chua dang nhap
            GlobalFunction.startActivity(this,SignInActivity.class);
        }
        finish();
    }
}