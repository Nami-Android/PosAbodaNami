package com.app.posaboda.models;

import java.io.Serializable;

public class UserModel extends StatusResponse implements Serializable {

    private Data data;

    public Data getData() {
        return data;
    }

    public static class Data implements Serializable{
        private int id;
        private String name;
        private String email;
        private String phone;
        private String user_name;
        private String logo;
        private String ip_address;
        private String access_permission;
        private String is_block;
        private String is_login;
        private String logout_time;
        private String forget_password_code;
        private String email_verified_at;
        private String deleted_at;
        private String created_at;
        private String updated_at;
        private int sales_orders_count;
        private String token;
        private UserJob user_job;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone() {
            return phone;
        }

        public String getUser_name() {
            return user_name;
        }

        public String getLogo() {
            return logo;
        }

        public String getIp_address() {
            return ip_address;
        }

        public String getAccess_permission() {
            return access_permission;
        }

        public String getIs_block() {
            return is_block;
        }

        public String getIs_login() {
            return is_login;
        }

        public String getLogout_time() {
            return logout_time;
        }

        public String getForget_password_code() {
            return forget_password_code;
        }

        public String getEmail_verified_at() {
            return email_verified_at;
        }

        public String getDeleted_at() {
            return deleted_at;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public int getSales_orders_count() {
            return sales_orders_count;
        }

        public String getToken() {
            return token;
        }

        public UserJob getUser_job() {
            return user_job;
        }
    }

    public static class UserJob implements Serializable{
        private int id;
        private int user_id;
        private String user_type;
        private int branch_id;
        private int bank_or_box_id;
        private int sales_account_id;
        private String purchase_account_id;
        private int balance;

        public int getId() {
            return id;
        }

        public int getUser_id() {
            return user_id;
        }

        public String getUser_type() {
            return user_type;
        }

        public int getBranch_id() {
            return branch_id;
        }

        public int getBank_or_box_id() {
            return bank_or_box_id;
        }

        public int getSales_account_id() {
            return sales_account_id;
        }

        public String getPurchase_account_id() {
            return purchase_account_id;
        }

        public int getBalance() {
            return balance;
        }
    }

}
