import java.util.*;

public interface LibraryManagementSystem {
    void inventory(String filePath);
    void lend(String isbn);
    void putBack (String name);
    void registerStudent(Student student);
    Book search(String isbn);
    List<Book> sort(int mode);

}
