package com.example.market.model;

import com.example.market.entity.ItemEntity;
import com.example.market.other.MyPair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ItemModel {
    private String id;
    private String type;
    private String name;
    private String parentId;
    private Integer price;
    private String date;
    private ArrayList<ItemModel> children;
    public ItemModel() {}
    public static ItemModel toModel(ItemEntity entity, ArrayList<ItemEntity> items){
        ItemModel model = new ItemModel();
        model.setId(entity.getId());
        model.setType(entity.getType());
        model.setName(entity.getName());
        model.setParentId(entity.getParentId());
        model.setDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(entity.getDate()));
        if(model.type.equals("CATEGORY")) {
            model.setChildren(model.findChildren(items));
            MyPair price_stat = model.findPrice();
            if (price_stat.getKey() == 0)
                model.setPrice(null);
            else
                model.setPrice(price_stat.getValue()/price_stat.getKey());
        }
        else
            model.setPrice(entity.getPrice());
        return model;
    }
    private ArrayList<ItemModel> findChildren(ArrayList<ItemEntity> items){
        ArrayList<ItemModel> child = new ArrayList<>();

        for(int i = 0; i < items.size(); i++)
            if (items.get(i).getParentId() != null)
                if (items.get(i).getParentId().equals(id))
                    child.add(ItemModel.toModel(items.get(i), items));

        return child;
    }
    private MyPair findPrice(){
        MyPair price = new MyPair(0,0);

        for(int i = 0; i < children.size(); i++) {
            ItemModel child = children.get(i);
            if(child.getType().equals("CATEGORY")){
                MyPair price_ = child.findPrice();
                price.setKey(price.getKey() + price_.getKey());
                price.setValue(price.getValue() + price_.getValue());
            }
            else {
                price.setKey(price.getKey() + 1);
                price.setValue(price.getValue() + child.getPrice());
            }
        }

        return price;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getParentId() {
        return parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setChildren(ArrayList<ItemModel> children) {
        this.children = children;
    }
    public ArrayList<ItemModel> getChildren() {
        return children;
    }
}
