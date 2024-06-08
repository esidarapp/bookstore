package com.example.spring.repository;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String s) {
        super(s);
    }

    public DataProcessingException(String s, Exception e) {
        super(s,e);
    }
}
