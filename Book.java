
public class Book {
    private int id;
    private String title;
    private boolean available;
    public Book(int id, String title) {
        if (id < 100 || id > 999) {
            throw new IllegalArgumentException("Book ID must be between 100 and 999");
        }
        this.id = id;
        this.title = title;
        this.available = true; // Default to available
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isAvailable() {
        return available;
    }
    
    public void setAvailable(boolean available) {
    	this.available = available;
    }

    // Method to borrow the book
    public void borrowBook() {
        if (available) {
            available = false;
        }
    }

    // Method to return the book
    public void returnBook() {
        available = true;
    }

    // Method to check if a book id is valid
    public boolean isValidId(int id) {
        return id >= 100 && id <= 999;
    }
}