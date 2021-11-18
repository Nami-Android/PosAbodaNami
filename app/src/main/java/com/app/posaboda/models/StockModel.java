package com.app.posaboda.models;

import java.io.Serializable;

public class StockModel implements Serializable {
    private int id;
    private String account_id;
    private int branch_id;
    private int storekeeper_id;
    private String title;
    private String code;
    private String name_to_display;
    private String address;
    private String phone;
    private String notes;

    public int getId() {
        return id;
    }

    public String getAccount_id() {
        return account_id;
    }

    public int getBranch_id() {
        return branch_id;
    }

    public int getStorekeeper_id() {
        return storekeeper_id;
    }

    public String getTitle() {
        return title;
    }

    public String getCode() {
        return code;
    }

    public String getName_to_display() {
        return name_to_display;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getNotes() {
        return notes;
    }
}
