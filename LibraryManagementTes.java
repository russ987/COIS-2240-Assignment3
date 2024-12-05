import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

class LibraryManagementTes {

    private Transaction transaction;
    private Book book;
    private Member member;

    @BeforeEach
    void setUp() {
        // Initialize the singleton Transaction instance
        transaction = Transaction.getTransaction();
        
        // Initialize Book and Member instances
        book = new Book(101, "Test Book");
        member = new Member(1001, "John Doe");
    }
    @Test
    void testBorrowReturn() {
        // Ensure the book is initially available
        assertTrue(book.isAvailable(), "Book should initially be available");

        // Borrow the book
        boolean borrow = transaction.borrowBook(book, member);
        assertTrue(borrow, "Borrowing should be successful");
        assertFalse(book.isAvailable(), "Book should no longer be available after borrowing");

        // Try borrowing the book again
        boolean borrowTwo = transaction.borrowBook(book, member);
        assertFalse(borrowTwo, "Borrowing the same book again should fail");

        // Return the book
        boolean returnBook = transaction.returnBook(book, member);
        assertTrue(returnBook, "Returning the book should be successful");
        assertTrue(book.isAvailable(), "Book should be available after returning");

        // Try returning the book again
        boolean returnTwo = transaction.returnBook(book, member);
        assertFalse(returnTwo, "Returning the same book again should fail"); 
    }


    @Test
    public void testSingletonTransaction() throws Exception {
        // Get the constructor of the Transaction class
        Constructor<Transaction> constructor = Transaction.class.getDeclaredConstructor();

        // Get the modifiers of the constructor
        int modifiers = constructor.getModifiers();
        assertTrue(Modifier.isPrivate(modifiers), "Constructor should be private");

        // Set the constructor accessible to break the private modifier
        constructor.setAccessible(true);

        // Create a new instance using reflection
        Transaction newInstance = constructor.newInstance();

        // Retrieve the singleton instances of Transaction
        Transaction firstInstance = Transaction.getTransaction();
        Transaction secondInstance = Transaction.getTransaction();

        // Assert that the instances are the same (Singleton behavior)
        assertSame(firstInstance, secondInstance, "Should enforce singleton behavior");

        // Assert that the new instance created via reflection is not the same as the singleton instance
        assertNotSame(firstInstance, newInstance, "New instance should not be the singleton");
    }
}
