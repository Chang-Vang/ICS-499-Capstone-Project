package com.ICS499.Application.model;

import com.ICS499.Application.FoodItem;

public class OrderItem {

    private FoodItem food;
    private int quantity;


    public OrderItem(FoodItem food, int quantity) {
        this.food = food;
        this.quantity = quantity;
    }

    public FoodItem getFood() {return food;}

    public void setFood(FoodItem food) {this.food = food;}

    public int getQuantity() {return quantity;}

    public void setQuantity(int quantity) {this.quantity = quantity;}
}
