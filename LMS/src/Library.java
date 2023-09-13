import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Library implements LibraryManagementSystem {
    List<FictionBook> fictionBooks;
    HashMap<String,Integer> inventory;
    List<NonfictionBook> nonfictionBooks;
    List<Student> students;

    /**
     * Constructor for the library class. Initializes the instance variables and fills
     * them with the data from the text file provided.
     * @param inventoryPath path to the inventory text file
     */
    public Library(String inventoryPath) {
        fictionBooks = new ArrayList<>();
        nonfictionBooks = new ArrayList<>();
        students = new ArrayList<>();
        inventory = new HashMap<>();
        inventory("src/inventory v2.txt");
    }

    @Override
    public void inventory(String filePath) {
        try {
            File file = new File(filePath);
            Scanner fileScanner = new Scanner(file);
            int lineNumber = 0;
            while (fileScanner.hasNext()) {
                if (lineNumber == 0) {
                    fileScanner.nextLine();
                    lineNumber++;
                } else {
                    String line = fileScanner.nextLine();
                    String[] bookData = line.split(",");
                    String isbn = bookData[0];
                    String name = bookData[1];
                    String author = bookData[2];
                    String pages = bookData[3];
                    String quantity = bookData[4];
                    if (bookData[5].equals("fiction")) {
                        fictionBooks.add(new FictionBook(author, isbn, name, pages));
                    } else {
                        nonfictionBooks.add(new NonfictionBook(author, isbn, name, pages));
                    }
                    inventory.put(isbn, Integer.parseInt(quantity));
                }
            }
        } catch (FileNotFoundException e){
            System.out.println("Inventory File not Found!");
        }
    }

    @Override
    public void lend(String isbn) {
        if (inventory.get(isbn) > 0) {
            inventory.put(isbn, inventory.get(isbn) - 1);
        }
        else{
            System.out.println("Unable to lend, out of copies.");
        }
    }

    @Override
    public void putBack(String isbn) {
        inventory.put(isbn, inventory.get(isbn) + 1);
    }

    @Override
    public void registerStudent(Student student) {
            for (Student s : students) {
                if (student.registrationNumber.equals(s.registrationNumber)) {
                    throw new IllegalArgumentException();
                }
            }
            students.add(student);
    }

        @Override
    public Book search(String isbn) {
        for (FictionBook b: fictionBooks) {
            if (b.getIsbn().equals(isbn)){
                return b;
            }
        }
        for (NonfictionBook b: nonfictionBooks) {
            if (b.getIsbn().equals(isbn)){
                return b;
            }
        }
        return null;
    }

    /**
     * returns the list sorted from one of two modes. throws IllegalArgumentException
     * if a mode other than 1 or 2 is used.
     * @param mode 1 for isbn, 2 for quantity
     * @return the sorted list
     */
    @Override
    public List<Book> sort(int mode) {
        ArrayList<Book> sortedList = new ArrayList<>();
        if (mode == 1) {
            ArrayList<Integer> isbnList = new ArrayList<>();
            for (String isbn : inventory.keySet()) {
                isbnList.add(Integer.parseInt(isbn));
            }
            isbnList.sort(Comparator.naturalOrder());
            for (int matches = 0; matches < 10; matches++){
                //get top 10 matches, break loop when at 10
                String currentIsbn = isbnList.get(matches).toString();
                for (Book b: nonfictionBooks) {
                    if (b.getIsbn().equals(currentIsbn)){
                        sortedList.add(b);
                    }
                }
                for (Book b: fictionBooks) {
                    if (b.getIsbn().equals(currentIsbn)){
                        sortedList.add(b);
                    }
                }
            }

        }
        else if (mode == 2) {
            ArrayList<String> isbnList = new ArrayList<>();
            int highest = 0;
            Map.Entry<String, Integer> highestEntry = new AbstractMap.SimpleEntry<>("",0);
            HashMap<String,Integer> tempMap = new HashMap<>(inventory);
             while (isbnList.size() < 10) {
                 for (Map.Entry<String, Integer> entry : tempMap.entrySet()) {
                     if (entry.getValue() > highest) {
                         highestEntry = entry;
                         highest = entry.getValue();
                     }
                 }
                 isbnList.add(highestEntry.getKey());
                 tempMap.remove(highestEntry.getKey());
                 highestEntry = new AbstractMap.SimpleEntry<>("",0);
                 highest = 0;
             }
            for (int matches = 0; matches < 10; matches++){
                //get top 10 matches, break loop when at 10
                String currentIsbn = isbnList.get(matches);
                for (Book b: nonfictionBooks) {
                    if (b.getIsbn().equals(currentIsbn)){
                        sortedList.add(b);
                    }
                }
                for (Book b: fictionBooks) {
                    if (b.getIsbn().equals(currentIsbn)){
                        sortedList.add(b);
                    }
                }
            }
        }
        else{throw new IllegalArgumentException("Not a valid sort mode.");}
        return sortedList;
    }

    /**
     * Calculates # of available books
     * @return the array of available number books with [0] being fiction books and [1] being nonfiction
     */
    public int[] availableBooks(){
        int[] availableBooks = {0,0};
        for (Book b: fictionBooks) {
            if (inventory.get(b.getIsbn()) > 0){
                availableBooks[0] += inventory.get(b.getIsbn());
            }
        }
        for (Book b: nonfictionBooks) {
            if (inventory.get(b.getIsbn()) > 0){
                availableBooks[1] += inventory.get(b.getIsbn());
            }
        }
        return availableBooks;
    }

    public List<FictionBook> getFictionBooks() {
        return fictionBooks;
    }

    public void setFictionBooks(List<FictionBook> fictionBooks) {
        this.fictionBooks = fictionBooks;
    }

    public HashMap<String, Integer> getInventory() {
        return inventory;
    }

    public void setInventory(HashMap<String, Integer> inventory) {
        this.inventory = inventory;
    }

    public List<NonfictionBook> getNonfictionBooks() {
        return nonfictionBooks;
    }

    public void setNonfictionBooks(List<NonfictionBook> nonfictionBooks) {
        this.nonfictionBooks = nonfictionBooks;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}

