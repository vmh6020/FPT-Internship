package com.simplelibrary;


public class BorrowRecord { 
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

    void increaseQuantity(int amount) {
        this.quantity += amount;
    }
    void decreaseQuantity(int amount) {
        if (this.quantity > amount) this.quantity -= amount;
        else this.quantity = 0;
    }
}
