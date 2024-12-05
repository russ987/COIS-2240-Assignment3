
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Member> members = new ArrayList<>();
    private List<Book> books = new ArrayList<>();

    // Add a new member to the Library
    public boolean addMember(Member member) {
        if (findMemberById(member.getId()) != null) {
            System.out.println("Member with ID " + member.getId() + " already exists.");
            return false; // Addition failed
        }
        members.add(member); // Add member if ID is unique
        System.out.println("Member added successfully.");
        return true; // Addition successful
    }

    // Find a member by ID
    public Member findMemberById(int id) {
        for (Member member : members) {
            if (member.getId() == id) {
                return member; // Return the member if the ID matches
            }
        }
        return null; // No member found with this ID
    }

    // Add a new book to the Library
    public boolean addBook(Book book) {
        if (findBookById(book.getId()) != null) {
            System.out.println("Book with ID " + book.getId() + " already exists.");
            return false; // Addition failed
        }
        books.add(book); // Add book if ID is unique
        System.out.println("Book added successfully.");
        return true; // Addition successful
    }

    // Find a book by ID
    public Book findBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book; // Return the book if the ID matches
            }
        }
        return null; // No book found with this ID
    }

    // Get the list of members
    public List<Member> getMembers() {
        return members;
    }

    // Get the list of books
    public List<Book> getBooks() {
        return books;
    }

    // Borrow a book for a member
    public boolean borrowBook(Book book, Member member) {
        if (book.isAvailable()) {
            book.borrowBook(); // Mark the book as borrowed
            member.borrowBook(book); // Add the book to the member's borrowed list
            Transaction.getTransaction().borrowBook(book, member); // Use the Singleton instance
            return true;
        } else {
            System.out.println("The book is not available.");
            return false;
        }
    }

    // Return a borrowed book
    public boolean returnBook(Book book, Member member) {
        if (member.returnBook(book)) {
            book.returnBook(); // Mark the book as returned
            Transaction.getTransaction().returnBook(book, member); // Use the Singleton instance
            return true;
        } else {
            System.out.println("This book was not borrowed by the member.");
            return false;
        }
    }

<<<<<<< Updated upstream
    // Save library data to a file
=======
    // Save Library data to a file
>>>>>>> Stashed changes
    public void saveLibraryData(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            // Save members
            writer.write("Members:\n");
            for (Member member : members) {
                writer.write(member.getId() + "," + member.getName() + "\n");
            }

            // Save books
            writer.write("Books:\n");
            for (Book book : books) {
                writer.write(book.getId() + "," + book.getTitle() + "," + book.isAvailable() + "\n");
            }

            System.out.println("Library data saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving library data: " + e.getMessage());
        }
    }

    // Load Library data from a file
    public void loadLibraryData(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean isMembersSection = false;
            boolean isBooksSection = false;

            while ((line = reader.readLine()) != null) {
                if (line.equals("Members:")) {
                    isMembersSection = true;
                    isBooksSection = false;
                } else if (line.equals("Books:")) {
                    isMembersSection = false;
                    isBooksSection = true;
                } else if (isMembersSection) {
                    String[] memberData = line.split(",");
                    addMember(new Member(Integer.parseInt(memberData[0]), memberData[1]));
                } else if (isBooksSection) {
                    String[] bookData = line.split(",");
                    Book book = new Book(Integer.parseInt(bookData[0]), bookData[1]);
                    if (!Boolean.parseBoolean(bookData[2])) {
                        book.borrowBook(); // Mark as borrowed
                    }
                    addBook(book);
                }
            }

            System.out.println("Library data loaded from " + filename);
        } catch (IOException e) {
            System.out.println("Error loading Library data: " + e.getMessage());
        }
    }

    // View transaction history (e.g., borrowed/returned books)
    public void viewTransactionHistory() {
        Transaction.getTransaction().displayTransactionHistory(); // Use Singleton to view history
    }
}
