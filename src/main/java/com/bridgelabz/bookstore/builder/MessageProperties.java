package com.bridgelabz.bookstore.builder;

public enum MessageProperties {
    REGISTRATION_SUCCESSFUL("Registration Successful."),
    EMAIL_VERIFIED("Email Verification Successful."),
    LOGIN_SUCCESSFUL("Login Successful."),
    USER_ALREADY_EXIST("User Already Exist."),
    REST_REQUEST_EXCEPTION("Exception in Rest Request."),
    FORGET_PASSWORD("Forget Password."),
    RESET_PASSWORD("Reset Password Successful."),
    BOOK_ADDED_SUCCESSFULLY("Book Added Successfully."),
    GET_BOOKS("List of all books."),
    DELETE_BOOK("Deleted the selected book from the list."),
    BOOK_NOT_FOUND("Book not found."),
    UPDATED_BOOK_PRICE("Updated price of the book."),
    UPDATED_BOOK_QUANTITY("Updated quantity of the book.");
    private String message;

    MessageProperties(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
