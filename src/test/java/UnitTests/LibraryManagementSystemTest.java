package UnitTests;

import org.example.Book;
import org.example.Library;
import org.example.LibraryManagementSystem;
import org.example.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import java.util.Scanner;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class LibraryManagementSystemTest {

    @Mock
    private Library libraryMock;

    @Mock
    private Scanner scannerMock;

    @InjectMocks
    private LibraryManagementSystem libraryManagementSystem;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks and inject them into LibraryManagementSystem
    }

    @Test
    void testAddNewBook() {
        // Set up mock Scanner input
        when(scannerMock.nextInt()).thenReturn(101);
        when(scannerMock.nextLine())
                .thenReturn("")  // Consume newline
                .thenReturn("Book Title") // Title input
                .thenReturn("Author Name"); // Author input

        libraryManagementSystem.addNewBook();

        // Verify the book was added
        verify(libraryMock).addBook(argThat(book ->
                book.getBookId() == 101 &&
                        book.getTitle().equals("Book Title") &&
                        book.getAuthor().equals("Author Name")
        ));

    }

    @Test
    void testAddNewBook_EmptyTitle() {
        when(scannerMock.nextInt()).thenReturn(105); // Book ID
        when(scannerMock.nextLine()).thenReturn("")  // Consume newline
                .thenReturn("") // Empty Title
                .thenReturn("Valid Author"); // Non-empty Author

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        // Call the method under test
        libraryManagementSystem.addNewBook();

        // Assert that the output contains the expected message
        assertTrue(output.toString().contains("Book title and author cannot be empty."));

        // Verify the book was not added
        verify(libraryMock, never()).addBook(any(Book.class));
    }


    @Test
    void testAddNewBook_EmptyAuthor() {
        when(scannerMock.nextInt()).thenReturn(104); // Book ID
        when(scannerMock.nextLine()).thenReturn("")  // Consume newline
                .thenReturn("Valid Title") // Non-empty Title
                .thenReturn(""); // Empty Author

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        // Call the method under test
        libraryManagementSystem.addNewBook();

        // Assert that the output contains the expected message
        assertTrue(output.toString().contains("Book title and author cannot be empty."));

        // Verify the book was not added
        verify(libraryMock, never()).addBook(any(Book.class));
    }

    @Test
    void testAddNewUser_EmptyName() {
        when(scannerMock.nextInt()).thenReturn(301); // User ID
        when(scannerMock.nextLine()).thenReturn(""); // Empty Name

        libraryManagementSystem.addNewUser();

        assertEquals(0, libraryMock.getUsers().size(), "No user should be added with an empty name.");
    }

    @Test
    void testAddNewUser() {
        // Set up mock Scanner input
        when(scannerMock.nextInt()).thenReturn(201);
        when(scannerMock.nextLine())
                .thenReturn("")  // Consume newline
                .thenReturn("User Name"); // Name input

        libraryManagementSystem.addNewUser();

        // Verify the user was added
        verify(libraryMock).addUser(argThat(user ->
                user.getUserId() == 201 &&
                        user.getName().equals("User Name")
        ));
    }


    @Test
    void testAddNewUser_InvalidInput() {
        when(scannerMock.nextInt()).thenReturn(-1); // Invalid User ID
        when(scannerMock.nextLine()).thenReturn(""); // Empty Name

        libraryManagementSystem.addNewUser();

        assertEquals(0, libraryMock.getUsers().size(), "No user should be added for invalid input.");
    }

    @Test
    void testBorrowBook_UserFound() {
        when(scannerMock.nextInt()).thenReturn(1); // Valid User ID
        when(scannerMock.nextLine()).thenReturn("Some Book Title");

        // Mock a matching user in the library
        User user1 = new User(1, "Alice");
        User user2 = new User(2, "Bob");
        when(libraryMock.getUsers()).thenReturn(Arrays.asList(user1, user2)); // User with ID 1 exists

        Book mockBook = new Book(101, "Some Book Title", "Some Author");
        when(libraryMock.searchBookByTitle("Some Book Title")).thenReturn(mockBook);

        libraryManagementSystem.borrowBook();

        // Verify the behavior: user1 should attempt to borrow the book
        verify(libraryMock, times(1)).getUsers();
        assertEquals(1, user1.getUserId());
    }

    @Test
    void testBorrowBook_UserNotFound() {
        when(scannerMock.nextInt()).thenReturn(99); // Non-existent User ID
        when(scannerMock.nextLine()).thenReturn("Some Book Title");

        // Mock no matching users in the library
        User user1 = new User(1, "Alice");
        User user2 = new User(2, "Bob");
        when(libraryMock.getUsers()).thenReturn(Arrays.asList(user1, user2)); // No user with ID 99

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        libraryManagementSystem.borrowBook();

        // Assert the expected message
        assertTrue(output.toString().contains("User not found."));

        // Verify the behavior
        verify(libraryMock, times(1)).getUsers();
    }


    @Test
    void testBorrowBook_UserAndBookFound() {
        User user = new User(201, "User Name");
        Book book = new Book(101, "Book Title", "Author Name");

        when(scannerMock.nextInt()).thenReturn(201);
        when(scannerMock.nextLine())
                .thenReturn("")  // Consume newline
                .thenReturn("Book Title"); // Title input

        when(libraryMock.getUsers()).thenReturn(Collections.singletonList(user));
        when(libraryMock.searchBookByTitle("Book Title")).thenReturn(book);

        libraryManagementSystem.borrowBook();

        verify(libraryMock).searchBookByTitle("Book Title");
        assertTrue(user.getBorrowedBooks().contains(book));
    }

    @Test
    void testBorrowBook_BookDoesNotExist() {
        User mockUser = new User(1, "John Doe");
        when(scannerMock.nextInt()).thenReturn(1); // Valid User ID
        when(scannerMock.nextLine()).thenReturn("Non-existent Book Title"); // Non-existent book

        when(libraryMock.getUsers()).thenReturn(Collections.singletonList(mockUser)); // Valid user
        when(libraryMock.searchBookByTitle("Non-existent Book Title")).thenReturn(null); // Book not found

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        libraryManagementSystem.borrowBook();

        // Assert that there is no output for a successful borrow (no success message)
        assertFalse(output.toString().contains("Borrowed"));

        // Verify interactions
        verify(libraryMock, times(1)).getUsers();
        verify(libraryMock, times(1)).searchBookByTitle("Non-existent Book Title");
    }


    @Test
    void testReturnBook_UserNotFound() {
        // Mock input for User ID and Book Title
        when(scannerMock.nextInt()).thenReturn(1); // User ID
        when(scannerMock.nextLine()).thenReturn("Nonexistent Book"); // Book title

        // Mock empty user list
        when(libraryMock.getUsers()).thenReturn(Collections.emptyList()); // No users

        // Call the method
        libraryManagementSystem.returnBook();

        // Verify `searchBookByTitle` is never called because no user was found
        verify(libraryMock, never()).searchBookByTitle(anyString());
        verify(libraryMock, times(1)).getUsers(); // Ensure `getUsers` is called once
    }


    @Test
    void testReturnBook_BookNotFound() {
        // Mock input for User ID and Book Title
        when(scannerMock.nextInt()).thenReturn(1); // User ID
        when(scannerMock.nextLine()).thenReturn("Nonexistent Book"); // Book title

        // Mock a valid user list
        User mockUser = mock(User.class);
        when(mockUser.getUserId()).thenReturn(1);
        when(libraryMock.getUsers()).thenReturn(Collections.singletonList(mockUser));

        // Mock book not found
        when(libraryMock.searchBookByTitle("Nonexistent Book")).thenReturn(null);

        // Call the method
        libraryManagementSystem.returnBook();

        // Verify the interactions
        verify(libraryMock, times(1)).searchBookByTitle("Nonexistent Book"); // Book searched
        verify(mockUser, never()).returnBook(any(Book.class)); // No book returned
    }

    @Test
    void testBorrowBook_NullBook() {
        // Arrange
        // Mock input for User ID and Book Title
        when(scannerMock.nextInt()).thenReturn(1); // User ID
        when(scannerMock.nextLine()).thenReturn("").thenReturn("Null Book"); // Book title

        // Mock a valid user
        User mockUser = mock(User.class);
        when(mockUser.getUserId()).thenReturn(1);
        when(libraryMock.getUsers()).thenReturn(Collections.singletonList(mockUser));

        // Mock null book returned from searchBookByTitle
        when(libraryMock.searchBookByTitle("Null Book")).thenReturn(null);

        // Capture console output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Act
        libraryManagementSystem.borrowBook();

        // Assert
        // Verify the interactions
        verify(libraryMock, times(1)).searchBookByTitle("Null Book");
        verify(mockUser, never()).borrowBook(any(Book.class));

        // Verify output
        String output = outputStream.toString();
        assertFalse(output.contains("User not found."), "User should be found, so this message should not appear.");
        // Since book is null, no specific message is printed in the implementation.
        // Verify indirectly via interactions.
    }

    @Test
    void testReturnBook_SuccessfulReturn() {
        // Mock input for User ID and Book Title
        when(scannerMock.nextInt()).thenReturn(1); // User ID
        when(scannerMock.nextLine()).thenReturn("Valid Book"); // Book title

        // Mock a valid user
        User mockUser = mock(User.class);
        when(mockUser.getUserId()).thenReturn(1);
        when(libraryMock.getUsers()).thenReturn(Collections.singletonList(mockUser));

        // Mock a valid book
        Book mockBook = mock(Book.class);
        when(libraryMock.searchBookByTitle("Valid Book")).thenReturn(mockBook);

        // Call the method
        libraryManagementSystem.returnBook();

        // Verify the interactions
        verify(libraryMock, times(1)).searchBookByTitle("Valid Book"); // Book searched
        verify(mockUser, times(1)).returnBook(mockBook); // Book returned
    }
    /*
    @Test
    void testReturnBook_BookNotInLibrary() {
        // Mock input for User ID and Book Title
        when(scannerMock.nextInt()).thenReturn(1); // User ID
        when(scannerMock.nextLine()).thenReturn("Unknown Book"); // Book title

        // Mock a valid user
        User mockUser = mock(User.class);
        when(mockUser.getUserId()).thenReturn(1);
        when(libraryMock.getUsers()).thenReturn(Collections.singletonList(mockUser));

        // Mock book not found in library
        when(libraryMock.searchBookByTitle("Unknown Book")).thenReturn(null);

        // Call the method
        libraryManagementSystem.returnBook();

        // Verify the interactions
        verify(libraryMock, times(1)).searchBookByTitle("Unknown Book"); // Book searched
        verify(mockUser, never()).returnBook(any(Book.class)); // No book returned
    }
    */
    @Test
    void testReturnBook_UserListEmpty() {
        // Mock input for User ID and Book Title
        when(scannerMock.nextInt()).thenReturn(1); // User ID
        when(scannerMock.nextLine()).thenReturn("Any Book"); // Book title

        // Mock an empty user list
        when(libraryMock.getUsers()).thenReturn(Collections.emptyList()); // No users

        // Call the method
        libraryManagementSystem.returnBook();

        // Verify no interaction with searchBookByTitle
        verify(libraryMock, never()).searchBookByTitle(anyString());
    }

    /*
    @Test
    void testReturnBook_NullBook() {
        // Mock input for User ID and Book Title
        when(scannerMock.nextInt()).thenReturn(1); // User ID
        when(scannerMock.nextLine()).thenReturn("Null Book"); // Book title

        // Mock a valid user
        User mockUser = mock(User.class);
        when(mockUser.getUserId()).thenReturn(1);
        when(libraryMock.getUsers()).thenReturn(Collections.singletonList(mockUser));

        // Mock null book returned from searchBookByTitle
        when(libraryMock.searchBookByTitle("Null Book")).thenReturn(null);

        // Call the method
        libraryManagementSystem.returnBook();

        // Verify the interactions
        verify(libraryMock, times(1)).searchBookByTitle("Null Book");
        verify(mockUser, never()).returnBook(any(Book.class));
    }

     */

    @Test
    void testReturnBook_NullBook() {
        // Arrange
        // Mock input for User ID and Book Title
        when(scannerMock.nextInt()).thenReturn(1); // User ID
        when(scannerMock.nextLine()).thenReturn("").thenReturn("Null Book"); // Book title

        // Mock a valid user
        User mockUser = mock(User.class);
        when(mockUser.getUserId()).thenReturn(1);
        when(libraryMock.getUsers()).thenReturn(Collections.singletonList(mockUser));

        // Mock null book returned from searchBookByTitle
        when(libraryMock.searchBookByTitle("Null Book")).thenReturn(null);

        // Capture console output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Act
        libraryManagementSystem.returnBook();

        // Assert
        // Verify the interactions
        verify(libraryMock, times(1)).searchBookByTitle("Null Book");
        verify(mockUser, never()).returnBook(any(Book.class));

        // Verify output
        String output = outputStream.toString();
        assertTrue(output.contains("Book not found."), "Expected 'Book not found.' message.");
    }


    @Test
    void testReturnBook_MultipleUsers_CorrectUserFound() {
        // Arrange
        User user1 = new User(1, "Alice");
        User user2 = new User(2, "Bob");
        Book book = new Book(101, "Test Book", "Author");
        user2.borrowBook(book); // Second user borrows the book
        when(libraryMock.getUsers()).thenReturn(Arrays.asList(user1, user2));
        when(libraryMock.searchBookByTitle("Test Book")).thenReturn(book);

        // Mock inputs
        when(scannerMock.nextInt()).thenReturn(2); // User ID
        when(scannerMock.nextLine()).thenReturn("").thenReturn("Test Book"); // Book title

        // Act
        libraryManagementSystem.returnBook();

        // Assert
        assertTrue(user2.getBorrowedBooks().isEmpty(), "The book should be returned for the correct user.");
        assertFalse(user1.getBorrowedBooks().contains(book), "The first user's borrowed books should remain unchanged.");
    }


    @Test
    void testDisplayAllBooksAndUsers() {
        Book book1 = new Book(101, "Book 1", "Author 1");
        Book book2 = new Book(102, "Book 2", "Author 2");
        User user1 = new User(201, "User 1");
        User user2 = new User(202, "User 2");

        when(libraryMock.getBooks()).thenReturn(Arrays.asList(book1, book2));
        when(libraryMock.getUsers()).thenReturn(Arrays.asList(user1, user2));

        libraryManagementSystem.displayAllBooksAndUsers();

        verify(libraryMock).displayAllBooks();
        verify(libraryMock).displayUsers();
    }

    @Test
    void testExit() {
        // Simulate exit choice
        when(scannerMock.nextInt()).thenReturn(8);
        when(scannerMock.nextLine()).thenReturn(""); // Consume newline

        assertDoesNotThrow(() -> libraryManagementSystem.run());
    }


    @Test
    public void testFindBooksByAuthor_NoBooksFound() {
        // Mocking behavior for when no books are found by the author
        when(libraryMock.searchBooksByAuthor("Author Z")).thenReturn(Collections.emptyList());
        when(libraryMock.countAvailableBooksByAuthor("Author Z")).thenReturn(0);

        // Simulate user input for author name
        ByteArrayInputStream input = new ByteArrayInputStream("Author Z\n".getBytes());
        System.setIn(input);

        // Capture the system output (console output)
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        // Call the method under test
        libraryManagementSystem.findBooksByAuthor();


        // Assert that the output contains the expected messages
        assertTrue(output.toString().contains("No books found by author:"));
        assertTrue(output.toString().contains("No available books found by author:"));
    }

    @Test
    public void testFindUsersWhoBorrowedBook_NoUsers() {
        // Create a mock book instance
        Book book = new Book(1, "Book A", "Author X");

        // Mock the behavior for when the book is searched
        when(libraryMock.searchBookByTitle("Book A")).thenReturn(book);

        // Mock the behavior for when no users have borrowed the book
        when(libraryMock.findUsersByBookTitle("Book A")).thenReturn(Collections.emptyList());

        // Simulate user input for the book title
        ByteArrayInputStream input = new ByteArrayInputStream("Book A\n".getBytes());
        System.setIn(input);

        // Capture the system output (console output)
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        // Call the method under test
        libraryManagementSystem.findUsersWhoBorrowedBook();


        // Assert that the output contains the expected messages
        assertTrue(output.toString().contains("Enter Book Title:"));
        assertTrue(output.toString().contains("No users have borrowed the book titled:"));
    }


    @Test
    void testFindUsersWhoBorrowedBook_ElsePart() {
        // Setup Library and Scanner
        Library library = new Library();
        Scanner scanner = new Scanner("1984\n"); // Simulated input for the book title

        // Add a book to the library
        Book book = new Book(1, "1984", "George Orwell");
        library.addBook(book);

        // Add a user and have them borrow the book
        User user = new User(1, "Alice");
        library.addUser(user);
        user.borrowBook(book);

        // Redirect system output to capture the print statements
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Call the function under test
        LibraryManagementSystem system = new LibraryManagementSystem(library, scanner);
        system.findUsersWhoBorrowedBook();

        // Validate the output
        String output = outputStream.toString();
        assertTrue(output.contains("Users who borrowed the book titled \"1984\":"),
                "The output should indicate the users who borrowed the book.");
        assertTrue(output.contains("User ID: 1, Name: Alice"),
                "The output should display the correct user who borrowed the book.");
    }



}
