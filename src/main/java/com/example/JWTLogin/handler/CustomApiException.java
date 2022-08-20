package com.example.JWTLogin.handler;

import java.util.Map;

public class CustomApiException extends RuntimeException{

    public CustomApiException(String message) {
        super(message);
    }
}