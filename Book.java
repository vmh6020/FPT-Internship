public class Book {
    private String id;
    private String title;
    private String author;
    private int year; 
    private int quantity;

    Book(String id, String title, String author, int year, int quantity) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.quantity = quantity;
    }

    String getTitle() {
        return title;
    }
    String getAuthor() {
        return author;
    }
    int getYear() {
        return year;
    }
    int getQuantity() {
        return quantity;
    }
    String getId() {
        return id;
    }
    
    void setId(String id) {
        this.id = id;
    }
    void setTitle(String title) {
        this.title = title;
    }
    void setAuthor(String author) {
        this.author = author;
    }
    void setYear(int year) {
        this.year = year;
    }
    void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
    