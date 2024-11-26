package IntegrationTests;
import org.junit.jupiter.api.Test;
import org.example.Book;
import org.example.User;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
public class UserIntTest {
    @Test
    void testBorrowBook_Success() {
        // Arrange
        Book book = new Book(101, "Clean Code", "Robert C. Martin");
        User user = new User(1, "Alice");

        // Act
        user.borrowBook(book);

        // Assert
        assertFalse(book.isAvailable(), "Book should not be available after being borrowed.");
        assertTrue(user.getBorrowedBooks().contains(book), "Borrowed book should be in the user's borrowed books list.");
    }

    @Test
    void testBorrowBook_Failure_BookAlreadyBorrowed() {
        // Arrange
        Book book = new Book(102, "Design Patterns", "Gang of Four");
        book.setAvailable(false); // Simulate the book already borrowed
        User user = new User(2, "Bob");

        // Act
        user.borrowBook(book);

        // Assert
        assertFalse(user.getBorrowedBooks().contains(book), "Book should not be added to the borrowed books list.");
        assertFalse(book.isAvailable(), "Book availability should remain false.");
    }

    @Test
    void testReturnBook_Success() {
        // Arrange
        Book book = new Book(103, "Refactoring", "Martin Fowler");
        User user = new User(3, "Charlie");
        user.borrowBook(book);

        // Act
        user.returnBook(book);

        // Assert
        assertTrue(book.isAvailable(), "Book should be available after being returned.");
        assertFalse(user.getBorrowedBooks().contains(book), "Book should no longer be in the user's borrowed books list.");
    }

    @Test
    void testReturnBook_Failure_BookNotBorrowed() {
        // Arrange
        Book book = new Book(104, "The Pragmatic Programmer", "Andrew Hunt");
        User user = new User(4, "Daisy");

        // Act
        user.returnBook(book);

        // Assert
        assertTrue(book.isAvailable(), "Book availability should remain true.");
        assertFalse(user.getBorrowedBooks().contains(book), "Book should not be in the user's borrowed books list.");
    }

    @Test
    void testDisplayBorrowedBooks() {
        // Arrange
        Book book1 = new Book(105, "Effective Java", "Joshua Bloch");
        Book book2 = new Book(106, "Java Concurrency in Practice", "Brian Goetz");
        User user = new User(5, "Eve");

        user.borrowBook(book1);
        user.borrowBook(book2);

        // Act
        user.displayBorrowedBooks();

        // Assert
        List<Book> borrowedBooks = user.getBorrowedBooks();
        assertEquals(2, borrowedBooks.size(), "User should have 2 borrowed books.");
        assertTrue(borrowedBooks.contains(book1), "Borrowed books should contain book1.");
        assertTrue(borrowedBooks.contains(book2), "Borrowed books should contain book2.");
    }
}
