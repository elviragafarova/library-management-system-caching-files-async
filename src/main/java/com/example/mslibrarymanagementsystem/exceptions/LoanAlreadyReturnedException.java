package com.example.mslibrarymanagementsystem.exceptions;

public class LoanAlreadyReturnedException extends RuntimeException{
    public LoanAlreadyReturnedException(String message) {
        super(message);
    }
}