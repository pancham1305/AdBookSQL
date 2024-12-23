
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

class AddressBook {
    private String bookName;

    private String type;

    public AddressBook(String bookName, String type) {
        this.bookName = bookName;
        this.type = type;
    }

    public void createContact(Scanner ts, Database db, Connection conn) throws SQLException {
        System.out.println("Enter First Name:");
        String FirstName = ts.nextLine();
        System.out.println("Enter Last Name:");
        String LastName = ts.nextLine();
        System.out.println("Enter City:");
        String city = ts.nextLine();
        System.out.println("Enter State:");
        String state = ts.nextLine();
        System.out.println("Enter Email:");
        String email = ts.nextLine();
        System.out.println("Enter Phone:");
        int phone = ts.nextInt();
        System.out.println("Enter Zip:");
        int zip = ts.nextInt();
        String address = city + ", " + state + " " + zip;
        db.insertData(conn, bookName, FirstName, LastName, address, city, state, zip, phone, email);
    }

    public void displayContacts(Database db, Connection conn) throws SQLException {
        db.displayData(conn, bookName);
    }

    public void updateContact(Scanner ts, Database db, Connection conn) throws SQLException {
        System.out.println("Enter First Name:");
        String FirstName = ts.nextLine();
        System.out.println("Enter Last Name:");
        String LastName = ts.nextLine();
        System.out.println("Enter City:");
        String city = ts.nextLine();
        System.out.println("Enter State:");
        String state = ts.nextLine();
        System.out.println("Enter Email:");
        String email = ts.nextLine();
        System.out.println("Enter Phone:");
        int phone = ts.nextInt();
        System.out.println("Enter ZIP:");
        int zip = ts.nextInt();
        String address = city + ", " + state + " " + zip;
        db.updateData(db, conn, FirstName, LastName, this, address, city, state, zip, phone, email);
    }

    public void deleteContact(Scanner sc, Database db, Connection conn) throws SQLException {
        System.out.println("Enter First Name:");
        String FirstName = sc.nextLine();
        System.out.println("Enter Last Name:");
        String LastName = sc.nextLine();
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

    public void getSortedListByNameGivenaCity(String city, Connection conn, String bookName, Database db) {
        db.sortedList(conn, city, bookName);
    }

    public String getBookName() {
        return bookName;
    }

    public String getType() {
        return type;
    }
}