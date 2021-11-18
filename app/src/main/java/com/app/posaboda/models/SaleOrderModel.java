package com.app.posaboda.models;

import java.io.Serializable;
import java.util.List;

public class SaleOrderModel implements Serializable {

    private int id;
    private String is_confirmed;
    private String type;
    private String date;
    private int client_id;
    private int total_cost;
    private String notes;
    private int branch_id;
    private String cashier_id;
    private int created_by;
    private String created_at;
    private String updated_at;
    private Client client;
    private Branch branch;
    private Client created_user;
    private List<SalesDetail> sales_details;

    public int getId() {
        return id;
    }

    public String getIs_confirmed() {
        return is_confirmed;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public int getClient_id() {
        return client_id;
    }

    public int getTotal_cost() {
        return total_cost;
    }

    public String getNotes() {
        return notes;
    }

    public int getBranch_id() {
        return branch_id;
    }

    public String getCashier_id() {
        return cashier_id;
    }

    public int getCreated_by() {
        return created_by;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public Client getClient() {
        return client;
    }

    public Branch getBranch() {
        return branch;
    }

    public Client getCreated_user() {
        return created_user;
    }

    public List<SalesDetail> getSales_details() {
        return sales_details;
    }

    public static class Client implements Serializable{
       private int id;
       private String type;
       private String customer_status;
       private int client_account_id;
       private String supplier_account_id;
       private String name;
       private String code;
       private String customer_work;
       private String email;
       private String phone;
       private String address;
       private String website_link;
       private String logo;
       private String notes;
       private String client_type;
       private int credit_limit_value;
       private int created_by;
       private String created_at;
       private String updated_at;

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getType() {
            return type;
        }

        public String getCustomer_status() {
            return customer_status;
        }

        public int getClient_account_id() {
            return client_account_id;
        }

        public String getSupplier_account_id() {
            return supplier_account_id;
        }

        public String getName() {
            return name;
        }

        public String getCode() {
            return code;
        }

        public String getCustomer_work() {
            return customer_work;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone() {
            return phone;
        }

        public String getAddress() {
            return address;
        }

        public String getWebsite_link() {
            return website_link;
        }

        public String getLogo() {
            return logo;
        }

        public String getNotes() {
            return notes;
        }

        public String getClient_type() {
            return client_type;
        }

        public int getCredit_limit_value() {
            return credit_limit_value;
        }

        public int getCreated_by() {
            return created_by;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }
    }

    public static class Branch implements Serializable{
        private int id;
        private String title;
        private String ip_address;
        private String email;
        private Object phone;
        private String address;
        private String logo;
        private int created_by;
        private String created_at;
        private String updated_at;

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getIp_address() {
            return ip_address;
        }

        public String getEmail() {
            return email;
        }

        public Object getPhone() {
            return phone;
        }

        public String getAddress() {
            return address;
        }

        public String getLogo() {
            return logo;
        }

        public int getCreated_by() {
            return created_by;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }
    }


    public static class Item implements Serializable{
        private int id;
        private String title;
        private String full_title;
        private int category_id;
        private int brand_id;
        private int type_id;
        private int cost_price;
        private int full_price;
        private int half_price;
        private int price;
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
        private String created_at;
        private String updated_at;

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

        public int getCost_price() {
            return cost_price;
        }

        public int getFull_price() {
            return full_price;
        }

        public int getHalf_price() {
            return half_price;
        }

        public int getPrice() {
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

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }
    }

    public static class Warehouse implements Serializable{
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
        private int created_by;
        private String created_at;
        private String updated_at;

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

        public int getCreated_by() {
            return created_by;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }
    }

    public static class SalesDetail implements Serializable{
        private int id;
        private String is_confirmed;
        private int sale_order_id;
        private int warehouse_id;
        private int item_id;
        private int amount;
        private int item_cost;
        private int total_cost;
        private String date;
        private String notes;
        private int branch_id;
        private int created_by;
        private String created_at;
        private String updated_at;
        private Item item;
        private Warehouse warehouse;

        public int getId() {
            return id;
        }

        public String getIs_confirmed() {
            return is_confirmed;
        }

        public int getSale_order_id() {
            return sale_order_id;
        }

        public int getWarehouse_id() {
            return warehouse_id;
        }

        public int getItem_id() {
            return item_id;
        }

        public int getAmount() {
            return amount;
        }

        public int getItem_cost() {
            return item_cost;
        }

        public int getTotal_cost() {
            return total_cost;
        }

        public String getDate() {
            return date;
        }

        public String getNotes() {
            return notes;
        }

        public int getBranch_id() {
            return branch_id;
        }

        public int getCreated_by() {
            return created_by;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public Item getItem() {
            return item;
        }

        public Warehouse getWarehouse() {
            return warehouse;
        }
    }
}
