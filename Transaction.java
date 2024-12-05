import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Transaction {

    // Singleton instance
    private static Transaction instance = null;

    // List to store transaction history
    private List<String> transactionHistory;

    // Private constructor
    private Transaction() {
        transactionHistory = new ArrayList<>();
        loadTransactions(); // Load transactions from file on startup
    }

    // Get Singleton instance
    public static Transaction getTransaction() {
        if (instance == null) {
            instance = new Transaction();
        }
        return instance;
    }

    // Borrow a book
    public boolean borrowBook(Book book, Member member) {
        if (book.isAvailable()) {
            book.setAvailable(false);
            member.borrowBook(book);
            
            // Record the transaction with date/time
            String transactionDetails = getCurrentDateTime() + " - Borrowing: " + member.getName() + " borrowed " + book.getTitle();
            recordTransaction(transactionDetails); // Log the transaction
            return true; 
        } else {
            System.out.println("The book is already borrowed.");
            return false;
        }
    }

    // Return a book
    public boolean returnBook(Book book, Member member) {
        if (member.getBorrowedBooks().contains(book)) {
            book.setAvailable(true);
            member.returnBook(book); // Assuming you have returnBook in the Member class
            
            // Record the return transaction with date/time
            String transactionDetails = getCurrentDateTime() + " - Returning: " + member.getName() + " returned " + book.getTitle();
            recordTransaction(transactionDetails); // Log the transaction
            System.out.println(transactionDetails);
            return true; // Return true when the book is successfully returned
        } else {
            System.out.println("This book was not borrowed by the member.");
            return false; // Return false when the book was not borrowed
        }
    }
    
    // Validate member ID
    public void isValidId(Member member) {
        if (member.getId() >= 100 && member.getId() <= 999) {
            System.out.println("Member Id is valid");
        } else {
            System.out.println("Member Id is invalid. Must be between 100 and 999");
        }
    }

    // Save a single transaction to file
    private void saveTransaction(String transactionDetails) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transaction.txt", true))) {
            writer.write(transactionDetails);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
        }
    }

    // Record a transaction
    public void recordTransaction(String transactionDetails) {
        transactionHistory.add(transactionDetails); // Add to list in memory
        saveTransaction(transactionDetails); // Save to file
    }

    // Load transactions from file
    private void loadTransactions() {
        try (BufferedReader reader = new BufferedReader(new FileReader("transaction.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                transactionHistory.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error loading transactions: " + e.getMessage());
        }
    }

    // Get current date and time
    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
 
    // Display transaction history
    public void displayTransactionHistory() {
        System.out.println("==== Transaction History ====");
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
        System.out.println("=============================");
    }
}
