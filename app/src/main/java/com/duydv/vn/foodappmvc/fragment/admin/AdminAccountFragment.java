package com.duydv.vn.foodappmvc.fragment.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.duydv.vn.foodappmvc.R;
import com.duydv.vn.foodappmvc.activity.AdminActivity;
import com.duydv.vn.foodappmvc.activity.ChangePasswordActivity;
import com.duydv.vn.foodappmvc.activity.SignInActivity;
import com.duydv.vn.foodappmvc.constant.GlobalFunction;
import com.duydv.vn.foodappmvc.databinding.FragmentAdminAccountBinding;
import com.duydv.vn.foodappmvc.fragment.BaseFragment;
import com.duydv.vn.foodappmvc.pref.DataStoreManager;
import com.google.firebase.auth.FirebaseAuth;

public class AdminAccountFragment extends BaseFragment {
    private FragmentAdminAccountBinding mFragmentAdminAccountBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentAdminAccountBinding = FragmentAdminAccountBinding
                .inflate(inflater, container, false);

        showInforUser();

        mFragmentAdminAccountBinding.layoutLogout.setOnClickListener(view -> onClickLogout());
        mFragmentAdminAccountBinding.layoutChangePassword.setOnClickListener(view ->
                GlobalFunction.startActivity(getActivity(), ChangePasswordActivity.class));


        return mFragmentAdminAccountBinding.getRoot();
    }

    @Override
    protected void initToolbar() {
        if(getActivity() != null){
            ((AdminActivity) getActivity()).showToolbar(getString(R.string.nav_account));
        }
    }

    private void showInforUser(){
        if(getActivity() == null){
            return;
        }
        mFragmentAdminAccountBinding.txtEmail.setText(DataStoreManager.getUser().getEmail());
        Glide.with(getActivity()).load(DataStoreManager.getUser().getImage()).error(R.drawable.ic_avatar_default)
                .into(mFragmentAdminAccountBinding.imgAvatar);
    }

    private void onClickLogout(){
        if(getActivity() == null){
            return;
        }
        FirebaseAuth.getInstance().signOut();
        DataStoreManager.setUser(null);
        GlobalFunction.startActivity(getActivity(), SignInActivity.class);
        getActivity().finishAffinity();
    }
}