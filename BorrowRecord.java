
public class BorrowRecord { //Danh sach da muon cua user
    private final Book book;
    private int quantity;

    public BorrowRecord(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }

    Book getBook() {
        return book;
    }
    int getQuantity() {
        return quantity;
    }

    // void setBook(Book book) {
    //     this.book = book;
    // }
    // void setQuantity(int quantity) {
    //     this.quantity = quantity;
    // }


    void increaseQuantity(int amount) {
        this.quantity += amount;
    }
    void decreaseQuantity(int amount) {
        if (this.quantity > amount) this.quantity -= amount;
        else this.quantity = 0;
    }
    @Override
    public String toString() {
        return book.getTitle() + " - Số lượng: " + quantity;
    }
    
}
