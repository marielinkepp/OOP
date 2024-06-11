package task6;

import java.util.List;

public class Book {
    private final String title;
    private final List<String> authors;
    private final int pages;

    public Book(String title, List<String> authors, int pages) {
        this.title = title;
        this.authors = authors;
        this.pages = pages;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public int getPages() {
        return pages;
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%d)", title, String.join(", ", authors), pages);
    }
}
