package com.example.market.other;

public class MyPair {
    private Integer key;
    private Integer value;
    public MyPair(Integer key, Integer value) {
        this.key = key;
        this.value = value;
    }
    public Integer getKey() {
        return key;
    }
    public void setKey(Integer key) {
        this.key = key;
    }
    public Integer getValue() {
        return value;
    }
    public void setValue(Integer value) {
        this.value = value;
    }
}
