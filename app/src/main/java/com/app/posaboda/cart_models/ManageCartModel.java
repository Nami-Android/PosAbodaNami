package com.app.posaboda.cart_models;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.app.posaboda.preferences.Preferences;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ManageCartModel implements Serializable {
    private static ManageCartModel instance = null;

    private ManageCartModel() {
    }

    public static synchronized ManageCartModel newInstance() {
        if (instance == null) {
            instance = new ManageCartModel();
        }
        return instance;
    }

    public void addItem(CartItemModel cartItemModel, Context context) {
        Preferences preferences = Preferences.getInstance();
        CartDataModel cartDataModel = preferences.getCartData(context);
        if (cartDataModel == null) {
            cartDataModel = new CartDataModel();
        }
        int pos = getItemCartPos(context, cartItemModel.getItem_id());
        List<CartItemModel> list = cartDataModel.getItems();
        if (pos == -1) {
            list.add(cartItemModel);


        } else {
            CartItemModel oldItem = list.get(pos);
            if (cartItemModel.getNeeded_amount() > 0) {
                int amount =  cartItemModel.getNeeded_amount();
                oldItem.setNeeded_amount(amount);
                oldItem.setTotal_item_cost(cartItemModel.getTotal_item_cost());

                list.set(pos, oldItem);
            } else {
                removeItem(context, cartDataModel, cartItemModel.getItem_id());
                list = cartDataModel.getItems();
            }


        }

        cartDataModel.setItems(list);
        preferences.create_cart_data(context, cartDataModel);

    }

    public CartDataModel getCartDataModel(Context context) {
        Preferences preferences = Preferences.getInstance();
        CartDataModel cartDataModel = preferences.getCartData(context);
        if (cartDataModel == null) {
            cartDataModel = new CartDataModel();
        }
        return cartDataModel;
    }

    private int getItemCartPos(Context context, int item_id) {
        int pos = -1;
        CartDataModel cartDataModel = getCartDataModel(context);
        List<CartItemModel> list = cartDataModel.getItems();
        for (int index = 0; index < list.size(); index++) {
            if (item_id == list.get(index).getItem_id()) {
                pos = index;
                return pos;
            }
        }
        return pos;
    }

    public void removeItem(Context context, CartDataModel cartDataModel, int item_id) {
        Preferences preferences = Preferences.getInstance();
        List<CartItemModel> list = cartDataModel.getItems();
        int pos = getItemCartPos(context, item_id);
        if (pos != -1) {
            list.remove(pos);
            cartDataModel.setItems(list);
            preferences.create_cart_data(context, cartDataModel);
        }

    }

    public double getTotalCost(Context context){
        CartDataModel cartDataModel = getCartDataModel(context);
        double total= 0.0;
        for (CartItemModel cartItemModel: cartDataModel.getItems()){
            total += cartItemModel.getTotal_item_cost();
            Log.e("rrrrcost", total+"__");

        }
        return total;
    }

    public void clearData(Context context){

        Preferences preferences = Preferences.getInstance();
        preferences.clearCart(context);
    }
}
