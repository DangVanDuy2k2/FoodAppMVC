package com.duydv.vn.foodappmvc.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.duydv.vn.foodappmvc.R;
import com.duydv.vn.foodappmvc.activity.MainActivity;
import com.duydv.vn.foodappmvc.databinding.FragmentCartBinding;

public class CartFragment extends BaseFragment {
    private FragmentCartBinding mFragmentCartBinding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentCartBinding = FragmentCartBinding.inflate(inflater,container,false);
        return mFragmentCartBinding.getRoot();
    }

    @Override
    protected void initToolbar() {
        if(getActivity() != null){
            ((MainActivity) getActivity()).showToolbar(false,getString(R.string.nav_cart));
        }
    }
}
