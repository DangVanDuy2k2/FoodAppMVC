package com.duydv.vn.foodappmvc.activity;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.duydv.vn.foodappmvc.R;
import com.duydv.vn.foodappmvc.adapter.ViewPagerAdapter;
import com.duydv.vn.foodappmvc.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding mActivityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mActivityMainBinding.getRoot());

        //Khong cho vuot man hinh
        mActivityMainBinding.viewPager.setUserInputEnabled(false);

        ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(this);
        mActivityMainBinding.viewPager.setAdapter(mViewPagerAdapter);

        mActivityMainBinding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        mActivityMainBinding.bottomNav.getMenu().findItem(R.id.nav_home).setChecked(true);
                        break;
                    case 1:
                        mActivityMainBinding.bottomNav.getMenu().findItem(R.id.nav_cart).setChecked(true);
                        break;
                    case 2:
                        mActivityMainBinding.bottomNav.getMenu().findItem(R.id.nav_feedback).setChecked(true);
                        break;
                    case 3:
                        mActivityMainBinding.bottomNav.getMenu().findItem(R.id.nav_contact).setChecked(true);
                        break;
                    case 4:
                        mActivityMainBinding.bottomNav.getMenu().findItem(R.id.nav_account).setChecked(true);
                        break;
                }
            }
        });

        mActivityMainBinding.bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.nav_home){
                    mActivityMainBinding.viewPager.setCurrentItem(0);
                }else if(id == R.id.nav_cart){
                    mActivityMainBinding.viewPager.setCurrentItem(1);
                }else if(id == R.id.nav_feedback){
                    mActivityMainBinding.viewPager.setCurrentItem(2);
                }else if(id == R.id.nav_contact){
                    mActivityMainBinding.viewPager.setCurrentItem(3);
                }else if(id == R.id.nav_account){
                    mActivityMainBinding.viewPager.setCurrentItem(4);
                }
                return true;
            }
        });
    }

    public void showToolbar(boolean isHome, String title){
        if(isHome){
            mActivityMainBinding.toolbar.layoutToolbar.setVisibility(View.GONE);
        }else{
            mActivityMainBinding.toolbar.layoutToolbar.setVisibility(View.VISIBLE);
            mActivityMainBinding.toolbar.txtTitle.setText(title);
        }
    }
}