import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class Library {
    private final List<User> users = new ArrayList<>();
    private final ArrayList<Book> books = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    //add and remove book from library
    void addBook() {
        String id;
        boolean exist;

        while (true) {
            System.out.print("Nhập ID: ");
            id = sc.next().trim();

            // Kiểm tra ID có phải số không
            if (!id.matches("\\d+")) {
                System.out.println(" ID phải là số, xin thử lại!");
                continue;
            }

            // Kiểm tra ID đã tồn tại chưa
            exist = false; // reset mỗi vòng
            for (Book b : books) {
                if (b.getId().equals(id)) {
                    exist = true;
                    break; // tìm thấy ID trùng, thoát for
                }
            }

            if (exist) {
                System.out.println(" Sác với ID này đã tồn tại, xin thử lại!");
            } else {
                break; // ID hợp lệ, thoát while
            }
        }

        // Nhập thông tin khác của sách
        sc.nextLine(); // clear buffer
        System.out.print("Nhập tên sách: ");
        String title = sc.nextLine().trim();
        System.out.print("Nhập tác giả: ");
        String author = sc.nextLine().trim();

        int year;
        while (true) {
            System.out.print("Nhập năm: ");
            String yearStr = sc.nextLine().trim();
            if (!yearStr.matches("\\d+")) {
                System.out.println(" Năm phải dương!");
            } else {
                year = Integer.parseInt(yearStr);
                break;
            }
        }

        int quantity;
        while (true) {
            System.out.print("Số lượng: ");
            String qStr = sc.nextLine().trim();
            if (!qStr.matches("\\d+")) {
                System.out.println(" Số lượng phải dương!");
            } else {
                quantity = Integer.parseInt(qStr);
                break;
            }
        }

        books.add(new Book(id, title, author, year, quantity));
        System.out.println("Thêm sách thành công!");
    }

    
    void removeBook() {
        System.out.print("Nhập ID sách: ");
        
        String temp;
        while (true) {
            temp = sc.next().trim();
            if (BookById(temp) == null) {
                System.out.println("Sách với ID này không tồn tại");
            } else break;
        }

        String id = temp;
        int c;
        do {
            System.out.print("""
                0. Thoát
                1. Xóa toàn bộ
                2. Xóa theo số lượng
                Nhập lựa chọn của bạn:
                """);
            c = sc.nextInt();
            switch (c){
                case 1 -> {
                    boolean removed = books.removeIf(b -> b.getId().equals(id));

                    if (removed) {
                        System.out.println("Xóa thành công.");
                        System.out.println("----------------");
                    } else {
                        System.out.println("Sách không tồn tại!");
                    }
                    break;
                }
                case 2 -> {
                    System.out.println("Nhập số lượng muốn xóa: ");
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
                    if (!found) System.out.println("Sách không tồn tại");
                }
                default -> System.out.println("Lựa chọn không tồn tại, thử lại");
            }
        } while (c != 0);
    }

    //getter
    public List<User> getUsers() {
        return users;
    }
    public ArrayList<Book> getBooks() {
        return books;
    }

    //show all book informations from library
    void showBooks(List<Book> list) {
        if (list.isEmpty()) {
        System.out.println("Kho trống!");
        return;
        }
        for (Book book : list) {
            System.out.println("-----------");
            System.out.printf("ID: %s\nTên Sách: %s\nTác Giả: %s\nNăm: %d\nSố lượng: %d\n", book.getId(), book.getTitle(), book.getAuthor(), book.getYear(), book.getQuantity());
            System.out.println("-----------");
        }
    }

    //find book by book's informations
    List<Book> findBook() {
        System.out.print("""
                Bạn muốn tìm sách theo thông tin nào: 
                1. ID
                2. Tác Giả
                3. Tên Sách
                4. Năm
                5. Số Lượng
                Nhập lựa chọn:
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
                System.out.print("Nhập Tác Giả: ");
                String author = sc.nextLine().trim();
                condition = b -> b.getAuthor().equalsIgnoreCase(author);
            }
            case 3 -> {
                System.out.print("Nhập Tên Sách: ");
                String title = sc.nextLine().trim();
                condition = b -> b.getTitle().equalsIgnoreCase(title);
            }
            case 4 -> {
                int year;
                while (true) {
                    System.out.print("Nhập Năm: ");
                    String input = sc.nextLine().trim();
                    if (!input.matches("\\d+")) {
                        System.out.println("Năm phải là số!");
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
                    System.out.print("Nhập Số lượng: ");
                    String input = sc.nextLine().trim();
                    if (!input.matches("\\d+")) {
                        System.out.println("Số lượng phải là số");
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
    System.out.print("Nhập ID sách bạn muốn thay đổi thông tin: ");
    String id = sc.next().trim();

    // Tìm sách theo ID
    Book bookToUpdate = BookById(id);

    if (bookToUpdate == null) {
        System.out.println(" Sách với ID " + id + " không tồn tại!");
        return;
    }

    sc.nextLine(); // clear buffer sau next()

    // Nhập ID mới
    String newId;
    while (true) {
    System.out.print("ID mới: ");
    newId = sc.nextLine().trim();
    final String idToCheck = newId; // biến final để lambda dùng
    if (newId.isEmpty()) {
        System.out.println(" ID không được trống!");
    } else if (!newId.equalsIgnoreCase(id) &&
               books.stream().anyMatch(b -> b.getId().equalsIgnoreCase(idToCheck))) {
        System.out.println(" ID đã tồn tại. Xin dùng ID khác");
    } else {
        break;
    }
}


    // Nhập Title
    System.out.print("Tên mới: ");
    String newTitle = sc.nextLine().trim();

    // Nhập Author
    System.out.print("Tác giả mới: ");
    String newAuthor = sc.nextLine().trim();

    // Nhập Year (chỉ chấp nhận số và nằm trong khoảng hợp lý)
    int newYear;
    while (true) {
        System.out.print("Năm mới: ");
        String yearStr = sc.nextLine().trim();
        if (!yearStr.matches("\\d+")) {
            System.out.println(" Năm phải là số!");
            continue;
        }
        newYear = Integer.parseInt(yearStr);
        if (newYear < 1000 || newYear > 2100) {
            System.out.println(" Năm phải nằm trong khoảng từ 1000 đến 2100");
        } else break;
    }

    // Nhập Quantity (chỉ chấp nhận số >= 0)
    int newQuantity;
    while (true) {
        System.out.print("Số lượng mới: ");
        String qStr = sc.nextLine().trim();
        if (!qStr.matches("\\d+")) {
            System.out.println(" Số lượng phải ở dạng số!");
            continue;
        }
        newQuantity = Integer.parseInt(qStr);
        if (newQuantity < 0) {
            System.out.println(" Số lượng không được âm!");
        } else break;
    }

    // Cập nhật thông tin sách
    bookToUpdate.setId(newId);
    bookToUpdate.setTitle(newTitle);
    bookToUpdate.setAuthor(newAuthor);
    bookToUpdate.setYear(newYear);
    bookToUpdate.setQuantity(newQuantity);

    System.out.println("Cập nhật thành công!");
}

    void addUser() {
        // ID
        String userId;
        while (true) {
        System.out.print("ID: ");
        userId = sc.nextLine().trim();

        final String idToCheck = userId; // biến final tạm thời, lambda được phép dùng
        if (userId.isEmpty()) {
            System.out.println(" ID không được trống!");
        } else if (users.stream().anyMatch(u -> u.getUserId().equalsIgnoreCase(idToCheck))) {
            System.out.println(" ID đã tồn tại. Xin dùng ID khác");
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
                System.out.println(" CCCD phải đủ 12 số!");
            } else {
                break;
            }
        }

        // Name
        String name;
        while (true) {
            System.out.print("Tên người dùng: ");
            name = sc.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println(" Tên không được để trống!");
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
                System.out.println("Sinh nhật phải theo format dd/MM/yyyy!");
                continue;
            }
            try {
                java.time.LocalDate.parse(birthday,
                        java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                break;
            } catch (Exception e) {
                System.out.println("Ngày không hợp lệ, xin thử lại!");
            }
        }

        // Address
        String userAddress;
        while (true) {
            System.out.print("Địa chỉ: ");
            userAddress = sc.nextLine().trim();
            if (userAddress.isEmpty()) {
                System.out.println("Địa chỉ không được để trống!");
            } else {
                break;
            }
        }

        // Tạo user mới
        User user = new User(userId, cccd, name, birthday, userAddress);
        users.add(user);

        System.out.println("Thêm người dùng thành công!");
    }

    // void removeUser(User user) {
    //     users.remove(user);
    // }
    void getUserInfo() {
        if (users.isEmpty()) {
            System.out.println(" Không tìm thấy người dùng trong hệ thống");
            return;
        }

        System.out.println("=========== THÔNG TIN NGƯỜI DÙNG ===========");

        for (User user : users) {
            System.out.println("---------------------------------");
            System.out.printf("Tên người dùng: %s | ID: %s | CCCD: %s | Sinh nhật: %s | Địa chỉ: %s\n",
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
        System.out.println("Nhập ID người dùng: ");
        String userId = sc.next();
        System.out.println("Nhập ID sách: ");
        String bookId = sc.next();
        System.out.println("Nhập số lượng mượn: ");
        int quantity = sc.nextInt();

        if (quantity <= 0) {
            System.out.println(" Số lượng không hợp lệ!");
            return;
        }

        User user = UserById(userId);
        Book book = BookById(bookId);

        if (user == null) {
            System.out.println(" Người dùng với ID " + userId + " không tồn tại!");
            return;
        }
        if (book == null) {
            System.out.println(" Sách với ID " + bookId + " không tồn tại!");
            return;
        }

        if (book.getQuantity() < quantity) {
            System.out.println(" Không đủ sách trong kho! Còn lại: " + book.getQuantity());
            return;
        }

        user.borrowBook(book, quantity); // gọi hàm ban đầu của User
        book.setQuantity(book.getQuantity() - quantity);

        System.out.println(user.getUserName() + " mượn " + quantity + " quyển \"" + book.getTitle() + "\"");
    }

    void userReturnBook() {
        System.out.println("Nhập ID người dùng: ");
        String userId = sc.next();
        System.out.println("Nhập ID sách: ");
        String bookId = sc.next();
        System.out.println("Nhập số lượng trả: ");
        int quantity = sc.nextInt();

        if (quantity <= 0) {
            System.out.println(" Số lượng không hợp lệ!");
            return;
        }

        User user = UserById(userId);
        Book book = BookById(bookId);

        if (user == null) {
            System.out.println(" Người dùng với ID " + userId + " không tồn tại!");
            return;
        }
        if (book == null) {
            System.out.println(" Sách với ID " + bookId + " không tồn tại!");
            return;
        }

        int borrowedQuantity = user.getBorrowedQuantity(book); // User cần có hàm này
        if (borrowedQuantity < quantity) {
            System.out.println(" Bạn đang trả vượt số lượng đã mượn! Số lượng: " + borrowedQuantity);
            return;
        }

        user.returnBook(book, quantity); // gọi hàm ban đầu của User
        book.setQuantity(book.getQuantity() + quantity);

        System.out.println(user.getUserName() + " trả " + quantity + " quyển \"" + book.getTitle() + "\"");
    }

    User UserById(String id) {
        for (User u : users) {
            if (u.getUserId().equals(id)) return u;
        }
        return null;
    }
    Book BookById(String id) {
        for (Book b : books) {
            if (b.getId().equals(id)) return b;
        }
        return null;
    }

    ArrayList<String> readFile(String FILEPATH) {
        ArrayList<String> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILEPATH))){
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) data.add(line.trim());
            }
        } catch (IOException e) {
            System.out.println("Có lỗi xảy ra");
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
            String userId = p[0];
            String bookId = p[1];
            int quantity = Integer.parseInt(p[2]);

            Book bk = BookById(bookId);
            User u = UserById(userId);
            u.getBorrowedBooks().add(new BorrowRecord(bk,quantity));
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
            System.out.println("Có lỗi xảy ra");
        }
    }

    void getData() {
        addBookData(readFile("../records/books.txt"));
        addUserData(readFile("../records/users.txt"));
        addBorrowRecord(readFile("../records/borrow_records.txt"));
    }
    void storeData() {
        writeFile("../records/books.txt",storeBookData());
        writeFile("../records/users.txt",storeUserData());
        writeFile("../records/borrow_records.txt",storeBorrowBook());
    }
}