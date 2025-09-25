package com.simplelibrary;

import java.util.ArrayList;

public class User {
    private final String id;
    private final String cccd;
    private final String name;
    private final String birthdate;
    private final String address;
    private final ArrayList<BorrowRecord> borrowedBooks;
    
    User(String id, String cccd, String name, String birthdate, String address) {
        this.id = id;
        this.cccd = cccd;
        this.name = name;
        this.birthdate = birthdate;
        this.address = address;
        this.borrowedBooks = new ArrayList<>();
    }
    
    String getUserId() {
        return id;
    }
    String getCccd() {
        return cccd;
    }
    String getUserName() {
        return name;
    }
    String getUserBirthdate() {
        return birthdate;
    }
    String getUserAddress() {
        return address;
    }
    ArrayList<BorrowRecord> getBorrowedBooks() {
        return borrowedBooks;
    }

    // void setUserId(String id) {
    //     this.id = id;
    // }
    // void setUserCccd(String cccd) {
    //     this.cccd = cccd;
    // }
    // void setUserName(String name) {
    //     this.name = name;
    // }
    // void setUserBirthdate(String birthdate) {
    //     this.birthdate = birthdate;
    // }
    // void setUserAddress(String address) {
    //     this.address = address;
    // }
    // void setBorrowedBooks(ArrayList<BorrowRecord> bb) {
    //     this.borrowedBooks = bb;
    // }

    void borrowBook(Book book, int quantity) {
        if (quantity <= 0) {
            System.out.println(" Quantity must be positive!");
            return;
        }

        // Tìm record nếu đã mượn
        BorrowRecord record = borrowedBooks.stream()
                .filter(r -> r.getBook().equals(book))
                .findFirst()
                .orElse(null);

        if (record != null) {
            record.increaseQuantity(quantity);
        } else {
            borrowedBooks.add(new BorrowRecord(book, quantity));
        }
    }

    void returnBook(Book book, int quantity) {
        if (quantity <= 0) {
            System.out.println(" Quantity must be positive!");
            return;
        }

        BorrowRecord record = borrowedBooks.stream()
                .filter(r -> r.getBook().equals(book))
                .findFirst()
                .orElse(null);

        if (record == null) {
            System.out.println(" You have not borrowed this book!");
            return;
        }

        if (record.getQuantity() > quantity) {
            record.decreaseQuantity(quantity);
        } else if (record.getQuantity() == quantity) {
            borrowedBooks.remove(record);
        } else {
            System.out.println(" You are trying to return more than borrowed! Borrowed: " + record.getQuantity());
        }
    }
    int getBorrowedQuantity(Book book) {
        BorrowRecord record = borrowedBooks.stream()
                .filter(r -> r.getBook().equals(book))
                .findFirst()
                .orElse(null);
        return record != null ? record.getQuantity() : 0;
    }
}