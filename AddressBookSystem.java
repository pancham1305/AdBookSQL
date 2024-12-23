import java.sql.*;
import java.util.ArrayList;

public class AddressBookSystem {
    ArrayList<AddressBook> addressBookList = new ArrayList<>();

    private void loadAddressBooks(Database db, Connection conn) throws SQLException {
        String query = "select * from metadata";
        Statement statement = conn.createStatement();
        ResultSet res = statement.executeQuery(query);
        while (res.next()) {
            addressBookList.add(new AddressBook(res.getString("Table_Name"), res.getString("Type")));
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

    private void createAddressBook(String name, String type, Database db, Connection conn) throws SQLException {
        AddressBook addressBook = new AddressBook(name, type);
        db.createTable(conn, name, type);
        addressBookList.add(addressBook);

    }

    public void addAddressBook(AddressBook addressBook, Database db, Connection conn) throws SQLException {
        createAddressBook(addressBook.getBookName(), addressBook.getType(), db, conn);
    }
}
