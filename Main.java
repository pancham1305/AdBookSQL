import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Database db = new Database();
        Connection conn = db.config();
        AddressBookSystem addressBookSystem = new AddressBookSystem(db, conn);

        if (conn == null) {
            System.out.println("Connection not established");
        } else {
            System.out.println("Connection established");
            try {
                int choice;
                do {
                    System.out.println("\nMain Menu:");
                    System.out.println("1. Create Address Book");
                    System.out.println("2. Display Address Books");
                    System.out.println("3. Select Address Book");
                    System.out.println("4. Exit");
                    System.out.print("Enter your choice: ");
                    choice = scanner.nextInt();

                    switch (choice) {
                        case 1:
                            System.out.print("Enter the name of the new Address Book: ");
                            String bookName = scanner.next();
                            addressBookSystem.addAddressBook(new AddressBook(bookName), db, conn);
                            System.out.println("Address Book created successfully.");
                            break;

                        case 2:
                            System.out.println("\nAddress Books:");
                            for (AddressBook book : addressBookSystem.addressBookList) {
                                System.out.println(book.getBookName());
                            }
                            break;

                        case 3:
                            System.out.print("Enter the name of the Address Book to select: ");
                            String selectedBook = scanner.next();
                            AddressBook addressBook = addressBookSystem.addressBookList.stream()
                                    .filter(book -> book.getBookName().equals(selectedBook))
                                    .findFirst()
                                    .orElse(null);

                            if (addressBook != null) {
                                int subChoice;
                                do {
                                    System.out.println("\nOperations for Address Book: " + selectedBook);
                                    System.out.println("1. Add Contact");
                                    System.out.println("2. Display Contacts");
                                    System.out.println("3. Back to Main Menu");
                                    System.out.print("Enter your choice: ");
                                    subChoice = scanner.nextInt();

                                    switch (subChoice) {
                                        case 1:
                                            addressBook.createContact(scanner, db, conn);
                                            System.out.println("Contact added successfully.");
                                            break;

                                        case 2:
                                            addressBook.displayContacts(db, conn);
                                            break;

                                        case 3:
                                            System.out.println("Returning to main menu...");
                                            break;

                                        default:
                                            System.out.println("Invalid choice. Please try again.");
                                    }
                                } while (subChoice != 3);
                            } else {
                                System.out.println("Address Book not found.");
                            }
                            break;

                        case 4:
                            System.out.println("Exiting program. Goodbye!");
                            break;

                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                } while (choice != 4);

            } catch (SQLException e) {
                System.err.println("Database operation error: " + e.getMessage());
            }
        }
    }
}
