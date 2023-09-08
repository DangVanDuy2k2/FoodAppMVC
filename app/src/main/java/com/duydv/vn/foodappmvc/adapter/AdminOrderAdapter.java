package com.duydv.vn.foodappmvc.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duydv.vn.foodappmvc.R;
import com.duydv.vn.foodappmvc.constant.Constant;
import com.duydv.vn.foodappmvc.databinding.ItemOrderAdminBinding;
import com.duydv.vn.foodappmvc.model.Order;
import com.duydv.vn.foodappmvc.utils.DateTimeUtils;

import java.util.List;

public class AdminOrderAdapter extends RecyclerView.Adapter<AdminOrderAdapter.AdminOrderViewHolder> {
    private final List<Order> mListOrder;
    private final ISetStatusOrder mISetStatusOrder;

    public interface ISetStatusOrder{
        void onClickCheckBox(Order order);
    }

    public AdminOrderAdapter(List<Order> mListOrder, ISetStatusOrder mISetStatusOrder) {
        this.mListOrder = mListOrder;
        this.mISetStatusOrder = mISetStatusOrder;
    }

    @NonNull
    @Override
    public AdminOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderAdminBinding itemOrderAdminBinding = ItemOrderAdminBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new AdminOrderViewHolder(itemOrderAdminBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminOrderViewHolder holder, int position) {
        Order order = mListOrder.get(position);
        if(order == null){
            return;
        }

        if(order.isCompleted()){
            holder.mItemOrderAdminBinding.layoutContent.setBackgroundResource(R.color.colorDivider);
        }else{
            holder.mItemOrderAdminBinding.layoutContent.setBackgroundResource(R.color.white);
        }

        holder.mItemOrderAdminBinding.txtIdOrder.setText(String.valueOf(order.getId()));
        holder.mItemOrderAdminBinding.txtNameOrder.setText(order.getFullName());
        holder.mItemOrderAdminBinding.txtAddressOrder.setText(order.getAddress());
        holder.mItemOrderAdminBinding.txtPhoneOrder.setText(order.getPhone());
        holder.mItemOrderAdminBinding.txtMenuOrder.setText(order.getListFoodOrder());

        String strDate = DateTimeUtils.convertTimeToDate(order.getId());
        holder.mItemOrderAdminBinding.txtDateOrder.setText(strDate);

        String str_sumPrice = order.getSumPrice() + Constant.VND;
        holder.mItemOrderAdminBinding.txtSumPriceOrder.setText(str_sumPrice);

        if(order.getPayment() == Constant.CART_PAYMENT_METHOD){
            holder.mItemOrderAdminBinding.txtPaymentMethodOrder.setText(Constant.CASH);
        }

        holder.mItemOrderAdminBinding.chkStatus.setChecked(order.isCompleted());

        holder.mItemOrderAdminBinding.chkStatus.setOnClickListener(view -> mISetStatusOrder.onClickCheckBox(order));
    }

    @Override
    public int getItemCount() {
        if(mListOrder != null){
            return mListOrder.size();
        }
        return 0;
    }

    public static class AdminOrderViewHolder extends RecyclerView.ViewHolder{
        private final ItemOrderAdminBinding mItemOrderAdminBinding;
        public AdminOrderViewHolder(@NonNull ItemOrderAdminBinding mItemOrderAdminBinding) {
            super(mItemOrderAdminBinding.getRoot());
            this.mItemOrderAdminBinding = mItemOrderAdminBinding;
        }
    }
}
