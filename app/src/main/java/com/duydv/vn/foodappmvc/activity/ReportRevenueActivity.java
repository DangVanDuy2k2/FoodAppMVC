package com.duydv.vn.foodappmvc.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.duydv.vn.foodappmvc.MyApplication;
import com.duydv.vn.foodappmvc.R;
import com.duydv.vn.foodappmvc.adapter.RevenueAdapter;
import com.duydv.vn.foodappmvc.constant.Constant;
import com.duydv.vn.foodappmvc.constant.GlobalFunction;
import com.duydv.vn.foodappmvc.databinding.ActivityReportBinding;
import com.duydv.vn.foodappmvc.model.Order;
import com.duydv.vn.foodappmvc.utils.DateTimeUtils;
import com.duydv.vn.foodappmvc.utils.StringUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReportRevenueActivity extends BaseActivity {
    private ActivityReportBinding mActivityReportBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityReportBinding = ActivityReportBinding.inflate(LayoutInflater.from(this));
        setContentView(mActivityReportBinding.getRoot());

        showToolbar();
        getListRevenue();
        initListener();
    }

    private void showToolbar(){
        mActivityReportBinding.toolbar.layoutToolbar.setVisibility(View.VISIBLE);
        mActivityReportBinding.toolbar.imgBack.setVisibility(View.VISIBLE);
        mActivityReportBinding.toolbar.imgCart.setVisibility(View.GONE);
        mActivityReportBinding.toolbar.txtTitle.setText(R.string.report);
    }

    private void initListener(){
        mActivityReportBinding.toolbar.imgBack.setOnClickListener(view -> onBackPressed());
        mActivityReportBinding.layoutDateStart.setOnClickListener(view ->
                GlobalFunction.showDialogPicker(ReportRevenueActivity.this,
                mActivityReportBinding.txtDateStart.getText().toString().trim(),
                date -> {
                    mActivityReportBinding.txtDateStart.setText(date);
                    getListRevenue();
                }));

        mActivityReportBinding.layoutDateEnd.setOnClickListener(view ->
                GlobalFunction.showDialogPicker(ReportRevenueActivity.this,
                mActivityReportBinding.txtDateEnd.getText().toString().trim(),
                date -> {
                    mActivityReportBinding.txtDateEnd.setText(date);
                    getListRevenue();
                }));
    }

    private void getListRevenue(){
        MyApplication.get(this).getBookingReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Order> mListOrder = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Order order = dataSnapshot.getValue(Order.class);
                    if(order != null){
                        if(canAddToListRevenue(order)){
                            mListOrder.add(0,order);
                        }
                    }
                }
                displayListRevenue(mListOrder);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                GlobalFunction.showToastMessage(ReportRevenueActivity.this,getString(R.string.msg_onCancelled));
            }
        });
    }

    private boolean canAddToListRevenue(Order order){
        if(order == null){
            return false;
        }

        if(!order.isCompleted()){
            return false;
        }

        String strDateStart = mActivityReportBinding.txtDateStart.getText().toString().trim();
        String strDateEnd = mActivityReportBinding.txtDateEnd.getText().toString().trim();

        if(StringUtils.isEmty(strDateStart) && StringUtils.isEmty(strDateEnd)){
            return true;
        }

        String strDateOrder = DateTimeUtils.convertTimeToDate_2(order.getId());
        long timeDateOrder = Long.parseLong(DateTimeUtils.convertDate2ToTime(strDateOrder));

        if(StringUtils.isEmty(strDateStart) && !StringUtils.isEmty(strDateEnd)){
            long timeDateEnd = Long.parseLong(DateTimeUtils.convertDate2ToTime(strDateEnd));
            return timeDateOrder <= timeDateEnd;
        }

        if(!StringUtils.isEmty(strDateStart) && StringUtils.isEmty(strDateEnd)){
            long timeDateStart = Long.parseLong(DateTimeUtils.convertDate2ToTime(strDateStart));
            return timeDateOrder >= timeDateStart;
        }

        long timeDateStart = Long.parseLong(DateTimeUtils.convertDate2ToTime(strDateStart));
        long timeDateEnd = Long.parseLong(DateTimeUtils.convertDate2ToTime(strDateEnd));
        return timeDateOrder >= timeDateStart && timeDateOrder <= timeDateEnd;
    }

    private void displayListRevenue(List<Order> mListOrder){
        if(mListOrder == null){
            return;
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mActivityReportBinding.rcvRevenue.setLayoutManager(linearLayoutManager);

        RevenueAdapter revenueAdapter = new RevenueAdapter(mListOrder);
        mActivityReportBinding.rcvRevenue.setAdapter(revenueAdapter);

        String strSumRevenue = getSumRevenue(mListOrder) + Constant.VND;
        mActivityReportBinding.txtSumRevenue.setText(strSumRevenue);
    }

    private long getSumRevenue(List<Order> mListOrder){
        if(mListOrder == null){
            return 0;
        }
        int sumRevenue = 0;
        for(Order order : mListOrder){
            sumRevenue += order.getSumPrice();
        }
        return sumRevenue;
    }
}