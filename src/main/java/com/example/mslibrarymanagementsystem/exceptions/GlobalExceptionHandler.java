package com.example.mslibrarymanagementsystem.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(AuthorNotFoundException.class)
    public ErrorResponse handleException(AuthorNotFoundException ex) {
        return new ErrorResponse("author.not.found", ex.getMessage());
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(BookNotFoundException.class)
    public ErrorResponse handleException(BookNotFoundException ex) {
        return new ErrorResponse("book.not.found", ex.getMessage());
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(MemberNotFoundException.class)
    public ErrorResponse handleException(MemberNotFoundException ex) {
        return new ErrorResponse("member.not.found", ex.getMessage());
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(BookAlreadyBorrowedException.class)
    public ErrorResponse handleException(BookAlreadyBorrowedException ex) {
        return new ErrorResponse("book.already.borrowed", ex.getMessage());
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(BookAlreadyReturnedException.class)
    public ErrorResponse handleException(BookAlreadyReturnedException ex) {
        return new ErrorResponse("book.already.returned", ex.getMessage());
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(LoanAlreadyReturnedException.class)
    public ErrorResponse handleException(LoanAlreadyReturnedException ex) {
        return new ErrorResponse("loan.already.returned", ex.getMessage());
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ErrorResponse handleException(CategoryAlreadyExistsException ex) {
        return new ErrorResponse("category.already.exists", ex.getMessage());
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(CategoryNotFoundException.class)
    public ErrorResponse handleException(CategoryNotFoundException ex) {
        return new ErrorResponse("category.not.found", ex.getMessage());
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(LoanNotFoundException.class)
    public ErrorResponse handleException(LoanNotFoundException ex) {
        return new ErrorResponse("loan.not.found", ex.getMessage());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(InvalidLoanDateException.class)
    public ErrorResponse handleException(InvalidLoanDateException ex) {
        return new ErrorResponse("invalid.loan.date", ex.getMessage());
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ErrorResponse handleException(EmailAlreadyExistsException ex) {
        return new ErrorResponse("email.already.exists", ex.getMessage());
    }

    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(InvalidCredentialsException.class)
    public ErrorResponse handleException(InvalidCredentialsException ex) {
        return new ErrorResponse("invalid.credentials", ex.getMessage());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleException(MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return new ErrorResponse("validation.failed", message);
    }
}