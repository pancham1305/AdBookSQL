
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

class AddressBook {
    private String bookName;

    public AddressBook(String bookName) {
        this.bookName = bookName;
    }

    public void createContact(Scanner ts, Database db, Connection conn) throws SQLException {
        System.out.println("Enter First Name:");
        String FirstName = ts.next();
        System.out.println("Enter Last Name:");
        String LastName = ts.next();
        System.out.println("Enter City:");
        String city = ts.next();
        System.out.println("Enter State:");
        String state = ts.next();
        System.out.println("Enter Email:");
        String email = ts.next();
        System.out.println("Enter Phone:");
        int phone = ts.nextInt();
        System.out.println("Enter ZIP:");
        int zip = ts.nextInt();
        String address = city + ", " + state + " " + zip;
        db.insertData(conn, bookName, FirstName, LastName, address, city, state, zip, phone, email);
    }

    public void displayContacts(Database db, Connection conn) throws SQLException {
        db.displayData(conn, bookName);
    }

    public String getBookName() {
        return bookName;
    }
}