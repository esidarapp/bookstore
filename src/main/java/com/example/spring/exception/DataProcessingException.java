package com.example.spring.exception;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String s) {
        super(s);
    }

    public DataProcessingException(String s, Exception e) {
        super(s,e);
    }
}
