package com.simplelibrary;

import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        library.getData();
        try (Scanner sc = new Scanner(System.in)){
            int userDecision;
            do {
                System.out.print("""
                ================= LIBRARY MENU =================
                1. Thêm sách
                2. Xóa sách
                3. Cập nhật thông tin sách
                4. Tìm kiếm sách
                5. Hiển thị danh sách sách
                6. Thêm người dùng
                7. Hiển thị danh sách người dùng + sách đã mượn
                8. Mượn sách
                9. Trả sách
                0. Thoát (lưu dữ liệu ra file)
                ===============================================
                Nhap lua chon:
                """);
                userDecision = sc.nextInt();
                switch (userDecision) {
                    case 0 -> library.storeData();
                    case 1 -> library.addBook();
                    case 2 -> library.removeBook();
                    case 3 -> library.updateBookInfo();
                    case 4 -> library.showBooks(library.findBook());
                    case 5 -> library.showBooks(library.getBooks());
                    case 6 -> library.addUser();
                    case 7 -> library.getUserInfo();
                    case 8 -> library.userBorrowBook();
                    case 9 -> library.userReturnBook();
                    default -> System.out.println("Invalid choice, please try again!");
                }
            } while (userDecision != 0);
        }
        
    }
}
