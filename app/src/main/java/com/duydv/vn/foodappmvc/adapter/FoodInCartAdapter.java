package com.duydv.vn.foodappmvc.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duydv.vn.foodappmvc.constant.Constant;
import com.duydv.vn.foodappmvc.database.FoodDatabase;
import com.duydv.vn.foodappmvc.databinding.ItemFoodInCartBinding;
import com.duydv.vn.foodappmvc.listener.IManagerFoodListener;
import com.duydv.vn.foodappmvc.model.Food;
import com.duydv.vn.foodappmvc.utils.GlideUtils;

import java.util.List;

public class FoodInCartAdapter extends RecyclerView.Adapter<FoodInCartAdapter.FoodInCartViewHolder> {
    private final List<Food> mListFoodInCarts;
    private final IManagerFoodListener mIManagerFoodListener;

    public FoodInCartAdapter(List<Food> mListFoodInCarts, IManagerFoodListener mIManagerFoodListener) {
        this.mListFoodInCarts = mListFoodInCarts;
        this.mIManagerFoodListener = mIManagerFoodListener;
    }

    @NonNull
    @Override
    public FoodInCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFoodInCartBinding itemFoodInCartBinding = ItemFoodInCartBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new FoodInCartViewHolder(itemFoodInCartBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodInCartViewHolder holder, int position) {
        Food food = mListFoodInCarts.get(position);
        if(food == null){
            return;
        }

        GlideUtils.loadUrl(food.getImage(),holder.mItemFoodInCartBinding.imgFoodInCart);
        holder.mItemFoodInCartBinding.txtFoodNameInCart.setText(food.getName());

        String strPrice = food.getRealPrice() + Constant.VND;
        holder.mItemFoodInCartBinding.txtFoodPriceInCart.setText(strPrice);
        holder.mItemFoodInCartBinding.txtCount.setText(String.valueOf(food.getCount()));

        holder.mItemFoodInCartBinding.txtMinus.setOnClickListener(view -> {
            int count = Integer.parseInt(holder.mItemFoodInCartBinding.txtCount.getText().toString());
            if(count <= 1){
                return;
            }
            int newCount = count - 1;
            holder.mItemFoodInCartBinding.txtCount.setText(String.valueOf(newCount));

            int newSumPrice = newCount * food.getRealPrice();

            food.setCount(newCount);
            food.setSum_price(newSumPrice);

            mIManagerFoodListener.onClickUpdateFood(food);
        });

        holder.mItemFoodInCartBinding.txtPlus.setOnClickListener(view -> {
            int count = Integer.parseInt(holder.mItemFoodInCartBinding.txtCount.getText().toString());
            int newCount = count + 1;
            holder.mItemFoodInCartBinding.txtCount.setText(String.valueOf(newCount));

            int newSumPrice = newCount * food.getRealPrice();

            food.setCount(newCount);
            food.setSum_price(newSumPrice);

            mIManagerFoodListener.onClickUpdateFood(food);
        });

        holder.mItemFoodInCartBinding.txtDelete.setOnClickListener(view -> {
            mIManagerFoodListener.onClickDeleteFood(food);
        });
    }

    @Override
    public int getItemCount() {
        if(mListFoodInCarts != null){
            return mListFoodInCarts.size();
        }
        return 0;
    }

    public static class FoodInCartViewHolder extends RecyclerView.ViewHolder{
        private final ItemFoodInCartBinding mItemFoodInCartBinding;
        public FoodInCartViewHolder(@NonNull ItemFoodInCartBinding mItemFoodInCartBinding) {
            super(mItemFoodInCartBinding.getRoot());
            this.mItemFoodInCartBinding = mItemFoodInCartBinding;
        }
    }
}
