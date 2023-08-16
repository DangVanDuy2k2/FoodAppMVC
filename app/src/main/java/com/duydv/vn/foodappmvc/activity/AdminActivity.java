package com.duydv.vn.foodappmvc.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

import com.duydv.vn.foodappmvc.R;
import com.duydv.vn.foodappmvc.adapter.AdminViewPagerAdapter;
import com.duydv.vn.foodappmvc.databinding.ActivityAdminBinding;
import com.google.android.material.navigation.NavigationBarView;

public class AdminActivity extends BaseActivity {
    private ActivityAdminBinding mActivityAdminBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityAdminBinding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(mActivityAdminBinding.getRoot());

        //Khong cho vuot man hinh
        mActivityAdminBinding.viewPager.setUserInputEnabled(true);

        AdminViewPagerAdapter mAdminViewPagerAdapter = new AdminViewPagerAdapter(this);
        mActivityAdminBinding.viewPager.setAdapter(mAdminViewPagerAdapter);

        mActivityAdminBinding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        mActivityAdminBinding.bottomNav.getMenu().findItem(R.id.nav_food).setChecked(true);
                        break;
                    case 1:
                        mActivityAdminBinding.bottomNav.getMenu().findItem(R.id.nav_feedback).setChecked(true);
                        break;
                    case 3:
                        mActivityAdminBinding.bottomNav.getMenu().findItem(R.id.nav_order).setChecked(true);
                        break;
                    case 4:
                        mActivityAdminBinding.bottomNav.getMenu().findItem(R.id.nav_account).setChecked(true);
                        break;
                }
            }
        });

        mActivityAdminBinding.bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.nav_food){
                    mActivityAdminBinding.viewPager.setCurrentItem(0);
                }else if(id == R.id.nav_feedback){
                    mActivityAdminBinding.viewPager.setCurrentItem(1);
                }else if(id == R.id.nav_order){
                    mActivityAdminBinding.viewPager.setCurrentItem(2);
                }else if(id == R.id.nav_account){
                    mActivityAdminBinding.viewPager.setCurrentItem(3);
                }
                return true;
            }
        });
    }

    public void showToolbar(String title){
        mActivityAdminBinding.toolbar.txtTitle.setText(title);
    }
}