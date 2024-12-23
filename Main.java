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
                    scanner.nextLine();

                    switch (choice) {
                        case 1:
                            System.out.print("Enter the name of the new Address Book: ");
                            String bookName = scanner.nextLine();
                            System.out.println("Enter Type of AddressBook (Friends, Family, Profession, etc.):");
                            String type = scanner.nextLine();
                            addressBookSystem.addAddressBook(new AddressBook(bookName, type), db, conn);
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
                            String selectedBook = scanner.nextLine();
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
                                    System.out.println("3. Edit Contact");
                                    System.out.println("4. Delete Contact");
                                    System.out.println("5. Get Contacts by City or State");
                                    System.out.println("6. Get Contact Count by City");
                                    System.out.println("7. Get Contact Count by State");
                                    System.out.println("8. Get Sorted List by Name for a City");
                                    System.out.println("9. Back to Main Menu");
                                    System.out.print("Enter your choice: ");
                                    subChoice = scanner.nextInt();
                                    scanner.nextLine();
                                    switch (subChoice) {
                                        case 1:
                                            addressBook.createContact(scanner, db, conn);
                                            System.out.println("Contact added successfully.");
                                            break;
                                        case 2:
                                            addressBook.displayContacts(db, conn);
                                            break;
                                        case 3:
                                            addressBook.updateContact(scanner, db, conn);
                                            System.out.println("Contact updated successfully.");
                                            break;
                                        case 4:
                                            addressBook.deleteContact(scanner, db, conn);
                                            System.out.println("Contact deleted successfully.");
                                            break;
                                        case 5:
                                            System.out.println("Enter City:");
                                            String searchCity = scanner.nextLine();
                                            System.out.println("Enter State:");
                                            String searchState = scanner.nextLine();
                                            addressBook.getDataByCityOrState(db, conn, searchCity, searchState);
                                            break;
                                        case 6:
                                            System.out.println("Enter City:");
                                            String countCity = scanner.nextLine();
                                            System.out.println("Enter State:");
                                            String countStateForCity = scanner.nextLine();
                                            addressBook.getCountByCity(db, conn, countCity, countStateForCity);
                                            break;
                                        case 7:
                                            System.out.println("Enter City:");
                                            String cityForState = scanner.nextLine();
                                            System.out.println("Enter State:");
                                            String countState = scanner.nextLine();
                                            addressBook.getCountByState(db, conn, cityForState, countState);
                                            break;
                                        case 8:
                                            System.out.println("Enter City:");
                                            String sortCity = scanner.nextLine();
                                            addressBook.getSortedListByNameGivenaCity(sortCity, conn, selectedBook, db);
                                            break;
                                        case 9:
                                            System.out.println("Returning to main menu...");
                                            break;
                                        default:
                                            System.out.println("Invalid choice. Please try again.");
                                    }
                                } while (subChoice != 9);
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
            scanner.close();
        }
    }
}