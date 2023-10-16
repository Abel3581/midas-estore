package com.midas.store.exception;

public class CartEmptyException extends RuntimeException{

    public CartEmptyException(String message){
        super(message);
    }
}
