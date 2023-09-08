package com.duydv.vn.foodappmvc.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duydv.vn.foodappmvc.constant.Constant;
import com.duydv.vn.foodappmvc.databinding.ItemOrderHistoryBinding;
import com.duydv.vn.foodappmvc.model.Order;
import com.duydv.vn.foodappmvc.utils.DateTimeUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder> {
    private final List<Order> mListOrderHistory;

    public OrderHistoryAdapter(List<Order> mListOrderHistory) {
        this.mListOrderHistory = mListOrderHistory;
    }

    @NonNull
    @Override
    public OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderHistoryBinding itemOrderHistoryBinding = ItemOrderHistoryBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new OrderHistoryViewHolder(itemOrderHistoryBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryViewHolder holder, int position) {
        Order order = mListOrderHistory.get(position);
        if(order == null){
            return;
        }

        holder.mItemOrderHistoryBinding.txtIdOrderHistory.setText(String.valueOf(order.getId()));
        holder.mItemOrderHistoryBinding.txtNameOrderHistory.setText(order.getFullName());
        holder.mItemOrderHistoryBinding.txtAddressOrderHistory.setText(order.getAddress());
        holder.mItemOrderHistoryBinding.txtPhoneOrderHistory.setText(order.getPhone());
        holder.mItemOrderHistoryBinding.txtMenuOrderHistory.setText(order.getListFoodOrder());

        String strDate = DateTimeUtils.convertTimeToDate(order.getId());
        holder.mItemOrderHistoryBinding.txtDateOrderHistory.setText(strDate);

        String str_sumPrice = order.getSumPrice() + Constant.VND;
        holder.mItemOrderHistoryBinding.txtSumPriceOrderHistory.setText(str_sumPrice);

        if(order.getPayment() == Constant.CART_PAYMENT_METHOD){
            holder.mItemOrderHistoryBinding.txtPaymentMethodOrderHistory.setText(Constant.CASH);
        }
    }

    @Override
    public int getItemCount() {
        if(mListOrderHistory != null){
            return mListOrderHistory.size();
        }
        return 0;
    }

    public static class OrderHistoryViewHolder extends RecyclerView.ViewHolder{
        private final ItemOrderHistoryBinding mItemOrderHistoryBinding;
        public OrderHistoryViewHolder(@NonNull ItemOrderHistoryBinding mItemOrderHistoryBinding) {
            super(mItemOrderHistoryBinding.getRoot());
            this.mItemOrderHistoryBinding = mItemOrderHistoryBinding;
        }
    }
}
