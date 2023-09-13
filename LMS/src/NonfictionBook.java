public class NonfictionBook extends Book {
    public NonfictionBook(String author, String isbn, String name, String pages) {
        super(author, isbn, name, pages);
    }

    @Override
    public String toString() {
        return super.toString() + "\tType: Nonfiction.\n";
    }
}
