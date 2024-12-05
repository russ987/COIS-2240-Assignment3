import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

class LibraryManagementTest {

    private Transaction transaction;
    private Book book;
    private Member member;

    @BeforeEach
    void setUp() {
        transaction = Transaction.getTransaction(); // Retrieve Singleton instance
        book = new Book(101, "Test Book");          // Initialize book
        member = new Member(1001, "John Doe");      // Initialize member
    }

    @Test
    void testBorrowReturn() {
        // Ensure the book is initially available
        assertTrue(book.isAvailable(), "Book should initially be available");

        // Borrow the book
        boolean borrow = transaction.borrowBook(book, member);
        assertTrue(borrow, "borrowing Borrowing should be successful");
        assertFalse(book.isAvailable(), "Book should no longer be available after borrowing");

        boolean borrowTwo = transaction.borrowBook(book, member);
        assertFalse(borrowTwo, "Should pass");
        
        boolean returnBook = transaction.returnBook(book, member);
        assertTrue(returnBook, "return should work");
        
        boolean returnTwo = transaction.returnBook(book, member);
        assertFalse(returnTwo, "returning same book should not work");
        
    
    }
    @Test
	public void testSingletonTransaction() throws Exception {
	Constructor<Transaction> constructor = Transaction.class.getDeclaredConstructor();
	
	int modifiers = constructor.getModifiers();
	assertTrue(Modifier.isPrivate(modifiers), "Constructor should be private");
	
	constructor.setAccessible(true);
	
	Transaction newInt = constructor.newInstance();
	
	Transaction intOne = Transaction.getTransaction();
	Transaction intTwo = Transaction.getTransaction(); 
	assertSame(intOne, intTwo, "Should enforce singleton behaviour");
	assertNotSame(intOne, newInt, " other instance should not affect singleton one");

}
}