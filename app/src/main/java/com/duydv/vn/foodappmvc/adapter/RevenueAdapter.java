package com.duydv.vn.foodappmvc.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duydv.vn.foodappmvc.constant.Constant;
import com.duydv.vn.foodappmvc.databinding.ItemRevenueBinding;
import com.duydv.vn.foodappmvc.model.Order;
import com.duydv.vn.foodappmvc.utils.DateTimeUtils;

import java.util.List;

public class RevenueAdapter extends RecyclerView.Adapter<RevenueAdapter.RevenueViewHolder> {
    private final List<Order> mListOrder;

    public RevenueAdapter(List<Order> mListOrder) {
        this.mListOrder = mListOrder;
    }

    @NonNull
    @Override
    public RevenueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRevenueBinding itemRevenueBinding = ItemRevenueBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new RevenueViewHolder(itemRevenueBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RevenueViewHolder holder, int position) {
        Order order = mListOrder.get(position);
        if(order == null){
            return;
        }

        holder.mItemRevenueBinding.txtId.setText(String.valueOf(order.getId()));

        String strDate = DateTimeUtils.convertTimeToDate_2(order.getId());
        holder.mItemRevenueBinding.txtDate.setText(strDate);

        String strSumPrice = order.getSumPrice() + Constant.VND;
        holder.mItemRevenueBinding.txtSumPrice.setText(strSumPrice);
    }

    @Override
    public int getItemCount() {
        if(mListOrder != null){
            return mListOrder.size();
        }
        return 0;
    }

    public static class RevenueViewHolder extends RecyclerView.ViewHolder{
        private final ItemRevenueBinding mItemRevenueBinding;
        public RevenueViewHolder(@NonNull ItemRevenueBinding mItemRevenueBinding) {
            super(mItemRevenueBinding.getRoot());
            this.mItemRevenueBinding = mItemRevenueBinding;
        }
    }
}
