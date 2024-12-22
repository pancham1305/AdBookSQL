import java.sql.*;
import java.util.ArrayList;

public class AddressBookSystem {
    ArrayList<AddressBook> addressBookList = new ArrayList<>();

    private void loadAddressBooks(Database db, Connection conn) throws SQLException {
        String query = "select * from information_schema.tables where table_schema = 'mydb'";
        Statement statement = conn.createStatement();
        ResultSet res = statement.executeQuery(query);
        while (res.next()) {
            addressBookList.add(new AddressBook(res.getString("table_name")));
        }
    }

    AddressBookSystem(Database db, Connection conn) {
        try {
            loadAddressBooks(db, conn);
            System.out.println("Finally working");
        } catch (Exception e) {
            System.out.println("Error in loading Address Books");
        }
    }

    private void createAddressBook(String name, Database db, Connection conn) throws SQLException {
        AddressBook addressBook = new AddressBook(name);
        db.createTable(conn, name);
        addressBookList.add(addressBook);

    }

    public void addAddressBook(AddressBook addressBook, Database db, Connection conn) throws SQLException {
        createAddressBook(addressBook.getBookName(), db, conn);
    }
}
