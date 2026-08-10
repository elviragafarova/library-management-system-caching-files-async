package com.example.mslibrarymanagementsystem.exceptions;

public class InvalidLoanDateException extends RuntimeException{
    public InvalidLoanDateException(String message) {
        super(message);
    }
}