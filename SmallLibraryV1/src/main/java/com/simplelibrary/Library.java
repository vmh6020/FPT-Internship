package com.simplelibrary;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Library {
    private final List<User> users = new ArrayList<>();
    private final List<Book> books = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    //add and remove book from library
    public List<User> getUsers() {
        return users;
    }
    public List<Book> getBooks() {
        return books;
    }
    // check String co phai so hay khong
    void addBook() {
        String id;

        while (true) {
            System.out.print("Nhap ID: ");
            id = sc.next().trim();
            final String idtemp = id;

            // Kiểm tra ID có phải số không
            if (!id.matches("\\d+")) {
                System.out.println(" Input must be a number, please try again!");
                continue;
            }

            boolean exist =  books.stream().anyMatch(b -> b.getId().equals(idtemp));

            if (exist) {
                System.out.println(" Exist book with the same ID, please try again!");
            } else {
                break; 
            }
        }

        sc.nextLine(); // clear buffer

        System.out.print("Title: ");
        String title = sc.nextLine().trim();
        System.out.print("Author: ");
        String author = sc.nextLine().trim();

        int year;
        while (true) {
            System.out.print("Year: ");
            String yearStr = sc.nextLine().trim();
            if (!yearStr.matches("\\d+")) {
                System.out.println(" Year must be a number!");
            } else {
                year = Integer.parseInt(yearStr);
                break;
            }
        }

        int quantity;
        while (true) {
            System.out.print("Quantity: ");
            String qStr = sc.nextLine().trim();
            if (!qStr.matches("\\d+")) {
                System.out.println(" Quantity must be a number!");
            } else {
                quantity = Integer.parseInt(qStr);
                break;
            }
        }

        books.add(new Book(id, title, author, year, quantity));
        System.out.println("Book added successfully!");
    }

    void removeBook() {
        System.out.print("Enter the id of the book: ");
        
        String temp;
        while (true) {
            temp = sc.next().trim();
            if (BookById(temp) == null) {
                System.out.println("Book with given id does not exists");
            } else break;
        }

        String id = temp;
        int c;
        do {
            System.out.print("""
                0. Thoat
                1. Xoa toan bo
                2. Xoa theo so luong
                Nhập lựa chọn của bạn:
                """);
            c = sc.nextInt();
            switch (c){
                case 1 -> {
                    boolean removed = books.removeIf(b -> b.getId().equals(id));
                    System.out.println(removed ? "Đã xóa sách" : "Không tìm thấy sách");
                }
                case 2 -> {
                    System.out.println("Nhap so luong sach ban muon xoa: ");
                    int q = sc.nextInt();
                    boolean found = false;
                    for (Book book : books) {
                        if (id.equals(book.getId())) {
                            found = true;
                            if (q > book.getQuantity()) {
                                System.out.println("Quá số lượng cho phép.");
                            } else {
                                book.setQuantity(book.getQuantity()-q);
                                if (book.getQuantity() == 0) {
                                    books.remove(book);
                                }
                        }
                        break;
                            
                    }
                }
                    if (!found) System.out.println("Khong tim duoc sach voi id nay");
                }
                default -> System.out.println("Khong co lua chon ton tai, thu lai");
            }
        } while (c != 0);
    }
    //show all book informations from library
    void showBooks(List<Book> list) {
        if (list.isEmpty()) {
        System.out.println("❌ No books found!");
        return;
        }
        for (Book book : list) {
            System.out.println("-----------");
            System.out.printf("Id: %s\nTitle: %s\nAuthor: %s\nYear: %d\nQuantity: %d\n", book.getId(), book.getTitle(), book.getAuthor(), book.getYear(), book.getQuantity());
            System.out.println("-----------");
        }
    }

    //find book by book's informations
    List<Book> findBook() {
        System.out.print("""
                Bạn muốn tìm sách theo thông tin nào: 
                1. ID
                2. Author
                3. Title
                4. Year
                5. Quantity
                """);
        int choice = sc.nextInt();
        sc.nextLine(); // clear buffer

        Predicate<Book> condition;

        switch (choice) {
            case 1 -> {
                System.out.print("Nhập ID: ");
                String id = sc.nextLine().trim();
                condition = b -> b.getId().equalsIgnoreCase(id);
            }
            case 2 -> {
                System.out.print("Nhập Author: ");
                String author = sc.nextLine().trim();
                condition = b -> b.getAuthor().equalsIgnoreCase(author);
            }
            case 3 -> {
                System.out.print("Nhập Title: ");
                String title = sc.nextLine().trim();
                condition = b -> b.getTitle().equalsIgnoreCase(title);
            }
            case 4 -> {
                int year;
                while (true) {
                    System.out.print("Nhập Year: ");
                    String input = sc.nextLine().trim();
                    if (!input.matches("\\d+")) {
                        System.out.println("Year must be a number");
                    } else {
                        year = Integer.parseInt(input);
                        break;
                    }
                }
                int finalYear = year;
                condition = b -> (b.getYear() == finalYear);
            }
            case 5 -> {
                int quantity;
                while (true) {
                    System.out.print("Nhập Quantity: ");
                    String input = sc.nextLine().trim();
                    if (!input.matches("\\d+")) {
                        System.out.println("Quantity must be a number");
                    } else {
                        quantity = Integer.parseInt(input);
                        break;
                    }
                }
                int finalQuantity = quantity;
                condition = b -> (b.getQuantity() == finalQuantity);
            }
            default -> {
                System.out.println("Lựa chọn không hợp lệ!");
                return List.of();
            }
        }

        return books.stream()
                .filter(condition)
                .toList();
    }

    //update book's information
    void updateBookInfo() {
    System.out.print("Enter the ID of the book you want to update: ");
    String id = sc.next().trim();

    // Tìm sách theo ID
    Book bookToUpdate = BookById(id);

    if (bookToUpdate == null) {
        System.out.println(" Book with ID " + id + " not found!");
        return;
    }

    sc.nextLine(); 

    String newId;
    while (true) {
    System.out.print("New ID: ");
    newId = sc.nextLine().trim();
    final String idToCheck = newId; 
    if (newId.isEmpty()) {
        System.out.println(" ID cannot be empty!");
    } else if (!newId.equalsIgnoreCase(id) &&
               books.stream().anyMatch(b -> b.getId().equalsIgnoreCase(idToCheck))) {
        System.out.println(" ID already exists! Please enter another one.");
    } else {
        break;
    }
}


    // Nhập Title
    System.out.print("New TITLE: ");
    String newTitle = sc.nextLine().trim();

    // Nhập Author
    System.out.print("New AUTHOR: ");
    String newAuthor = sc.nextLine().trim();

    // Nhập Year (chỉ chấp nhận số và nằm trong khoảng hợp lý)
    int newYear;
    while (true) {
        System.out.print("New YEAR: ");
        String yearStr = sc.nextLine().trim();
        if (!yearStr.matches("\\d+")) {
            System.out.println(" Year must be a number!");
            continue;
        }
        newYear = Integer.parseInt(yearStr);
        if (newYear < 1000 || newYear > 2100) {
            System.out.println(" Year must be between 1000 and 2100!");
        } else break;
    }

    // Nhập Quantity (chỉ chấp nhận số >= 0)
    int newQuantity;
    while (true) {
        System.out.print("New QUANTITY: ");
        String qStr = sc.nextLine().trim();
        if (!qStr.matches("\\d+")) {
            System.out.println(" Quantity must be a number!");
            continue;
        }
        newQuantity = Integer.parseInt(qStr);
        if (newQuantity < 0) {
            System.out.println(" Quantity cannot be negative!");
        } else break;
    }

    // Cập nhật thông tin sách
    bookToUpdate.setId(newId);
    bookToUpdate.setTitle(newTitle);
    bookToUpdate.setAuthor(newAuthor);
    bookToUpdate.setYear(newYear);
    bookToUpdate.setQuantity(newQuantity);

    System.out.println("Update Complete!");
}

    void addUser() {
        // ID
        String userId;
        while (true) {
        System.out.print("ID: ");
        userId = sc.nextLine().trim();

        final String idToCheck = userId;
        if (userId.isEmpty()) {
            System.out.println(" ID cannot be empty!");
        } else if (users.stream().anyMatch(u -> u.getUserId().equalsIgnoreCase(idToCheck))) {
            System.out.println(" ID already exists! Please enter another one.");
        } else {
            break;
        }
        }

        // CCCD (12 số)
        String cccd;
        while (true) {
            System.out.print("CCCD: ");
            cccd = sc.nextLine().trim();
            if (!cccd.matches("\\d{12}")) {
                System.out.println(" CCCD must be exactly 12 digits!");
            } else {
                break;
            }
        }

        // Name
        String name;
        while (true) {
            System.out.print("NAME: ");
            name = sc.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println(" Name cannot be empty!");
            } else {
                break;
            }
        }

        // Birthday (format dd/MM/yyyy)
        String birthday;
        while (true) {
            System.out.print("BIRTHDAY (dd/MM/yyyy): ");
            birthday = sc.nextLine().trim();
            if (!birthday.matches("\\d{2}/\\d{2}/\\d{4}")) {
                System.out.println("Birthday must be in format dd/MM/yyyy!");
                continue;
            }
            try {
                java.time.LocalDate.parse(birthday,
                        java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                break;
            } catch (Exception e) {
                System.out.println("Invalid date, please try again!");
            }
        }

        // Address
        String userAddress;
        while (true) {
            System.out.print("ADDRESS: ");
            userAddress = sc.nextLine().trim();
            if (userAddress.isEmpty()) {
                System.out.println("Address cannot be empty!");
            } else {
                break;
            }
        }

        // Tạo user mới
        User user = new User(userId, cccd, name, birthday, userAddress);
        users.add(user);

        System.out.println("User added successfully!");
    }

    // void removeUser(User user) {
    //     users.remove(user);
    // }
    void getUserInfo() {
        if (users.isEmpty()) {
            System.out.println(" No users found in the system.");
            return;
        }

        System.out.println("=========== USER INFO ===========");

        for (User user : users) {
            System.out.println("---------------------------------");
            System.out.printf("Username: %s | ID: %s | CCCD: %s | Birthday: %s | Address: %s\n",
                    user.getUserName(),
                    user.getUserId(),
                    user.getCccd(),
                    user.getUserBirthdate(),
                    user.getUserAddress());

            System.out.println("Borrowed Books:");
            if (user.getBorrowedBooks().isEmpty()) {
                System.out.println("  None");
            } else {
                for (BorrowRecord record : user.getBorrowedBooks()) {
                    System.out.println("  - " + record); // dùng toString() của BorrowRecord
                }
            }
        }

        System.out.println("=================================");
    }

    void userBorrowBook() {
        System.out.println("Enter userID: ");
        String userId = sc.next();
        System.out.println("Enter bookID: ");
        String bookId = sc.next();
        System.out.println("Enter quantity to borrow: ");
        int quantity = sc.nextInt();

        if (quantity <= 0) {
            System.out.println(" Quantity must be positive!");
            return;
        }

        User user = UserById(userId);
        Book book = BookById(bookId);

        if (user == null) {
            System.out.println(" User with ID " + userId + " does not exist!");
            return;
        }
        if (book == null) {
            System.out.println(" Book with ID " + bookId + " does not exist!");
            return;
        }

        if (book.getQuantity() < quantity) {
            System.out.println(" Not enough books in storage! Available: " + book.getQuantity());
            return;
        }

        user.borrowBook(book, quantity); // gọi hàm ban đầu của User
        book.setQuantity(book.getQuantity() - quantity);

        System.out.println(user.getUserName() + " borrowed " + quantity + " of \"" + book.getTitle() + "\"");
    }

    void userReturnBook() {
        System.out.println("Enter userID: ");
        String userId = sc.next();
        System.out.println("Enter bookID: ");
        String bookId = sc.next();
        System.out.println("Enter quantity to return: ");
        int quantity = sc.nextInt();

        if (quantity <= 0) {
            System.out.println(" Quantity must be positive!");
            return;
        }

        User user = UserById(userId);
        Book book = BookById(bookId);

        if (user == null) {
            System.out.println(" User with ID " + userId + " does not exist!");
            return;
        }
        if (book == null) {
            System.out.println(" Book with ID " + bookId + " does not exist!");
            return;
        }

        int borrowedQuantity = user.getBorrowedQuantity(book); // User cần có hàm này
        if (borrowedQuantity < quantity) {
            System.out.println(" You are trying to return more than borrowed! Borrowed: " + borrowedQuantity);
            return;
        }

        user.returnBook(book, quantity); // gọi hàm ban đầu của User
        book.setQuantity(book.getQuantity() + quantity);

        System.out.println(user.getUserName() + " returned " + quantity + " of \"" + book.getTitle() + "\"");
    }

    User UserById(String id) {
        return users.stream().filter(u -> u.getUserId().equals(id)).findFirst().orElse(null);
    }
    Book BookById(String id) {
        return books.stream().filter(b -> b.getId().equals(id)).findFirst().orElse(null);
    }

    ArrayList<String> readFile(String FILEPATH) {
        ArrayList<String> data = new ArrayList<>();
        try (Stream<String> lines = Files.lines(Path.of(FILEPATH))) {
            lines.map(String::trim).filter(line -> !line.isEmpty()).forEach(data::add);
        } catch (IOException e) {
            System.out.println("");
        }
        return data;
    }

    void addBookData(ArrayList<String> data) {
        for (String s : data) {
            String[] p = s.split(",");
            String id = p[0];
            String title = p[1];
            String author = p[2];
            String yearStr = p[3];
            int year = Integer.parseInt(yearStr);
            String quantityStr = p[4];
            int quantity = Integer.parseInt(quantityStr);

            Book book = new Book(id, title, author, year, quantity);
            books.add(book);
        }
    }
    void addUserData(ArrayList<String> data) {
        for (String s : data) {
            String[] p = s.split(",");
            String id = p[0];
            String cccd = p[1];
            String name = p[2];
            String birthday = p[3];
            String address = p[4];

            User user = new User(id, cccd, name, birthday, address);
            users.add(user);
        }
    }
    void addBorrowRecord(ArrayList<String> data) {
        for (String s : data) {
            String[] p = s.split(",");
            UserById(p[0]).getBorrowedBooks().add(new BorrowRecord(BookById(p[1]),Integer.parseInt(p[2])));
        }
    }

    ArrayList<String> storeBookData() {
        ArrayList<String> data = new ArrayList<>();
        for (Book book : books) {
            ArrayList<String> d = new ArrayList<>();
            d.add(book.getId());
            d.add(book.getTitle());
            d.add(book.getAuthor());
            d.add(String.valueOf(book.getYear()));
            d.add(String.valueOf(book.getQuantity()));
            data.add(String.join(",",d));
        }
        return data;
    }
    ArrayList<String> storeUserData() {
        ArrayList<String> data = new ArrayList<>();
        for (User user : users) {
            List<String> d = new ArrayList<>();
            d.add(user.getUserId());
            d.add(user.getCccd());
            d.add(user.getUserName());
            d.add(user.getUserBirthdate());
            d.add(user.getUserAddress());
            data.add(String.join(",",d));
        }
        return data;
    }
    ArrayList<String> storeBorrowBook() {
        ArrayList<String> data = new ArrayList<>();
        for (User user : users) {
            for (BorrowRecord br:  user.getBorrowedBooks()) {
                ArrayList<String> d = new ArrayList<>();
                d.add(user.getUserId());
                d.add(br.getBook().getId());
                d.add(String.valueOf(br.getQuantity()));
                data.add(String.join(",",d));
            }
        }
        return data;
    }

    void writeFile(String FILEPATH, ArrayList<String> data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILEPATH))){
            for (String s : data) {
                bw.write(s);
                bw.newLine();
            }
            
        } catch (IOException e) {
            System.out.println("An error occur");
        }
    }

    void getData() {
        addBookData(readFile("./records/books.txt"));
        addUserData(readFile("./records/users.txt"));
        addBorrowRecord(readFile("./records/borrow_records.txt"));
    }
    void storeData() {
        writeFile("./records/books.txt",storeBookData());
        writeFile("./records/users.txt",storeUserData());
        writeFile("./records/borrow_records.txt",storeBorrowBook());
    }

}