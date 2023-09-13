import java.io.*;
import java.util.*;


public class LibraryRunner {
    private Library library;
    private HashMap<String, List<String>> borrowedBooks;
    public LibraryRunner(String inventoryFilePath) {
        library = new Library(inventoryFilePath);
        borrowedBooks = new HashMap<>();
        readFile("src/borrowed_books.txt");
    }
    public void run() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("");
            System.out.println("Welcome to the Library Management System");
            System.out.println("_________________________________________");
            System.out.println("1. Register");
            System.out.println("2. Sort Books");
            System.out.println("3. Search Books");
            System.out.println("4. Borrow Book");
            System.out.println("5. Return Book");
            System.out.println("6. Show Inventory Stats");
            System.out.println("0. Exit");
            System.out.println("_________________________________________");
            System.out.print("Please choose an option by entering its corresponding number:");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Enter your name to register: ");
                    String student = scanner.nextLine();
                    System.out.print("Choose a student register number: ");
                    String regnum = scanner.nextLine();
                    Student newstudent = new Student(student, regnum);
                    try {
                        library.registerStudent(newstudent);
                        System.out.println(student + " has been registered with registration number " + regnum + ".");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Student Registration Number is already present in this library.");
                    }
                    break;
                case 2:
                    System.out.println("Books sorted by ISBN: ");
                    List<Book> sortedList = library.sort(1);
                    int i = 0;
                    for (Book book : sortedList) {
                        i++;
                        System.out.println(i + ". " + book);
                    }
                    break;


                case 3:
                    System.out.print("Enter the ISBN to search: ");
                    String isbn = scanner.nextLine();
                    Book bookcheck = library.search(isbn);
                    if (bookcheck == null) {
                        System.out.println("The ISBN you have entered was not matched with a book.");
                    } else {
                        System.out.println("Book found... " + bookcheck);
                    }
                    break;
                case 4:
                    System.out.print("Enter your student registration number: ");
                    String registrationNumber = scanner.nextLine();
                    boolean inSystem = false;
                    for (Student s : library.getStudents()) {
                        if (s.registrationNumber.equals(registrationNumber)) {
                            inSystem = true;
                            break;
                        }
                    }
                        if (inSystem) {
                            System.out.print("Enter the ISBN of the book you want to borrow: ");
                            String borrowISBN = scanner.nextLine();


                            if (library.search(borrowISBN) != null) {
                                library.lend(borrowISBN);
                                if (borrowedBooks.containsKey(registrationNumber)) {
                                    borrowedBooks.get(registrationNumber).add(borrowISBN);
                                    System.out.println(library.search(borrowISBN).getName() + " was borrowed");
                                } else {
                                    List<String> borrowedList = new ArrayList<>();
                                    borrowedList.add(borrowISBN);
                                    borrowedBooks.put(registrationNumber, borrowedList);
                                    System.out.println(library.search(borrowISBN).getName() + " was borrowed");
                                }
                            } else {
                                System.out.println("ISBN not found");
                            }
                            writeFile("src/borrowed_books.txt");
                        }
                        else {
                            System.out.println("Error: Student Registration number not found in library.");
                        }
                        break;



                case 5:
                    System.out.print("Enter your student registration number: ");
                    String registrationNumber1 = scanner.nextLine();
                    List<String> borrowedISBNs = borrowedBooks.get(registrationNumber1);
                    if (borrowedISBNs != null && borrowedISBNs.isEmpty() == false) {
                        System.out.println("Books borrowed by registration number " + registrationNumber1 + ":");
                        for (String borrowedISBN : borrowedISBNs) {
                            System.out.println("- " + library.search(borrowedISBN).getName() + ", ISBN: " + library.search(borrowedISBN).getIsbn());
                        }
                    } else {
                        System.out.println("No books found for registration number " + registrationNumber1);
                        break;
                    }

                    System.out.print("Enter the ISBN of the book you want to return: ");
                    String removeISBN = scanner.nextLine();
                    boolean removed = false;


                    for (String registrationNumber2 : borrowedBooks.keySet()) {
                        List<String> borrowedISBNs1 = borrowedBooks.get(registrationNumber2);
                        for (String borrowedISBN : borrowedISBNs) {
                            if (borrowedISBN.equals(removeISBN)) {
                                borrowedISBNs1.remove(removeISBN);
                                removed = true;
                                break;
                            }
                        }
                        if (removed) {
                            break;
                        }
                    }


                    if (removed) {
                        System.out.println("Book with ISBN " + removeISBN + " has been removed from borrowed books.");
                    } else {
                        System.out.println("Book with ISBN " + removeISBN + " was not found in borrowed books.");
                    }


                    library.putBack(removeISBN);
                    writeFile("src/borrowed_books.txt");
                    break;


                case 6:
                    int nonfictionQuantity = 0;
                    int fictionQuantity = 0;
                    for (Map.Entry<String, Integer> entry : library.inventory.entrySet()) {
                        String isbn1 = entry.getKey();
                        int quantity = entry.getValue();


                        for (NonfictionBook book : library.nonfictionBooks) {
                            if (book.getIsbn().equals(isbn1)) {
                                nonfictionQuantity += quantity;
                                break;
                            }
                        }


                        for (FictionBook book : library.fictionBooks) {
                            if (book.getIsbn().equals(isbn1)) {
                                fictionQuantity += quantity;
                                break;
                            }
                        }
                    }
                    int[] stats = {fictionQuantity, nonfictionQuantity};
                    InventoryChart chart = new InventoryChart("Available Fiction and Nonfiction Books", stats);
                    chart.displayGraph();


                case 0:
                    System.out.println("Ending the Library Management System. Thank you!");
                    break;
                default:
                    System.out.println("Invalid input. Please enter a listed option.");
                    break;
            }
        } while (choice != 0);
        scanner.close();
    }


    public static void main(String[] args) {
        LibraryRunner libraryRunner = new LibraryRunner("src/inventory v2.txt");
        libraryRunner.run();
    }


    private HashMap<String, List<String>> readFile(String fileName) {
        try {
            FileReader reader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                for (String s:
                     parts) {
                    System.out.println(s + "\n");
                }

                String registrationNumber = parts[0].trim();
                String borrowISBN = parts[1].trim();
                if (borrowedBooks.containsKey(registrationNumber)) {
                    borrowedBooks.get(registrationNumber).add(borrowISBN);
                } else {
                    List<String> isbnList = new ArrayList<>();
                    isbnList.add(borrowISBN);
                    borrowedBooks.put(registrationNumber, isbnList);
                }
            }


            bufferedReader.close();
            reader.close();
        } catch (IOException e) {
            System.out.println("Failed to read from " + fileName + ": " + e.getMessage());
        }


        return borrowedBooks;
    }


    private void writeFile(String filename) {
        try {
            File file = new File(filename);
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));


            for (String registrationNumber : borrowedBooks.keySet()) {
                List<String> books = borrowedBooks.get(registrationNumber);
                for (String isbn : books) {
                    String line = registrationNumber + "," + isbn;
                    bw.write(line);
                    bw.newLine();
                }
            }

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

