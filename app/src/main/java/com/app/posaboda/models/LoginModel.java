package com.app.posaboda.models;

import android.content.Context;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.app.posaboda.BR;
import com.app.posaboda.R;


public class LoginModel extends BaseObservable {
    private String user_name;
    private String password;
    public ObservableField<String> error_user_name = new ObservableField<>();
    public ObservableField<String> error_password = new ObservableField<>();


    public boolean isDataValid(Context context) {
        if (!user_name.trim().isEmpty() &&
                !password.trim().isEmpty() &&
                password.trim().length() >= 6
        ) {
            error_user_name.set(null);
            error_password.set(null);
            return true;
        } else {

            if (user_name.isEmpty()) {
                error_user_name.set(context.getString(R.string.field_required));

            } else {
                error_user_name.set(null);

            }

            if (password.isEmpty()) {
                error_password.set(context.getString(R.string.field_required));

            } else if (password.trim().length() < 6) {
                error_password.set(context.getString(R.string.pasword_short));

            } else {
                error_password.set(null);

            }
            return false;
        }
    }

    public LoginModel() {
        user_name="";
        password="";
    }

    @Bindable
    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
        notifyPropertyChanged(BR.user_name);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }
}
