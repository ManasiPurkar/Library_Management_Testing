package UnitTests;

import org.example.Book;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    @Test
    public void testBookConstructor() {
        // Create a book instance using the constructor
        Book book = new Book(1, "The Great Gatsby", "F. Scott Fitzgerald");

        // Validate initial values
        assertEquals(1, book.getBookId());
        assertEquals("The Great Gatsby", book.getTitle());
        assertEquals("F. Scott Fitzgerald", book.getAuthor());
        assertTrue(book.isAvailable());
    }

    @Test
    public void testSetAndGetBookId() {
        Book book = new Book(1, "The Great Gatsby", "F. Scott Fitzgerald");

        // Set a new book ID
        book.setBookId(2);

        // Validate the updated book ID
        assertEquals(2, book.getBookId());
    }

    @Test
    public void testSetAndGetTitle() {
        Book book = new Book(1, "The Great Gatsby", "F. Scott Fitzgerald");

        // Set a new title
        book.setTitle("1984");

        // Validate the updated title
        assertEquals("1984", book.getTitle());
    }

    @Test
    public void testSetAndGetAuthor() {
        Book book = new Book(1, "The Great Gatsby", "F. Scott Fitzgerald");

        // Set a new author
        book.setAuthor("George Orwell");

        // Validate the updated author
        assertEquals("George Orwell", book.getAuthor());
    }

    @Test
    public void testSetAndGetIsAvailable() {
        Book book = new Book(1, "The Great Gatsby", "F. Scott Fitzgerald");

        // Set availability to false
        book.setAvailable(false);

        // Validate the updated availability
        assertFalse(book.isAvailable());

        // Set availability back to true
        book.setAvailable(true);

        // Validate the updated availability
        assertTrue(book.isAvailable());
    }

    @Test
    public void testDisplayBook() {
        Book book = new Book(1, "The Great Gatsby", "F. Scott Fitzgerald");

        // Redirect console output to capture displayBook method output
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        // Call displayBook method
        book.displayBook();

        // Validate console output
        assertEquals("Book ID: 1, Title: The Great Gatsby, Author: F. Scott Fitzgerald, Available: Yes\n", outContent.toString());

        // Restore System.out
        System.setOut(System.out);
    }
}
