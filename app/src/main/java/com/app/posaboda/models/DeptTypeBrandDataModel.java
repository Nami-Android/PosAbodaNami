package com.app.posaboda.models;

import java.io.Serializable;
import java.util.List;

public class DeptTypeBrandDataModel extends StatusResponse implements Serializable {
    private List<DeptTypeBrandModel> data;

    public List<DeptTypeBrandModel> getData() {
        return data;
    }
}
