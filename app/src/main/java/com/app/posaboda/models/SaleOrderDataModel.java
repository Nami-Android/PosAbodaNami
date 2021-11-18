package com.app.posaboda.models;

import java.io.Serializable;
import java.util.List;

public class SaleOrderDataModel extends StatusResponse implements Serializable {
    private List<SaleOrderModel> data;

    public List<SaleOrderModel> getData() {
        return data;
    }
}
