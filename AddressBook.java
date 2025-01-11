
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.sql.Statement;

class AddressBook {
    private String bookName;

    private String type;

    private int getTypeId(String type, Connection conn) throws SQLException {
        String query = "SELECT id FROM Types WHERE type = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, type);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                } else {
                    throw new SQLException("Type not found: " + type);
                }
            }
        }
    }

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

    public void getCountByType(Connection conn) {
        String query = "SELECT type, COUNT(*) AS count FROM Contacts GROUP BY type";
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("Contact Counts by Type:");
            TableFormatter.displayAsTable(rs);
        } catch (SQLException e) {
            System.err.println("Error fetching counts by type: " + e.getMessage());
        }
    }

    public void addContactToMultipleTypes(int contactId, List<String> types, Connection conn) {
        String query = "INSERT INTO Contact_Type (contact_id, type_id) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            for (String type : types) {
                int typeId = getTypeId(type, conn); // Assume this method fetches type_id from the DB
                stmt.setInt(1, contactId);
                stmt.setInt(2, typeId);
                stmt.executeUpdate();
            }
            System.out.println("Contact added to multiple types successfully.");
        } catch (SQLException e) {
            System.err.println("Error assigning contact to multiple types: " + e.getMessage());
        }
    }
    

}