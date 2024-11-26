package IntegrationTests;
import org.example.Book;
import org.example.Library;
import org.example.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryIntTest {

    private Library library;
    private Book book1, book2, book3;
    private User user1, user2;

    @BeforeEach
    void setUp() {
        library = new Library();

        // Sample books
        book1 = new Book(1, "Effective Java", "Joshua Bloch");
        book2 = new Book(2, "Clean Code", "Robert C. Martin");
        book3 = new Book(3, "Design Patterns", "Erich Gamma");

        // Sample users
        user1 = new User(101, "Alice");
        user2 = new User(102, "Bob");

        // Add books and users to the library
        library.addBook(book1);
        library.addBook(book2);
        library.addUser(user1);
        library.addUser(user2);
    }

    @Test
    void testAddBook() {
        Book newBook = new Book(4, "Refactoring", "Martin Fowler");
        library.addBook(newBook);

        // Verify the book was added
        assertTrue(library.getBooks().contains(newBook), "New book should be added to the library.");
    }

    @Test
    void testAddUser() {
        User newUser = new User(103, "Charlie");
        library.addUser(newUser);

        // Verify the user was added
        assertTrue(library.getUsers().contains(newUser), "New user should be added to the library.");
    }

    @Test
    void testDisplayAllBooks() {
        // Check initial books
        assertEquals(2, library.getBooks().size(), "Library should contain initial books.");
    }

    @Test
    void testSearchBookByTitle() {
        // Search for an existing book
        Book foundBook = library.searchBookByTitle("Effective Java");
        assertNotNull(foundBook, "Book should be found.");
        assertEquals("Joshua Bloch", foundBook.getAuthor(), "Author should match.");

        // Search for a non-existent book
        Book notFoundBook = library.searchBookByTitle("Unknown Book");
        assertNull(notFoundBook, "Book should not be found.");
    }

    @Test
    void testSearchBooksByAuthor() {
        // Add another book by the same author
        Book anotherBook = new Book(4, "More Effective Java", "Joshua Bloch");
        library.addBook(anotherBook);

        // Search for books by Joshua Bloch
        List<Book> booksByAuthor = library.searchBooksByAuthor("Joshua Bloch");
        assertEquals(2, booksByAuthor.size(), "Should find 2 books by Joshua Bloch.");
    }

    @Test
    void testRemoveBook() {
        // Remove an existing book
        library.removeBook(book1);
        assertFalse(library.getBooks().contains(book1), "Book should be removed from the library.");
    }

    @Test
    void testRemoveUser() {
        // Remove an existing user
        library.removeUser(user1);
        assertFalse(library.getUsers().contains(user1), "User should be removed from the library.");
    }

    @Test
    void testUserBorrowBook() {
        // User borrows a book
        user1.borrowBook(book1);

        // Verify the book's availability
        assertFalse(book1.isAvailable(), "Book should not be available after being borrowed.");

        // Verify the user's borrowed list
        assertTrue(user1.getBorrowedBooks().contains(book1), "User's borrowed list should include the book.");
    }

    @Test
    void testUserReturnBook() {
        // User borrows and returns a book
        user1.borrowBook(book1);
        user1.returnBook(book1);

        // Verify the book's availability
        assertTrue(book1.isAvailable(), "Book should be available after being returned.");

        // Verify the user's borrowed list
        assertFalse(user1.getBorrowedBooks().contains(book1), "User's borrowed list should not include the book.");
    }

    @Test
    void testDisplayUsers() {
        // Check initial users
        assertEquals(2, library.getUsers().size(), "Library should contain initial users.");
    }
}
