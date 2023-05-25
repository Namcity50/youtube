package com.example.youtube.exps;

public class ItemAlreadyExistException extends RuntimeException{
    public ItemAlreadyExistException(String message) {
        super(message);
    }
}
