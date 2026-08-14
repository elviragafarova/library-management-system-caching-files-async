package com.example.mslibrarymanagementsystem.exceptions;

public class MemberNotFoundException extends RuntimeException{
    public MemberNotFoundException(String message) {
        super(message);
    }
}