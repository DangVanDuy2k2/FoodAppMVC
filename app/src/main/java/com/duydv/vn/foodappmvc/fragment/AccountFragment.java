package com.duydv.vn.foodappmvc.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.duydv.vn.foodappmvc.R;
import com.duydv.vn.foodappmvc.activity.ChangePasswordActivity;
import com.duydv.vn.foodappmvc.activity.MainActivity;
import com.duydv.vn.foodappmvc.activity.OrderHistoryActivity;
import com.duydv.vn.foodappmvc.activity.SignInActivity;
import com.duydv.vn.foodappmvc.constant.GlobalFunction;
import com.duydv.vn.foodappmvc.database.FoodDatabase;
import com.duydv.vn.foodappmvc.databinding.FragmentAccountBinding;
import com.duydv.vn.foodappmvc.pref.DataStoreManager;
import com.google.firebase.auth.FirebaseAuth;

public class AccountFragment extends BaseFragment {
    private FragmentAccountBinding mFragmentAccountBinding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentAccountBinding = FragmentAccountBinding.inflate(inflater, container, false);

        showInforUser();
        
        mFragmentAccountBinding.layoutLogout.setOnClickListener(view -> onClickLogout());
        mFragmentAccountBinding.layoutChangePassword.setOnClickListener(view ->
                GlobalFunction.startActivity(getActivity(), ChangePasswordActivity.class));

        mFragmentAccountBinding.layoutOrderHistory.setOnClickListener(view ->
                GlobalFunction.startActivity(getActivity(), OrderHistoryActivity.class));

        return mFragmentAccountBinding.getRoot();
    }


    @Override
    protected void initToolbar() {
        if(getActivity() != null){
            ((MainActivity) getActivity()).showToolbar(false,getString(R.string.nav_account));
        }
    }

    private void showInforUser(){
        if(getActivity() == null){
            return;
        }
        mFragmentAccountBinding.txtEmail.setText(DataStoreManager.getUser().getEmail());
        Glide.with(getActivity()).load(DataStoreManager.getUser().getImage()).error(R.drawable.ic_avatar_default)
                .into(mFragmentAccountBinding.imgAvatar);
    }

    private void onClickLogout() {
        if(getActivity() == null){
            return;
        }
        FirebaseAuth.getInstance().signOut();
        DataStoreManager.setUser(null);
        FoodDatabase.getInstance(getContext()).foodDAO().deleteAllFood();
        GlobalFunction.startActivity(getActivity(), SignInActivity.class);
        getActivity().finishAffinity();
    }
}
