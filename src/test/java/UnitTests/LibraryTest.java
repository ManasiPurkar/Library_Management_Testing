package UnitTests;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Scanner;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.example.*;
import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {

    private Library library;
    private Book book1;
    private Book book2;
    private Book book3;
    private User user1;
    private User user2;

    @BeforeEach
    public void setUp() {
        library = new Library();

        // Create sample books and users
        book1 = new Book(1, "The Great Gatsby", "F. Scott Fitzgerald"); // Available
        book2 = new Book(2, "1984", "George Orwell"); // Available
        book3 = new Book(3, "Animal Farm", "George Orwell"); // Available

        user1 = new User(1, "Alice");
        user2 = new User(2, "Bob");

        // Add books and users to library
        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);

        library.addUser(user1);
        library.addUser(user2);
    }

    @Test
    public void testAddBook() {

        Book book4 = new Book(4, "Brave New World", "Aldous Huxley");
        Book book5 = new Book(5, "To Kill a Mockingbird", "Harper Lee");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        library.addBook(book4);
        library.addBook(book5);

        List<Book> books = library.getBooks();
        assertEquals(5, books.size());
        assertTrue(books.contains(book4));
        assertTrue(books.contains(book5));
        String output = outputStream.toString();
        assertTrue(output.contains(" has been added to the library."));

    }

    @Test
    public void testAddUser() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        library.addUser(user1);
        library.addUser(user2);

        List<User> users = library.getUsers();
        assertEquals(4, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
        String output = outputStream.toString();
        assertTrue(output.contains("has been added as a user."));
    }

    @Test
    public void testDisplayAllBooks() {
        library.addBook(book1);
        library.addBook(book2);

        // This test ensures the method does not throw exceptions
        library.displayAllBooks();
    }

    @Test
    public void testSearchBookByTitleFound() {
        library.addBook(book1);
        library.addBook(book2);

        Book foundBook = library.searchBookByTitle("1984");
        assertNotNull(foundBook);
        assertEquals(book2, foundBook);
    }

    @Test
    public void testSearchBookByTitleNotFound() {
        library.addBook(book1);
        Book foundBook = library.searchBookByTitle("Nonexistent Book");
        assertNull(foundBook);
    }

    @Test
    public void testSearchBooksByAuthor() {
        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);

        List<Book> booksByOrwell = library.searchBooksByAuthor("George Orwell");
        assertEquals(4, booksByOrwell.size());
        assertTrue(booksByOrwell.contains(book2));
        assertTrue(booksByOrwell.contains(book3));
    }

    @Test
    public void testDisplayUsers() {
        library.addUser(user1);
        library.addUser(user2);

        // This test ensures the method does not throw exceptions
        library.displayUsers();
    }

    // Test countAvailableBooksByAuthor method
    @Test
    public void testCountAvailableBooksByAuthor() {
        // Author F. Scott Fitzgerald
        int availableBooks = library.countAvailableBooksByAuthor("F. Scott Fitzgerald");
        assertEquals(1, availableBooks, "There should be 1 available book by F. Scott Fitzgerald.");

        // Author George Orwell
        int availableBooksGeorgeOrwell = library.countAvailableBooksByAuthor("George Orwell");
        assertEquals(2, availableBooksGeorgeOrwell, "There should be 2 available books by George Orwell.");

        // Borrow "1984" by George Orwell
        user1.borrowBook(book2);  // Alice borrows "1984" (making it unavailable)

        // Re-check availability after borrowing
        availableBooksGeorgeOrwell = library.countAvailableBooksByAuthor("George Orwell");
        assertEquals(1, availableBooksGeorgeOrwell, "After borrowing, there should be 1 available book by George Orwell.");

        // Non-existent author
        availableBooks = library.countAvailableBooksByAuthor("J.K. Rowling");
        assertEquals(0, availableBooks, "There should be 0 available books by J.K. Rowling.");
    }

    @Test
    public void testRemoveBook() {
        Book book4 = new Book(4, "Brave New World", "Aldous Huxley");
        Book book5 = new Book(5, "To Kill a Mockingbird", "Harper Lee");

        library.addBook(book4);
        library.addBook(book5);

        library.removeBook(book4);

        List<Book> books = library.getBooks();
        assertEquals(4, books.size());
        assertFalse(books.contains(book4));
    }

    @Test
    public void testRemoveUser() {
        User user3 = new User(3, "Charlie");
        User user4 = new User(4, "Diana");
        library.addUser(user3);
        library.addUser(user4);

        library.removeUser(user3);

        List<User> users = library.getUsers();
        assertEquals(3, users.size()); //user 1&2 already added before test case
        assertFalse(users.contains(user3));
    }

    // Test findUsersByBookTitle method
    @Test
    public void testFindUsersByBookTitle() {
        // Test for "The Great Gatsby" (not borrowed yet)
        List<User> users = library.findUsersByBookTitle("The Great Gatsby");
        assertEquals(0, users.size(), "No users should have borrowed The Great Gatsby.");

        // Test for "1984" (borrowed by Alice)
        user1.borrowBook(book2);  // Alice borrows "1984"
        users = library.findUsersByBookTitle("1984");
        assertEquals(1, users.size(), "There should be 1 user who borrowed 1984.");
        assertEquals("Alice", users.get(0).getName(), "The user who borrowed 1984 should be Alice.");

        // Test for "Animal Farm" (not borrowed by anyone)
        users = library.findUsersByBookTitle("Animal Farm");
        assertTrue(users.isEmpty(), "No users should have borrowed Animal Farm.");

        // Test for a non-existent book title
        users = library.findUsersByBookTitle("Nonexistent Book");
        assertTrue(users.isEmpty(), "No users should have borrowed a non-existent book.");
    }

    @Test
    void testFindBooksByAuthor_AllBooksUnavailable() {
        // Setup Library and Scanner
        Library library = new Library();
        Scanner scanner = new Scanner("George Orwell\n"); // Simulated input for the author name

        // Add books by the author and make them unavailable
        Book book1 = new Book(1, "1984", "George Orwell");
        Book book2 = new Book(2, "Animal Farm", "George Orwell");
        book1.setAvailable(false);
        book2.setAvailable(false);
        library.addBook(book1);
        library.addBook(book2);

        // Redirect system output to capture the print statements
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Call the function under test
        LibraryManagementSystem system = new LibraryManagementSystem(library, scanner);
        system.findBooksByAuthor();

        // Validate the output
        String output = outputStream.toString();
        assertTrue(output.contains("Books by George Orwell:"),
                "The output should list the books by the author.");
        assertTrue(output.contains("Book ID: 1, Title: 1984, Availability: Not Available"),
                "The output should correctly indicate the availability status of the books.");
        assertTrue(output.contains("Book ID: 2, Title: Animal Farm, Availability: Not Available"),
                "The output should correctly indicate the availability status of the books.");
        assertTrue(output.contains("No available books found by author: George Orwell"),
                "The output should indicate no available books for the given author.");
    }

    @Test
    void testFindBooksByAuthor_SomeBooksAvailable() {
        // Setup Library and Scanner
        Library library = new Library();
        Scanner scanner = new Scanner("George Orwell\n"); // Simulated input for the author name

        // Add books by the author with mixed availability
        Book book1 = new Book(1, "1984", "George Orwell");
        Book book2 = new Book(2, "Animal Farm", "George Orwell");
        book2.setAvailable(false); // One book unavailable
        library.addBook(book1);
        library.addBook(book2);

        // Redirect system output to capture the print statements
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Call the function under test
        LibraryManagementSystem system = new LibraryManagementSystem(library, scanner);
        system.findBooksByAuthor();

        // Validate the output
        String output = outputStream.toString();
        assertTrue(output.contains("Books by George Orwell:"),
                "The output should list the books by the author.");
        assertTrue(output.contains("Book ID: 1, Title: 1984, Availability: Available"),
                "The output should correctly indicate the availability status of the books.");
        assertTrue(output.contains("Book ID: 2, Title: Animal Farm, Availability: Not Available"),
                "The output should correctly indicate the availability status of the books.");
        assertTrue(output.contains("Number of available books by George Orwell: 1"),
                "The output should indicate the correct count of available books.");
    }

}
