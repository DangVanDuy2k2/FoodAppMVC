package com.duydv.vn.foodappmvc.activity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.duydv.vn.foodappmvc.MyApplication;
import com.duydv.vn.foodappmvc.R;
import com.duydv.vn.foodappmvc.adapter.OrderHistoryAdapter;
import com.duydv.vn.foodappmvc.databinding.ActivityOrderHistoryBinding;
import com.duydv.vn.foodappmvc.model.Order;
import com.duydv.vn.foodappmvc.pref.DataStoreManager;
import com.duydv.vn.foodappmvc.utils.StringUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryActivity extends BaseActivity {
    private ActivityOrderHistoryBinding mActivityOrderHistoryBinding;
    private List<Order> mListOrderHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityOrderHistoryBinding = ActivityOrderHistoryBinding.inflate(getLayoutInflater());
        setContentView(mActivityOrderHistoryBinding.getRoot());

        showToolbar();
        getListOrderFromFirebase();

        mActivityOrderHistoryBinding.toolbar.imgBack.setOnClickListener(view -> onBackPressed());
    }

    private void showToolbar(){
        mActivityOrderHistoryBinding.toolbar.layoutToolbar.setVisibility(View.VISIBLE);
        mActivityOrderHistoryBinding.toolbar.imgBack.setVisibility(View.VISIBLE);
        mActivityOrderHistoryBinding.toolbar.imgCart.setVisibility(View.GONE);
        mActivityOrderHistoryBinding.toolbar.txtTitle.setText(getString(R.string.order_history2));
    }

    private void getListOrderFromFirebase() {
        MyApplication.get(getApplication()).getBookingReference()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(mListOrderHistory != null){
                            mListOrderHistory.clear();
                        }else{
                            mListOrderHistory = new ArrayList<>();
                        }
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            Order order = dataSnapshot.getValue(Order.class);
                            if(order != null){
                                String email = DataStoreManager.getUser().getEmail();
                                if(!StringUtils.isEmty(order.getEmail()) && order.getEmail().equals(email)){
                                    mListOrderHistory.add(0,order);
                                }
                            }
                        }
                        displayOrderHistory(mListOrderHistory);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(OrderHistoryActivity.this, getString(R.string.msg_onCancelled), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void displayOrderHistory(List<Order> mListOrderHistory){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mActivityOrderHistoryBinding.rcvOrderHistory.setLayoutManager(linearLayoutManager);

        OrderHistoryAdapter orderHistoryAdapter = new OrderHistoryAdapter(mListOrderHistory);
        mActivityOrderHistoryBinding.rcvOrderHistory.setAdapter(orderHistoryAdapter);
    }
}