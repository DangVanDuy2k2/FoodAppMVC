package com.duydv.vn.foodappmvc.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duydv.vn.foodappmvc.constant.Constant;
import com.duydv.vn.foodappmvc.databinding.ItemFoodPopularBinding;
import com.duydv.vn.foodappmvc.listener.IItemFoodListener;
import com.duydv.vn.foodappmvc.model.Food;
import com.duydv.vn.foodappmvc.utils.GlideUtils;

import java.util.List;

public class FoodPopularAdapter extends RecyclerView.Adapter<FoodPopularAdapter.FoodPopularViewHolder> {
    private final List<Food> mListFoodPopulars;
    public IItemFoodListener mIOnClickFoodItem;

    public FoodPopularAdapter(List<Food> mListFoodPopulars, IItemFoodListener mIOnClickFoodItem) {
        this.mListFoodPopulars = mListFoodPopulars;
        this.mIOnClickFoodItem = mIOnClickFoodItem;
    }

    @NonNull
    @Override
    public FoodPopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFoodPopularBinding mItemFoodPopularBinding = ItemFoodPopularBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new FoodPopularViewHolder(mItemFoodPopularBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodPopularViewHolder holder, int position) {
        Food food = mListFoodPopulars.get(position);
        if(food == null){
            return;
        }

        GlideUtils.loadUrl(food.getImage(),holder.mItemFoodPopularBinding.imgFoodPopular);
        if(food.getSale() <= 0){
            holder.mItemFoodPopularBinding.txtSale.setVisibility(View.GONE);
        }else {
            holder.mItemFoodPopularBinding.txtSale.setVisibility(View.VISIBLE);

            String strSale = Constant.SALE + food.getSale() + Constant.PERCENT;
            holder.mItemFoodPopularBinding.txtSale.setText(strSale);
        }

        holder.mItemFoodPopularBinding.itemFoodPopular.setOnClickListener(view -> mIOnClickFoodItem.onClickFoodItem(food));
    }

    @Override
    public int getItemCount() {
        if(mListFoodPopulars != null){
            return mListFoodPopulars.size();
        }
        return 0;
    }

    public static class FoodPopularViewHolder extends RecyclerView.ViewHolder{
        private final ItemFoodPopularBinding mItemFoodPopularBinding;
        public FoodPopularViewHolder(@NonNull ItemFoodPopularBinding mItemFoodPopularBinding) {
            super(mItemFoodPopularBinding.getRoot());
            this.mItemFoodPopularBinding = mItemFoodPopularBinding;
        }
    }
}
