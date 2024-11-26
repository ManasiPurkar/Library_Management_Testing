package org.example;

import java.util.*;

public class LibraryManagementSystem {
    private Library library;
    private Scanner scanner;

    public LibraryManagementSystem() {
        this.library = new Library();
        this.scanner = new Scanner(System.in);
    }
    public LibraryManagementSystem(Library library, Scanner scanner) {
        this.library = library;
        this.scanner = scanner;
    }
    // Add a new book to the library
    public void addNewBook() {
        System.out.print("Enter Book ID: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.print("Enter Book Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Book Author: ");
        String author = scanner.nextLine();
        // Validation to check for empty title or author
        if (title.isEmpty() || author.isEmpty()) {
            System.out.println("Book title and author cannot be empty.");
            return; // Do not add the book if validation fails
        }
        Book book = new Book(bookId, title, author);
        library.addBook(book);
    }

    // Add a new user to the system
    public void addNewUser() {
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.print("Enter User Name: ");
        String name = scanner.nextLine();

        User user = new User(userId, name);
        library.addUser(user);
    }

    // Borrow a book for a user
    public void borrowBook() {
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.print("Enter Book Title to Borrow: ");
        String title = scanner.nextLine();

        User user = library.getUsers().stream().filter(u -> u.getUserId() == userId).findFirst().orElse(null);
        if (user != null) {
            Book book = library.searchBookByTitle(title);
            if (book != null) {
                user.borrowBook(book);
            }
        } else {
            System.out.println("User not found.");
        }
    }

    // Return a borrowed book
    public void returnBook() {
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.print("Enter Book Title to Return: ");
        String title = scanner.nextLine();

        User user = library.getUsers().stream().filter(u -> u.getUserId() == userId).findFirst().orElse(null);
        if (user != null) {
            Book book = library.searchBookByTitle(title);
            if (book != null) {
                user.returnBook(book);
            }
            else {
                System.out.println("Book not found.");
            }
        } else {
            System.out.println("User not found.");
        }
    }

    // Display all books and users
    public void displayAllBooksAndUsers() {
        library.displayAllBooks();
        library.displayUsers();
    }

    public void findBooksByAuthor() {
        System.out.print("Enter Author Name: ");
        String author = scanner.nextLine();

        List<Book> booksByAuthor = library.searchBooksByAuthor(author);

        if (booksByAuthor.isEmpty()) {
            System.out.println("No books found by author: " + author);
        } else {
            System.out.println("Books by " + author + ":");
            for (Book book : booksByAuthor) {
                String availability = book.isAvailable() ? "Available" : "Not Available";
                System.out.println("Book ID: " + book.getBookId() + ", Title: " + book.getTitle() + ", Availability: " + availability);
            }
        }
        int availableBooksCount = library.countAvailableBooksByAuthor(author);

        if (availableBooksCount == 0) {
            System.out.println("No available books found by author: " + author);
        } else {
            System.out.println("Number of available books by " + author + ": " + availableBooksCount);
        }
    }

    public void findUsersWhoBorrowedBook() {
        System.out.print("Enter Book Title: ");
        String title = scanner.nextLine();

        List<User> users = library.findUsersByBookTitle(title);

        if (users.isEmpty()) {
            System.out.println("No users have borrowed the book titled: " + title);
        } else {
            System.out.println("Users who borrowed the book titled \"" + title + "\":");
            for (User user : users) {
                System.out.println("User ID: " + user.getUserId() + ", Name: " + user.getName());
            }
        }
    }

    // Run the system
    public void run() {
        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add a new book");
            System.out.println("2. Add a new user");
            System.out.println("3. Borrow a book");
            System.out.println("4. Return a book");
            System.out.println("5. Display all books and users");
            System.out.println("6. Find books by author");
            System.out.println("7. Find users who borrowed a book");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (option) {
                case 1:
                    addNewBook();
                    break;
                case 2:
                    addNewUser();
                    break;
                case 3:
                    borrowBook();
                    break;
                case 4:
                    returnBook();
                    break;
                case 5:
                    displayAllBooksAndUsers();
                    break;
                case 6:
                    findBooksByAuthor();
                    break;
                case 7:
                    findUsersWhoBorrowedBook();
                    break;
                case 8:
                    System.out.println("Exiting the system...");
                    return;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }
}
