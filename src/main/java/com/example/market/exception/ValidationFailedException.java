package com.example.market.exception;

public class ValidationFailedException extends Exception{
    public ValidationFailedException() {
        super("Validation Failed");
    }
}
