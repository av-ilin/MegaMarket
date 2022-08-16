package com.example.market.exception;

public class ItemNotFoundException extends Exception{
    public ItemNotFoundException() {
        super("Item not found");
    }
}
