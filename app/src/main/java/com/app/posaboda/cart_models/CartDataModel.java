package com.app.posaboda.cart_models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartDataModel implements Serializable {
    private int client_id;
    private String notes ;
    private List<CartItemModel> items;

    public CartDataModel() {
        notes ="";
        items = new ArrayList<>();
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<CartItemModel> getItems() {
        return items;
    }

    public void setItems(List<CartItemModel> items) {
        this.items = items;
    }
}
