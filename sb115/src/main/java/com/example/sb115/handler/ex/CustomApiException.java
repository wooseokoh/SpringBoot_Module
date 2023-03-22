package com.example.sb115.handler.ex;

public class CustomApiException extends RuntimeException {

    public CustomApiException(String message) {
        super(message);
    }
}