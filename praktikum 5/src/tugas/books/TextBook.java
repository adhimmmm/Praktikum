package tugas.books;

public class TextBook extends Book {
    private String category = "Text Book";

    public TextBook(String bookId, String title, String author, int stock) {
        super(bookId, title, author, stock);
        super.setCategory(category);
    }
}
