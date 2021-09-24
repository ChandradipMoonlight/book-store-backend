package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.BookDTO;

import java.util.List;

public interface IBookService {
    String addBook(String token, BookDTO bookDTO);

    List<BookDTO> getBooks(String token);

    String deleteBook(String token, int bookId);

    String updateBookPrice(String token, int id, double price);

    String updateBookQuantity(String token, int id, int quantity);
}