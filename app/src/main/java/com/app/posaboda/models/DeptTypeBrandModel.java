package com.app.posaboda.models;

import java.io.Serializable;

public class DeptTypeBrandModel implements Serializable {
    private int id;
    private String logo;
    private String title;
    private String desc;
    private int created_by;
    private boolean isSelected=false;

    public DeptTypeBrandModel() {
    }

    public DeptTypeBrandModel(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getLogo() {
        return logo;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public int getCreated_by() {
        return created_by;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
