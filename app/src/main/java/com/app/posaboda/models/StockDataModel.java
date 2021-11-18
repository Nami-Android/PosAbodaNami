package com.app.posaboda.models;

import java.io.Serializable;
import java.util.List;

public class StockDataModel extends StatusResponse implements Serializable {
    private List<StockModel> data;

    public List<StockModel> getData() {
        return data;
    }


}
