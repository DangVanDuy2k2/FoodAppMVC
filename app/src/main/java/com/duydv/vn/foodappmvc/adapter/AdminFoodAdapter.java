package com.duydv.vn.foodappmvc.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duydv.vn.foodappmvc.R;
import com.duydv.vn.foodappmvc.constant.Constant;
import com.duydv.vn.foodappmvc.databinding.ItemAdminFoodBinding;
import com.duydv.vn.foodappmvc.listener.IManagerFoodListener;
import com.duydv.vn.foodappmvc.model.Food;
import com.duydv.vn.foodappmvc.utils.GlideUtils;

import java.util.List;

public class AdminFoodAdapter extends RecyclerView.Adapter<AdminFoodAdapter.AdminFoodViewHolder> {
    private final List<Food> mListFood;
    private final IManagerFoodListener mIManagerFoodListener;

    public AdminFoodAdapter(List<Food> mListFood, IManagerFoodListener mIManagerFoodListener) {
        this.mListFood = mListFood;
        this.mIManagerFoodListener = mIManagerFoodListener;
    }

    @NonNull
    @Override
    public AdminFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAdminFoodBinding itemAdminFoodBinding = ItemAdminFoodBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new AdminFoodViewHolder(itemAdminFoodBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminFoodViewHolder holder, int position) {
        Food food = mListFood.get(position);
        if(food == null){
            return;
        }

        GlideUtils.loadUrl(food.getImage(), holder.mItemAdminFoodBinding.imgFood);
        holder.mItemAdminFoodBinding.txtFoodName.setText(food.getName());

        if(food.getSale() <= 0){
            holder.mItemAdminFoodBinding.txtOldPrice.setVisibility(View.GONE);
            holder.mItemAdminFoodBinding.txtSale.setVisibility(View.GONE);
        }else{
            holder.mItemAdminFoodBinding.txtOldPrice.setVisibility(View.VISIBLE);
            holder.mItemAdminFoodBinding.txtSale.setVisibility(View.VISIBLE);

            String strSale = Constant.SALE + food.getSale() + Constant.PERCENT;
            holder.mItemAdminFoodBinding.txtSale.setText(strSale);

            String strOldPrice = food.getPrice() + Constant.VND;
            holder.mItemAdminFoodBinding.txtOldPrice.setText(strOldPrice);
            //Them dong gach ngang
            holder.mItemAdminFoodBinding.txtOldPrice.setPaintFlags(holder.mItemAdminFoodBinding.txtOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        String strRealPrice = food.getRealPrice() + Constant.VND;
        holder.mItemAdminFoodBinding.txtRealPrice.setText(strRealPrice);

        if(food.isPopular()){
            holder.mItemAdminFoodBinding.txtPopular.setText(R.string.yes);
        }else{
            holder.mItemAdminFoodBinding.txtPopular.setText(R.string.no);
        }
        holder.mItemAdminFoodBinding.txtFoodDescription.setText(food.getDescription());

        holder.mItemAdminFoodBinding.imgEdit.setOnClickListener(view -> mIManagerFoodListener.onClickUpdateFood(food));
        holder.mItemAdminFoodBinding.imgDelete.setOnClickListener(view -> mIManagerFoodListener.onClickDeleteFood(food));
    }

    @Override
    public int getItemCount() {
        if(mListFood != null){
            return mListFood.size();
        }
        return 0;
    }

    public static class AdminFoodViewHolder extends RecyclerView.ViewHolder{
        private final ItemAdminFoodBinding mItemAdminFoodBinding;
        public AdminFoodViewHolder(@NonNull ItemAdminFoodBinding mItemAdminFoodBinding) {
            super(mItemAdminFoodBinding.getRoot());
            this.mItemAdminFoodBinding = mItemAdminFoodBinding;
        }
    }
}
