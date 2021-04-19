package com.example.demo.exception;

public class NotFoundException extends RuntimeException{

    private static final long serialVersionUID = 234234234L;

    public NotFoundException(String message){
        super(message);
    }

}
