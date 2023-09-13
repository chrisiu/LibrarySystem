import org.junit.jupiter.api.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {
    String libraryPath = "src/inventory v2.txt";
    @Test
    public void constructorErrorTest(){
        assertDoesNotThrow(()-> {Library library = new Library("invalid.txt");});
        assertDoesNotThrow(()-> {Library library = new Library("src/inventory v2.txt");});
    }
    @Test
    public void sortErrorTest(){
        Library library = new Library("src/inventory v2.txt");
        assertThrows(IllegalArgumentException.class, ()-> {library.sort(3);});
    }
    @Test
    public void isbnSortTest(){
        Library library = new Library("src/inventory v2.txt");
        Book book1 = new FictionBook("Jane Austen", "10384", "Pride and Prejudice", "416");
        Book book2 = new NonfictionBook("Paulo Coelho", "13044", "The Alchemist", "208");
        Book book3 = new FictionBook("Alice Sebold", "17359", "The Lovely Bones", "328");
        Book book4 = new FictionBook("Stephen King", "17362", "The Stand", "1152");
        Book book5 = new NonfictionBook("Rebecca Skloot", "17427", "The Immortal Life of Henrietta Lacks", "400");
        Book book6 = new FictionBook("Dr. Seuss", "17463", "Green Eggs and Ham", "62");
        Book book7 = new FictionBook("Mark Twain", "18375", "The Adventures of Tom Sawyer", "288");
        Book book8  = new NonfictionBook("Rhonda Byrne", "20824", "The Secret", "216");
        Book book9 = new NonfictionBook("Charles Darwin", "23401", "The Origin of Species", "502");
        Book book10 = new FictionBook("Lois Lowry", "23596", "The Giver", "240");
        ArrayList<Book> isbnSorted = new ArrayList<>();
        isbnSorted.add(book1);
        isbnSorted.add(book2);
        isbnSorted.add(book3);
        isbnSorted.add(book4);
        isbnSorted.add(book5);
        isbnSorted.add(book6);
        isbnSorted.add(book7);
        isbnSorted.add(book8);
        isbnSorted.add(book9);
        isbnSorted.add(book10);
        ArrayList<Book> actual = new ArrayList<>();
        actual = (ArrayList<Book>) library.sort(1);
        assertEquals(isbnSorted.toString(),actual.toString());
    }

    @Test
    public void studentTest(){
        Library library = new Library(libraryPath);
        library.registerStudent(new Student("Josh", "5567B"));
        assertEquals("Josh", library.getStudents().get(0).getName());
        assertThrows(IllegalArgumentException.class, () -> {library.registerStudent(new Student("Don", "5567B"));});
    }

    @Test
    public void inventoryTest(){
        assertDoesNotThrow(() ->{Library library = new Library(libraryPath);});
    }

    @Test
    public void lendTest(){
        Library library = new Library(libraryPath);
        assertEquals(21,library.inventory.get("78350"));
        library.lend("78350");
        assertEquals(20,library.inventory.get("78350"));
    }

    @Test
    public void putBackTest(){
        Library library = new Library(libraryPath);
        assertEquals(21,library.inventory.get("78350"));
        library.putBack("78350");
        assertEquals(22,library.inventory.get("78350"));
    }
    @Test
    public void searchTest(){
        Library library = new Library(libraryPath);
        Book book = new FictionBook("George Orwell", "78350", "1984", "328");
        assertEquals(book.toString(),library.search("78350").toString());
    }
    @Test
    public void availableBooksTest() {
        Library library = new Library(libraryPath);
        int[] stats = {838,446};
        assertArrayEquals(stats, library.availableBooks());
        }
}