package com.bridgelabz.bookstore;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class BookStoreAppApplication {

    public static void main(String[] args) {
        log.info("Inside main method of book-store application.");
        SpringApplication.run(BookStoreAppApplication.class, args);
        System.out.println("Welcome to the BOOK-STORE Application!" +
                "\nApplication Started.");
    }

}
