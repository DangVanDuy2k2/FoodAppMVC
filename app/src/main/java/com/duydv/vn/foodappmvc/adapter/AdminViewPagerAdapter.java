package com.duydv.vn.foodappmvc.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.duydv.vn.foodappmvc.fragment.admin.AdminAccountFragment;
import com.duydv.vn.foodappmvc.fragment.admin.AdminFeedbackFragment;
import com.duydv.vn.foodappmvc.fragment.admin.AdminFoodFragment;
import com.duydv.vn.foodappmvc.fragment.admin.AdminOrderFragment;

public class AdminViewPagerAdapter extends FragmentStateAdapter {
    public AdminViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new AdminFoodFragment();
            case 1:
                return new AdminFeedbackFragment();
            case 2:
                return new AdminOrderFragment();
            case 3:
                return new AdminAccountFragment();
            default:
                return new AdminFoodFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
