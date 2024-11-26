package IntegrationTests;
import org.example.Book;
import org.example.Library;
import org.example.LibraryManagementSystem;
import org.example.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class LibraryManagmentIntTest {
    private Library library;
    private LibraryManagementSystem librarySystem;
    private Scanner mockScanner;

    @BeforeEach
    void setUp() {
        library = new Library();
        mockScanner = mock(Scanner.class);
        librarySystem = new LibraryManagementSystem(library, mockScanner);
    }

    @Test
    void testAddNewBook() {
        // Mock user inputs for adding a new book
        when(mockScanner.nextInt()).thenReturn(1); // Book ID
        when(mockScanner.nextLine())
                .thenReturn("")  // Consume newline left by nextInt()
                .thenReturn("Effective Java") // Title
                .thenReturn("Joshua Bloch");  // Author

        librarySystem.addNewBook();

        // Verify the book was added to the library
        assertEquals(1, library.getBooks().size(), "Library should have one book.");
        Book addedBook = library.getBooks().get(0);
        assertNotNull(addedBook, "Book should be added to the library.");
        assertEquals("Effective Java", addedBook.getTitle(), "Book title should match.");
        assertEquals("Joshua Bloch", addedBook.getAuthor(), "Book author should match.");
    }


    @Test
    void testAddNewUser() {
        // Mock user inputs for adding a new user
        when(mockScanner.nextInt()).thenReturn(101); // User ID
        when(mockScanner.nextLine()).thenReturn("Alice"); // User Name

        librarySystem.addNewUser();

        // Verify the user was added to the library
        User addedUser = library.getUsers().get(0);
        assertNotNull(addedUser, "User should be added to the library.");
        assertEquals(101, addedUser.getUserId(), "User ID should match.");
        assertEquals("Alice", addedUser.getName(), "User name should match.");
    }

    @Test
    void testBorrowBook() {
        // Setup initial data
        Book book = new Book(1, "Clean Code", "Robert C. Martin");
        User user = new User(101, "Bob");
        library.addBook(book);
        library.addUser(user);

        // Mock user inputs for borrowing a book
        when(mockScanner.nextInt()).thenReturn(101); // User ID
        when(mockScanner.nextLine()).thenReturn("Clean Code"); // Book title

        librarySystem.borrowBook();

        // Verify the book's availability and user's borrowed list
        assertFalse(book.isAvailable(), "Book should be marked as unavailable.");
        assertTrue(user.getBorrowedBooks().contains(book), "User's borrowed list should include the book.");
    }

    @Test
    void testReturnBook() {
        // Setup initial data
        Book book = new Book(1, "Clean Code", "Robert C. Martin");
        User user = new User(101, "Bob");
        library.addBook(book);
        library.addUser(user);
        user.borrowBook(book);

        // Mock user inputs for returning a book
        when(mockScanner.nextInt()).thenReturn(101); // User ID
        when(mockScanner.nextLine()).thenReturn("Clean Code"); // Book title

        librarySystem.returnBook();

        // Verify the book's availability and user's borrowed list
        assertTrue(book.isAvailable(), "Book should be marked as available.");
        assertFalse(user.getBorrowedBooks().contains(book), "User's borrowed list should not include the book.");
    }

    @Test
    void testDisplayAllBooksAndUsers() {
        // Setup initial data
        Book book1 = new Book(1, "Effective Java", "Joshua Bloch");
        Book book2 = new Book(2, "Clean Code", "Robert C. Martin");
        User user1 = new User(101, "Alice");
        User user2 = new User(102, "Bob");

        library.addBook(book1);
        library.addBook(book2);
        library.addUser(user1);
        library.addUser(user2);

        // Capture system output
        librarySystem.displayAllBooksAndUsers();

        // Verify books and users are displayed
        assertEquals(2, library.getBooks().size(), "Library should display all books.");
        assertEquals(2, library.getUsers().size(), "Library should display all users.");
    }

    @Test
    void testRun_ExitOption() {
        // Mock inputs for exiting the system
        when(mockScanner.nextInt()).thenReturn(8); // Exit option
        when(mockScanner.nextLine()).thenReturn(""); // Consume newline

        // Run the system and ensure it exits without errors
        assertDoesNotThrow(() -> librarySystem.run(), "System should exit without throwing errors.");
    }
}
