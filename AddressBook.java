
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

    public void updateContact(Scanner ts, Database db, Connection conn) throws SQLException {
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
        db.updateData(db, conn, FirstName, LastName, this, address, city, state, zip, phone, email);
    }

    public void deleteContact(Scanner sc, Database db, Connection conn) throws SQLException {
        System.out.println("Enter First Name:");
        String FirstName = sc.next();
        System.out.println("Enter Last Name:");
        String LastName = sc.next();
        db.deleteData(conn, bookName, FirstName, LastName);
    }

    public void getDataByCityOrState(Database db, Connection conn, String city, String state) throws SQLException {
        db.getDataByCityOrState(conn, bookName, city, state);
    }

    public void getCountByCity(Database db, Connection conn, String city, String state) throws SQLException {
        db.getCountByCity(conn, state, city, state);
    }

    public void getCountByState(Database db, Connection conn, String city, String state) throws SQLException {
        db.getCountByState(conn, state, city, state);
    }
    
    public String getBookName() {
        return bookName;
    }
}