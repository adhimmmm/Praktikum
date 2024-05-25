package tugas.books;

public class StoryBook extends Book {

    private String category = "Story";

    public StoryBook(String bookId, String title, String author, int stock) {
        super(bookId, title, author, stock);
        super.setCategory(category);
    }

    @Override
    public String getCategory() {
        return category;
    }
}
