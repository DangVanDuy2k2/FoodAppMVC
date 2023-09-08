package com.duydv.vn.foodappmvc.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity(tableName = "food")
public class Food implements Serializable {
    @PrimaryKey
    private long id;
    private String name;
    private String image;
    private int price;
    private boolean popular;
    private int sale;

    private String description;
    private int count;
    private int sum_price;

    public Food() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isPopular() {
        return popular;
    }

    public void setPopular(boolean popular) {
        this.popular = popular;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSum_price() {
        return sum_price;
    }

    public void setSum_price(int sum_price) {
        this.sum_price = sum_price;
    }

    public int getRealPrice(){
        if(sale <= 0){
            return price;
        }
        return price - (price * sale) / 100;
    }

}
