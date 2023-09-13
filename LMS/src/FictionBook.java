public class FictionBook extends Book {

    public FictionBook(String author, String isbn, String name, String pages) {
        super(author, isbn, name, pages);
    }

    @Override
    public String toString() {
            return super.toString() + "\tType: Fiction.\n";
        }
}
