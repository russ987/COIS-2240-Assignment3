import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Member> members = new ArrayList<>();
    private List<Book> books = new ArrayList<>();

    // Add a new member to the library
    public void addMember(Member member) {
        members.add(member);
    }

    // Add a new book to the library
    public void addBook(Book book) {
        books.add(book);
    }

    // Get the list of members
    public List<Member> getMembers() {
        return members;
    }

    // Get the list of books
    public List<Book> getBooks() {
        return books;
    }

    // Save library data to a file
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

    // Load library data from a file
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
            System.out.println("Error loading library data: " + e.getMessage());
        }
    }
}
