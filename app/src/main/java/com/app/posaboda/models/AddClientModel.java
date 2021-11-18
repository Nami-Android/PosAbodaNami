package com.app.posaboda.models;

import android.content.Context;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.app.posaboda.BR;
import com.app.posaboda.R;


public class AddClientModel extends BaseObservable {
    private String name;
    private String phone;
    public ObservableField<String> error_name = new ObservableField<>();
    public ObservableField<String> error_phone = new ObservableField<>();


    public boolean isDataValid(Context context) {
        if (!name.trim().isEmpty() &&
                !phone.trim().isEmpty()
        ) {
            error_name.set(null);
            error_phone.set(null);
            return true;
        } else {

            if (name.isEmpty()) {
                error_name.set(context.getString(R.string.field_required));

            } else {
                error_name.set(null);

            }

            if (phone.isEmpty()) {
                error_phone.set(context.getString(R.string.field_required));

            }  else {
                error_phone.set(null);

            }
            return false;
        }
    }

    public AddClientModel() {
        name="";
        phone="";
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);

    }
}
