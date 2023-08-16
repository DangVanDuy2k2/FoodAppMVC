package com.duydv.vn.foodappmvc.fragment.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duydv.vn.foodappmvc.R;
import com.duydv.vn.foodappmvc.activity.AdminActivity;
import com.duydv.vn.foodappmvc.fragment.BaseFragment;
public class AdminFeedbackFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_feedback, container, false);
    }

    @Override
    protected void initToolbar() {
        if(getActivity() != null){
            ((AdminActivity) getActivity()).showToolbar(getString(R.string.nav_feedback));
        }
    }
}