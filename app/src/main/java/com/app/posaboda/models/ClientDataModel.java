package com.app.posaboda.models;

import java.io.Serializable;
import java.util.List;

public class ClientDataModel extends StatusResponse implements Serializable {
    private List<SaleOrderModel.Client> data;

    public List<SaleOrderModel.Client> getData() {
        return data;
    }
}
