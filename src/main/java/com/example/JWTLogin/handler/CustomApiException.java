package com.example.JWTLogin.handler;

public class CustomApiException extends RuntimeException{

    public CustomApiException(String message) {
        super(message);
    }
}