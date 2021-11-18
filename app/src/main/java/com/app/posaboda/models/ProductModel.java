package com.app.posaboda.models;

import java.io.Serializable;

public class ProductModel implements Serializable {
    private int id;
    private String title;
    private String full_title;
    private int category_id;
    private int brand_id;
    private int type_id;
    private double cost_price;
    private double full_price;
    private double half_price;
    private double price;
    private int minimum_order_shipping;
    private String code;
    private String barcode;
    private String unit;
    private String main_image;
    private String type;
    private int item_commission;
    private String website_display;
    private String cashier_display;
    private int created_by;
    private int amount;
    private int warehouse_id;
    private String warehouse_title;
    private double item_price_for_cal;
    private DeptTypeBrandModel brand_rl;
    private DeptTypeBrandModel category_rl;
    private DeptTypeBrandModel type_rl;
    private int count = 0;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getFull_title() {
        return full_title;
    }

    public int getCategory_id() {
        return category_id;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public int getType_id() {
        return type_id;
    }

    public double getCost_price() {
        return cost_price;
    }

    public double getFull_price() {
        return full_price;
    }

    public double getHalf_price() {
        return half_price;
    }

    public double getPrice() {
        return price;
    }

    public int getMinimum_order_shipping() {
        return minimum_order_shipping;
    }

    public String getCode() {
        return code;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getUnit() {
        return unit;
    }

    public String getMain_image() {
        return main_image;
    }

    public String getType() {
        return type;
    }

    public int getItem_commission() {
        return item_commission;
    }

    public String getWebsite_display() {
        return website_display;
    }

    public String getCashier_display() {
        return cashier_display;
    }

    public int getCreated_by() {
        return created_by;
    }

    public int getAmount() {
        return amount;
    }

    public int getWarehouse_id() {
        return warehouse_id;
    }

    public String getWarehouse_title() {
        return warehouse_title;
    }

    public double getItem_price_for_cal() {
        return item_price_for_cal;
    }

    public DeptTypeBrandModel getBrand_rl() {
        return brand_rl;
    }

    public DeptTypeBrandModel getCategory_rl() {
        return category_rl;
    }

    public DeptTypeBrandModel getType_rl() {
        return type_rl;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
