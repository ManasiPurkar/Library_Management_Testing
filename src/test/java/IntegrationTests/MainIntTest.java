package IntegrationTests;

import org.example.LibraryManagementSystem;
import org.example.Main;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.example.Book;
import org.example.Library;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class MainIntTest {
    private InputStream originalInput;
    private PrintStream originalOutput;

    @BeforeEach
    void setUp() {
        originalInput = System.in; // Backup original System.in
        originalOutput = System.out; // Backup original System.out
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalInput); // Restore System.in
        System.setOut(originalOutput); // Restore System.out
    }

    @Test
    void testMain_AddNewBookOption() {
        String simulatedInput = "1\n101\nBook Title\nAuthor Name\n8\n"; // Add book and exit
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        // Capture system output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Run Main class
        Main.main(new String[]{});

        // Validate output
        String output = outputStream.toString();
        assertTrue(output.contains("Library Management System"), "Main menu should be displayed.");
        assertTrue(output.contains("1. Add a new book"), "Add book option should be displayed.");
        assertTrue(output.contains("Enter Book ID:"));
        assertTrue(output.contains("Enter Book Title:"));
        assertTrue(output.contains("Enter Book Author:"));
        assertTrue(output.contains("Exiting the system..."), "Exit message should be displayed.");
    }

    @Test
    void testMain_AddNewUserOption() {
        String simulatedInput = "2\n201\nJohn Doe\n8\n"; // Add user and exit
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        // Capture system output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Run Main class
        Main.main(new String[]{});

        // Validate output
        String output = outputStream.toString();
        assertTrue(output.contains("Library Management System"), "Main menu should be displayed.");
        assertTrue(output.contains("2. Add a new user"), "Add user option should be displayed.");
        assertTrue(output.contains("Exiting the system..."), "Exit message should be displayed.");
    }

    @Test
    void testMain_DisplayAllBooksAndUsersOption() {
        String simulatedInput = "5\n8\n"; // Display all books and users, then exit
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        // Capture system output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Run Main class
        Main.main(new String[]{});

        // Validate output
        String output = outputStream.toString();
        assertTrue(output.contains("Library Management System"), "Main menu should be displayed.");
        assertTrue(output.contains("5. Display all books and users"), "Display option should be displayed.");
        assertTrue(output.contains("Library Books:"));
        assertTrue(output.contains("Library Users:"));
        assertTrue(output.contains("Exiting the system..."), "Exit message should be displayed.");
    }

    @Test
    void testMain_ExitOption() {
        String simulatedInput = "8\n"; // Exit
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        // Capture system output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Run Main class
        Main.main(new String[]{});

        // Validate output
        String output = outputStream.toString();
        assertTrue(output.contains("Exiting the system..."), "Exit message should be displayed.");
    }

    // case for borrow operation
    @Test
    void testMain_BorrowBookOption() {
        String simulatedInput = "3\n201\nBook Title\n8\n"; // Borrow a book and exit
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        // Capture system output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Run Main class
        Main.main(new String[]{});

        // Validate output
        String output = outputStream.toString();
        assertTrue(output.contains("3. Borrow a book"), "Borrow book option should be displayed.");
        assertTrue(output.contains("Enter User ID:"));
        assertTrue(output.contains("Exiting the system..."), "Exit message should be displayed.");
    }

    @Test
    void testMain_ReturnBookOption() {
        String simulatedInput = "4\n201\nBook Title\n8\n"; // Return a book and exit
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        // Capture system output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Run Main class
        Main.main(new String[]{});

        // Validate output
        String output = outputStream.toString();

        assertTrue(output.contains("4. Return a book"), "Return book option should be displayed.");
        assertTrue(output.contains("Enter User ID:"));
        assertTrue(output.contains("Exiting the system..."), "Exit message should be displayed.");
    }

    @Test
    void testMain_FindBooksByAuthorOption() {
        String simulatedInput = "6\nGeorge Orwell\n8\n"; // Find books by "George Orwell" and exit
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        // Capture system output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Run Main class
        Main.main(new String[]{});

        // Validate output
        String output = outputStream.toString();
        assertTrue(output.contains("6. Find books by author"), "Find books by author option should be displayed.");
        assertTrue(output.contains("Enter Author Name:"));
        assertTrue(output.contains("Exiting the system..."), "Exit message should be displayed.");
    }

    @Test
    void testMain_FindUsersWhoBorrowedBookOption() {
        String simulatedInput = "7\n1984\n8\n"; // Find users who borrowed "1984" and exit
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        // Capture system output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Run Main class
        Main.main(new String[]{});

        // Validate output
        String output = outputStream.toString();
        assertTrue(output.contains("7. Find users who borrowed a book"), "Find users who borrowed a book option should be displayed.");
        assertTrue(output.contains("No users have borrowed the book titled:") ||
                        output.contains("Users who borrowed the book titled"),
                "Should display either no users or list of users who borrowed the book.");
        assertTrue(output.contains("Exiting the system..."), "Exit message should be displayed.");
    }


}
