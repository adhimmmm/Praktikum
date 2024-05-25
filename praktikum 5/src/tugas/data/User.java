package tugas.data;

import tugas.books.Book;

import java.util.ArrayList;

public abstract class User {
    private static ArrayList<Book> bookList = new ArrayList<>();

    public abstract void showUser();

    public void displayBook() {
        printLine();
        System.out.printf("|| %-3s || %-19s || %-20s || %-20s || %-12s || %-6s ||\n", "No.", "Id buku", "Nama Buku",
                "Author", "Category", "Stock");
        printLine();
        int count = 1;
        for (Book x : bookList) {
            System.out.printf("|| %-3d || %-19s || %-20s || %-20s || %-12s || %-6d ||\n", count, x.getBookId(),
                    x.getTitle(), x.getAuthor(), x.getCategory(), x.getStock());
            count++;
        }
        printLine();
    }

    public void printLine() {
        for (int i = 0; i < 53; i++)
            System.out.print("==");
        System.out.println();
    }

    public static ArrayList<Book> getBookList() {
        return bookList;
    }

    public void addBook(Book book) {
        bookList.add(book);
    }
}
