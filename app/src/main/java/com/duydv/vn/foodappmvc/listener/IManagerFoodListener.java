package com.duydv.vn.foodappmvc.listener;

import com.duydv.vn.foodappmvc.model.Food;

public interface IManagerFoodListener {
    void onClickUpdateFood(Food food);
    void onClickDeleteFood(Food food);
}
