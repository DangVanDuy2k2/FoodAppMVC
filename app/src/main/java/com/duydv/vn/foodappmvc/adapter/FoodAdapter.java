package com.duydv.vn.foodappmvc.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duydv.vn.foodappmvc.constant.Constant;
import com.duydv.vn.foodappmvc.databinding.ItemFoodBinding;
import com.duydv.vn.foodappmvc.listener.IItemFoodListener;
import com.duydv.vn.foodappmvc.model.Food;
import com.duydv.vn.foodappmvc.utils.GlideUtils;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private final List<Food> mListFoods;

    public IItemFoodListener mIItemFoodListener;

    public FoodAdapter(List<Food> mListFoods, IItemFoodListener mIItemFoodListener) {
        this.mListFoods = mListFoods;
        this.mIItemFoodListener = mIItemFoodListener;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFoodBinding mItemFoodBinding = ItemFoodBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new FoodViewHolder(mItemFoodBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = mListFoods.get(position);
        if(food == null){
            return;
        }

        GlideUtils.loadUrl(food.getImage(), holder.mItemFoodBinding.imgFood);
        holder.mItemFoodBinding.txtFoodName.setText(food.getName());
        if(food.getSale() <= 0){
            holder.mItemFoodBinding.txtOldPrice.setVisibility(View.GONE);
            holder.mItemFoodBinding.txtSale.setVisibility(View.GONE);
        }else{
            holder.mItemFoodBinding.txtOldPrice.setVisibility(View.VISIBLE);
            holder.mItemFoodBinding.txtSale.setVisibility(View.VISIBLE);

            String strSale = Constant.SALE + food.getSale() + Constant.PERCENT;
            holder.mItemFoodBinding.txtSale.setText(strSale);

            String strOldPrice = food.getPrice() + Constant.VND;
            holder.mItemFoodBinding.txtOldPrice.setText(strOldPrice);
           //Them dong gach ngang
            holder.mItemFoodBinding.txtOldPrice.setPaintFlags(holder.mItemFoodBinding.txtOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        String strRealPrice = food.getRealPrice() + Constant.VND;
        holder.mItemFoodBinding.txtRealPrice.setText(strRealPrice);

        holder.mItemFoodBinding.itemFood.setOnClickListener(view -> mIItemFoodListener.onClickFoodItem(food));
    }

    @Override
    public int getItemCount() {
        if(mListFoods != null){
            return mListFoods.size();
        }
        return 0;
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder{
        private final ItemFoodBinding mItemFoodBinding;

        public FoodViewHolder(@NonNull ItemFoodBinding mItemFoodBinding) {
            super(mItemFoodBinding.getRoot());
            this.mItemFoodBinding = mItemFoodBinding;
        }
    }
}
