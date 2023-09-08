package com.duydv.vn.foodappmvc.model;

import java.io.Serializable;

public class FoodObject implements Serializable {
    private long id;
    private String name;
    private String image;
    private int price;
    private boolean popular;
    private int sale;

    private String description;

    public FoodObject() {
    }

    public FoodObject(long id, String name, String image, int price, boolean popular, int sale, String description) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.popular = popular;
        this.sale = sale;
        this.description = description;
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
}
