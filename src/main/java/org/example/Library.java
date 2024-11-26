package org.example;

import java.util.*;

public class Library {
    private List<Book> books;
    private List<User> users;

    // Constructor
    public Library() {
        this.books = new ArrayList<>();
        this.users = new ArrayList<>();
    }
    // Getter for users list
    public List<User> getUsers() {
        return users;
    }

    public List<Book> getBooks() {
        return books;
    }
    // Add book to the library
    public void addBook(Book book) {
        books.add(book);
        System.out.println("\"" + book.getTitle() + "\" has been added to the library.");
    }

    // Add user to the library
    public void addUser(User user) {
        users.add(user);
        System.out.println(user.getName() + " has been added as a user.");
    }

    // Display all books
    public void displayAllBooks() {
        System.out.println("Library Books:");
        for (Book book : books) {
            book.displayBook();
        }
    }

    // Search for a book by title
    public Book searchBookByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
//        System.out.println("Book with title \"" + title + "\" not found.");
        return null;
    }

    // Search for books by author
    public List<Book> searchBooksByAuthor(String author) {
        List<Book> foundBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }

    // Display all users
    public void displayUsers() {
        System.out.println("Library Users:");
        for (User user : users) {
            System.out.println(user.getName() + " (User ID: " + user.getUserId() + ")");
        }
    }

    // Remove a book from the library
    public void removeBook(Book book) {
        books.remove(book);
//        System.out.println("\"" + book.getTitle() + "\" has been removed from the library.");
    }

    // Remove a user
    public void removeUser(User user) {
        users.remove(user);
//        System.out.println(user.getName() + " has been removed from the system.");
    }

    // Count available books by an author
    public int countAvailableBooksByAuthor(String author) {
        int count = 0;
        for (Book book : books) {
            if (book.getAuthor().equalsIgnoreCase(author) && book.isAvailable()) {
                count++;
            }
        }
        return count;
    }

    // Find users who borrowed a specific book
    public List<User> findUsersByBookTitle(String title) {
        Book book = searchBookByTitle(title);
        if (book == null) {
            return Collections.emptyList();
        }

        List<User> result = new ArrayList<>();
        for (User user : users) {
            if (user.getBorrowedBooks().contains(book)) {
                result.add(user);
            }
        }
        return result;
    }


}
