package com.app.posaboda.models;

import java.io.Serializable;

public class SingleClientModel extends StatusResponse implements Serializable {
    private SaleOrderModel.Client data;

    public SaleOrderModel.Client getData() {
        return data;
    }
}
