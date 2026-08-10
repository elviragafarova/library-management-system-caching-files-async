package com.example.mslibrarymanagementsystem.exceptions;

public class LoanNotFoundException extends RuntimeException{
    public LoanNotFoundException(String message) {
        super(message);
    }
}