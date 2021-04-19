package com.example.demo.exception;

public class NoContentException extends RuntimeException{

    private static final long serialVersionUID = 234234234L;

    public NoContentException(String message){
        super(message);
    }

}
