package com.duydv.vn.foodappmvc.model;

import java.io.Serializable;

public class Order implements Serializable {
    private long id;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String listFoodOrder;
    private int sumPrice;
    private int payment;
    private boolean completed;

    public Order() {
    }

    public Order(long id, String fullName, String email, String phone, String address, String listFoodOrder, int sumPrice, int payment, boolean completed) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.listFoodOrder = listFoodOrder;
        this.sumPrice = sumPrice;
        this.payment = payment;
        this.completed = completed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getListFoodOrder() {
        return listFoodOrder;
    }

    public void setListFoodOrder(String listFoodOrder) {
        this.listFoodOrder = listFoodOrder;
    }

    public int getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(int sumPrice) {
        this.sumPrice = sumPrice;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
