package com.example.prateek.bimsapp;

/**
 * Created by prateek on 19/1/17.
 */

public class OrderItem {
    private String food, quantity, price;

    public OrderItem() {
    }

    public OrderItem(String food, String quantity, String price) {
        this.food = food;
        this.price = price;
        this.quantity = quantity;
    }



    public String getFoodItem() {
        return food;
    }

    public void setFoodItem(String foodItem) {
        this.food = foodItem;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setQuantity(String quantity){this.quantity = quantity;}

    public String getQuantity(){ return  quantity;}
}
