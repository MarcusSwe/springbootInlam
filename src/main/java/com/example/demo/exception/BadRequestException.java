package com.example.demo.exception;

public class BadRequestException extends RuntimeException{

    private static final long serialVersionUID = 234234234L;

    public BadRequestException(String message){
        super(message);
    }
}
