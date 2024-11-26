package UnitTests;

import org.example.Book;
import org.example.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User user;
    private Book availableBook;
    private Book unavailableBook;

    @BeforeEach
    void setUp() {
        // Create test user
        user = new User(1, "John");

        // Create test books
        availableBook = mock(Book.class);
        unavailableBook = mock(Book.class);

        // Set up the available and unavailable book mock behaviors
        when(availableBook.isAvailable()).thenReturn(true);
        when(unavailableBook.isAvailable()).thenReturn(false);
    }

    @Test
    void testBorrowBook_Successful() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        // Borrow an available book
        user.borrowBook(availableBook);

        // Verify the book was borrowed
        verify(availableBook).setAvailable(false);
        assertTrue(user.getBorrowedBooks().contains(availableBook));
        String output = outputStream.toString();
        assertTrue(output.contains("successfully borrowed"));
    }

    @Test
    void testBorrowBook_Unavailable() {
        // Try borrowing an unavailable book
        user.borrowBook(unavailableBook);

        // Verify that the book's availability was not changed
        verify(unavailableBook, never()).setAvailable(false);
        assertFalse(user.getBorrowedBooks().contains(unavailableBook));
    }

    @Test
    void testBorrowBook_NullBook() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        // Arrange
        User user = new User(6, "Grace");

        // Act
        user.borrowBook(null);

        // Assert
        assertTrue(user.getBorrowedBooks().isEmpty(), "No books should be added when trying to borrow a null book.");
        String output = outputStream.toString();
        assertTrue(output.contains("Cannot borrow a null book."));
    }

    @Test
    void testBorrowBook_UnregisteredUser() {
        // Arrange
        Book book = new Book(108, "Ghost Book", "Phantom Author");
        User user = new User(9, "Jack");

        // Act
        user.borrowBook(book);

        // Assert
        assertEquals(1, user.getBorrowedBooks().size(), "Unregistered users should still be able to borrow books.");

    }

    @Test
    void testReturnBook_NullBook() {
        // Arrange
        User user = new User(7, "Helen");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        // Act
        user.returnBook(null);


        String output = outputStream.toString();
        assertTrue(output.contains("Cannot return a null book."));
        // Assert
        assertTrue(user.getBorrowedBooks().isEmpty(), "No books should be removed when trying to return a null book.");

    }

    @Test
    void testReturnBook_Successful() {
        // Borrow a book first
        user.borrowBook(availableBook);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        // Now return the book
        user.returnBook(availableBook);

        // Verify the book was returned
        verify(availableBook).setAvailable(true);
        assertFalse(user.getBorrowedBooks().contains(availableBook));
        String output = outputStream.toString();
        assertTrue(output.contains("successfully returned"));
    }

    @Test
    void testReturnBook_NotBorrowed() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        // Try returning a book that was never borrowed
        user.returnBook(availableBook);

        // Verify the book's availability is not changed
        verify(availableBook, never()).setAvailable(true);
        assertFalse(user.getBorrowedBooks().contains(availableBook));
        String output = outputStream.toString();
        assertTrue(output.contains("You haven't borrowed"));
    }



    @Test
    void testDisplayBorrowedBooks() {
        // Arrange: Prepare a user and some books
        User user = new User(1, "Test User");
        Book availableBook = new Book(101, "Available Book", "Author A");
        Book unavailableBook = new Book(102, "Unavailable Book", "Author B");
        unavailableBook.setAvailable(false); // Mark the book as unavailable

        // Borrow only the available book
        user.borrowBook(availableBook);
        user.borrowBook(unavailableBook); // This should not be added as it's unavailable

        // Redirect the system output to capture display output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Act: Call the method to display borrowed books
        user.displayBorrowedBooks();

        // Reset the system output to its original state
        System.setOut(System.out);

        // Assert: Verify the borrowed books and captured output
        assertEquals(1, user.getBorrowedBooks().size(), "Only one book should be borrowed");
        String output = outputStream.toString();
        assertTrue(output.contains("Book ID: 101"), "Output should contain the available book's details");
        assertTrue(output.contains("Title: Available Book"), "Output should contain the title of the borrowed book");
        assertFalse(output.contains("Book ID: 102"), "Output should not contain the unavailable book's details");
    }


    @Test
    void testDisplayBorrowedBooks_Empty() {
        // Make sure no books are borrowed at the start of the test
        assertTrue(user.getBorrowedBooks().isEmpty(), "User should have no borrowed books initially.");

        // Display borrowed books (which should be empty)
        user.displayBorrowedBooks();

        // Assert the list is still empty after displaying
        assertTrue(user.getBorrowedBooks().isEmpty(), "User's borrowed books list should still be empty.");
    }
}
