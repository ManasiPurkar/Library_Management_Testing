package org.example;

import java.util.*;

public class User {
    private int userId;
    private String name;
    private List<Book> borrowedBooks;

    // Constructor
    public User(int userId, String name) {
        this.userId = userId;
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
    }

    // Borrow a book
    public void borrowBook(Book book) {
        if (book == null) {
            System.out.println("Cannot borrow a null book.");
            return; // Exit the method
        }
        if (book.isAvailable()) {
            borrowedBooks.add(book);
            book.setAvailable(false);
            System.out.println(name + " successfully borrowed \"" + book.getTitle() + "\".");
        } else {
            System.out.println("Sorry, \"" + book.getTitle() + "\" is currently not available.");
        }
    }

    // Return a book
    public void returnBook(Book book) {
        if (book == null) {
            System.out.println("Cannot return a null book.");
            return; // Exit the method
        }
        if (borrowedBooks.contains(book)) {
            borrowedBooks.remove(book);
            book.setAvailable(true);
            System.out.println(name + " successfully returned \"" + book.getTitle() + "\".");
        } else {
            System.out.println("You haven't borrowed \"" + book.getTitle() + "\".");
        }
    }

    // Display borrowed books
    public void displayBorrowedBooks() {
        System.out.println(name + " has borrowed the following books:");
        for (Book book : borrowedBooks) {
            book.displayBook();
        }
    }
    public List<Book> getBorrowedBooks()
    {
        return borrowedBooks;
    }
    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }
}
