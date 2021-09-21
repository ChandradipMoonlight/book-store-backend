package com.bridgelabz.bookstore.exception;

public class BookStoreException extends RuntimeException {
    public enum ExceptionType {
        USER_ALREADY_PRESENT,
        EMAIL_NOT_FOUND,
        PASSWORD_INCORRECT,
        EMAIL_NOT_VERIFIED,
        UNAUTHORISED_USER,
        BOOK_NOT_FOUND
    }
    public BookStoreException.ExceptionType type;

    public BookStoreException(String message, BookStoreException.ExceptionType type) {
        super(message);
        this.type = type;
    }

    public BookStoreException(String message) {
        super(message);
    }
}
