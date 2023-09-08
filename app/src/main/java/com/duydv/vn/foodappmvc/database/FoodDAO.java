package com.duydv.vn.foodappmvc.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.duydv.vn.foodappmvc.model.Food;

import java.util.List;

@Dao
public interface FoodDAO {
    @Insert
    void insertFood(Food food);

    @Update
    void updateFood(Food food);

    @Delete
    void deleteFood(Food food);

    @Query("SELECT * FROM food")
    List<Food> getListFood();

    @Query("SELECT * FROM food WHERE id = :id")
    List<Food> checkFoodInCart(long id);

    @Query("DELETE FROM food")
    void deleteAllFood();

    @Query("SELECT Sum(sum_price) FROM food")
    int getSumPrice();
}
