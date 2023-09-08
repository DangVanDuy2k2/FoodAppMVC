package com.duydv.vn.foodappmvc.fragment.admin;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duydv.vn.foodappmvc.MyApplication;
import com.duydv.vn.foodappmvc.R;
import com.duydv.vn.foodappmvc.activity.AdminActivity;
import com.duydv.vn.foodappmvc.adapter.AdminOrderAdapter;
import com.duydv.vn.foodappmvc.databinding.FragmentAdminOrderBinding;
import com.duydv.vn.foodappmvc.fragment.BaseFragment;
import com.duydv.vn.foodappmvc.model.Order;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminOrderFragment extends BaseFragment {
    private FragmentAdminOrderBinding mFragmentAdminOrderBinding;
    private List<Order> mListOrder;
    private AdminOrderAdapter mAdminOrderAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentAdminOrderBinding = FragmentAdminOrderBinding.inflate(inflater,container,false);

        initView();
        getListFromFirebase();

        return mFragmentAdminOrderBinding.getRoot();
    }

    @Override
    protected void initToolbar() {
        if(getActivity() != null){
            ((AdminActivity) getActivity()).showToolbar(getString(R.string.nav_order));
        }
    }

    private void initView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mFragmentAdminOrderBinding.rcvOrder.setLayoutManager(linearLayoutManager);

        mListOrder = new ArrayList<>();
        mAdminOrderAdapter = new AdminOrderAdapter(mListOrder, new AdminOrderAdapter.ISetStatusOrder() {
            @Override
            public void onClickCheckBox(Order order) {
                setStatusOrder(order);
            }
        });
        mFragmentAdminOrderBinding.rcvOrder.setAdapter(mAdminOrderAdapter);
    }
    private void getListFromFirebase(){
        if(getActivity() == null){
            return;
        }
        MyApplication.get(getActivity()).getBookingReference().addChildEventListener(
                new ChildEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Order order = snapshot.getValue(Order.class);
                        if(order == null || mListOrder == null || mAdminOrderAdapter == null){
                            return;
                        }
                        mListOrder.add(0,order);
                        mAdminOrderAdapter.notifyDataSetChanged();
                    }
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Order order = snapshot.getValue(Order.class);

                        if(order == null || mListOrder == null || mAdminOrderAdapter == null || mListOrder.isEmpty()){
                            return;
                        }

                        for(int i=0;i<mListOrder.size();i++){
                            if(order.getId() == mListOrder.get(i).getId()){
                                mListOrder.set(i,order);
                            }
                        }
                        mAdminOrderAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );
    }

    private void setStatusOrder(Order order){
        if(getActivity() == null){
            return;
        }
        MyApplication.get(getActivity()).getBookingReference().child(String.valueOf(order.getId())).child("completed").setValue(!order.isCompleted());
    }
}