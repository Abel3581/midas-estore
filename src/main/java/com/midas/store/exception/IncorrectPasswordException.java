package com.midas.store.exception;

public class IncorrectPasswordException extends RuntimeException{

    public IncorrectPasswordException(String message){
        super(message);
    }
}
