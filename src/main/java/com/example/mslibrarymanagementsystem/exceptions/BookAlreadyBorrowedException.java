package com.example.mslibrarymanagementsystem.exceptions;

public class BookAlreadyBorrowedException extends RuntimeException{
    public BookAlreadyBorrowedException(String message) {
        super(message);
    }
}