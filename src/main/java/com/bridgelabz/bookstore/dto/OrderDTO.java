package com.bridgelabz.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    @NotNull(message = "User Id cannot be null")
    private int userId;

    @NotNull(message = "Book id cannot be null")
    private int bookId;

    @NotNull(message = "Quantity cannot be null.")
    private int orderQuantity;

    private double orderPrice;

    private String State;
    private String City;

    @NotNull(message = "Zip code cannot be null.")
    private String zipCode;

    @NotNull(message = "Address cannot be null.")
    private String address;

    @Pattern(regexp = "Home|Work|Other", message ="User Type should be Home or Work or Other!")
    private String addressType;
}
