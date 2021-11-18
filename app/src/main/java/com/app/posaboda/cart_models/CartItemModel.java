package com.app.posaboda.cart_models;

import java.io.Serializable;

public class CartItemModel implements Serializable {
    private int item_id;
    private int warehouse_id;
    private int needed_amount;
    private String title;
    private String image;
    private double price;
    private int stock_amount;
    private double total_item_cost;

    public CartItemModel(int item_id, int warehouse_id, int needed_amount, String title, String image, double price, int stock_amount, double total_item_cost) {
        this.item_id = item_id;
        this.warehouse_id = warehouse_id;
        this.needed_amount = needed_amount;
        this.title = title;
        this.image = image;
        this.price = price;
        this.stock_amount = stock_amount;
        this.total_item_cost = total_item_cost;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getWarehouse_id() {
        return warehouse_id;
    }

    public void setWarehouse_id(int warehouse_id) {
        this.warehouse_id = warehouse_id;
    }

    public int getNeeded_amount() {
        return needed_amount;
    }

    public void setNeeded_amount(int needed_amount) {
        this.needed_amount = needed_amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock_amount() {
        return stock_amount;
    }

    public void setStock_amount(int stock_amount) {
        this.stock_amount = stock_amount;
    }

    public double getTotal_item_cost() {
        return total_item_cost;
    }

    public void setTotal_item_cost(double total_item_cost) {
        this.total_item_cost = total_item_cost;
    }
}
