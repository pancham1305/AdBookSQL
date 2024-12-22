import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddressBookSystem {
    ArrayList<AddressBook> addressBookList = new ArrayList<>();

    private void createAddressBook(String name, Database db, Connection conn) throws SQLException {
        AddressBook addressBook = new AddressBook(name);
        db.createTable(conn, name);
        addressBookList.add(addressBook);

    }

    public void addAddressBook(AddressBook addressBook, Database db, Connection conn) throws SQLException {
        createAddressBook(addressBook.getBookName(), db, conn);
    }
}
