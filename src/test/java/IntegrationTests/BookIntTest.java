package IntegrationTests;
import org.example.Book;
import org.example.Main;
import org.example.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
public class BookIntTest {
    private Book book;
    private User user;

    @BeforeEach
    void setUp() {
        book = new Book(1, "Effective Java", "Joshua Bloch");
        user = new User(101, "Alice");
    }

    @Test
    void testBorrowBookIntegration() {
        // User borrows a book
        user.borrowBook(book);

        // Check the book's availability status
        assertFalse(book.isAvailable(), "Book should not be available after being borrowed.");

        // Check that the user has the book in their borrowed list
        assertTrue(user.getBorrowedBooks().contains(book), "User's borrowed books should include this book.");
    }

    @Test
    void testReturnBookIntegration() {
        // User borrows and then returns a book
        user.borrowBook(book);
        user.returnBook(book);

        // Check the book's availability status
        assertTrue(book.isAvailable(), "Book should be available after being returned.");
        // Check that the user's borrowed books list does not contain the book
        assertFalse(user.getBorrowedBooks().contains(book), "User's borrowed books should not include this book after return.");
    }

    @Test
    void testDisplayBookDetailsIntegration() {
     
        book.displayBook();
   
        assertEquals(1, book.getBookId());
        assertEquals("Effective Java", book.getTitle());
        assertEquals("Joshua Bloch", book.getAuthor());
    }

    @Test
    void testBorrowUnavailableBookIntegration() {
        // User borrows the book
        user.borrowBook(book);

        // Another user tries to borrow the same book
        User anotherUser = new User(102, "Bob");
        anotherUser.borrowBook(book);

        // Verify the second user could not borrow the book
        assertFalse(anotherUser.getBorrowedBooks().contains(book), "Second user should not be able to borrow an unavailable book.");
        assertTrue(user.getBorrowedBooks().contains(book), "First user should still have the borrowed book.");
    }

}
