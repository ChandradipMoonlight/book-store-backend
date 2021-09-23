package com.bridgelabz.bookstore.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookDTO {

    private int bookId;

    @NotEmpty(message = "Book Name cannot be null")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]{2,25}", message = "Book Name Invalid")
    private String bookName;

    @NotEmpty(message = "Author Name cannot be null")
    @Pattern(regexp = "^[A-Z][a-zA-Z0-9\\s]{2,25}", message = "Author Name Invalid")
    private String bookAuthor;

    @NotEmpty(message = "Book description cannot be null")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Description Invalid")
    private String bookDescription;

    @NotEmpty(message = "Logo cannot be null")
    private String bookLogo;

    @NotNull(message = "Price cannot be null")
    private double bookPrice;

    @NotNull(message = "Quantity cannot be null")
    private int bookQuantity;
}
