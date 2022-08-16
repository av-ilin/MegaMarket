package com.example.market.service;

import com.example.market.entity.ItemEntity;

import java.util.ArrayList;
import java.util.Date;

public class Request {
    private ArrayList<ItemEntity> items;
    private String updateDate;
    public Request() {}
    public ArrayList<ItemEntity> getItems() {
        return items;
    }
    public void setItems(ArrayList<ItemEntity> items) {
        this.items = items;
    }
    public String getUpdateDate() {
        return updateDate;
    }
    public void setUpdateDate(String date) {
        this.updateDate = date;
    }
}
