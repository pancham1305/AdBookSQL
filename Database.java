import java.sql.*;
import java.util.List;

public class Database {
    public Connection config() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            System.out.println("Driver not found");
        }
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/mydb", "sk", "13052003");
            System.out.println("it works");
            return conn;
        } catch (Exception e) {
            System.out.println("Connection not established");
        }
        return null;
    }

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

    public void createTable(Connection conn, String AddressBookName, String type) throws SQLException {
        String createQuery = "create table " + AddressBookName +
                "(     FirstName VARCHAR(50) NOT NULL,     LastName VARCHAR(50) NOT NULL,     Address VARCHAR(255) NOT NULL,     City VARCHAR(100) NOT NULL,     State CHAR(100) NOT NULL,     Zip CHAR(10) NOT NULL,     PhoneNumber VARCHAR(12),     Email VARCHAR(100),     PRIMARY KEY (FirstName, LastName)  )";
        Statement statement = conn.createStatement();
        statement.executeUpdate(createQuery);
        System.out.println("Table created");
        // insert into metadata
        String insertQuery = "insert into metadata values('" + AddressBookName + "', '" + type + "')";
        Statement stmt = conn.createStatement();
        statement.executeUpdate(insertQuery);
        System.out.println("Table created");
    }

    public void insertData(Connection conn, String AddressBookName, String FirstName, String LastName, String Address,
            String City, String State, int Zip, int PhoneNumber, String Email) throws SQLException {
        String insertQuery = "insert into " + AddressBookName + " values('" + FirstName + "','" + LastName + "','"
                + Address + "','" + City + "','" + State + "'," + Zip + "," + PhoneNumber + ",'" + Email + "')";
        Statement statement = conn.createStatement();
        statement.executeUpdate(insertQuery);
        System.out.println("Data inserted");
    }

    public void updateData(Database db, Connection conn, String FirstName, String Lastname, AddressBook ab,
            String address, String city, String state, int zip, int phone, String email) throws SQLException {
        try {
            String updateQuery = "update " + ab.getBookName() + " set Address = '" + address + "', City = '" + city
                    + "', State = '"
                    + state + "', Zip = " + zip + ", PhoneNumber = " + phone + ", Email = '" + email
                    + "' where FirstName = '" + FirstName + "' and LastName = '" + Lastname + "'";
            Statement statement = conn.createStatement();
            statement.executeUpdate(updateQuery);
            System.out.println("Data updated");
        } catch (Exception e) {
            System.out.println("Data not updated");
        }
    }

    public void deleteData(Connection conn, String AddressBookName, String FirstName, String LastName)
            throws SQLException {
        String deleteQuery = "delete from " + AddressBookName + " where FirstName = '" + FirstName
                + "' and LastName = '" + LastName + "'";
        Statement statement = conn.createStatement();
        statement.executeUpdate(deleteQuery);
        System.out.println("Data deleted");
    }

    public void displayData(Connection conn, String AddressBookName) throws SQLException {
        String displayQuery = "select * from " + AddressBookName;
        Statement statement = conn.createStatement();
        ResultSet res = statement.executeQuery(displayQuery);
        TableFormatter.displayAsTable(res);
    }

    public void getDataByCityOrState(Connection conn, String AddressBookName, String city, String state)
            throws SQLException {
        String getQuery = "select * from " + AddressBookName + " where City = '" + city + "' or State = '" + state
                + "'";
        Statement statement = conn.createStatement();
        ResultSet res = statement.executeQuery(getQuery);
        TableFormatter.displayAsTable(res);
    }

    public void getCountByCity(Connection conn, String AddressBookName, String city, String State)
            throws SQLException {
        String query = "select city, count(*) as count from " + AddressBookName + " group by city";
        Statement stmt = conn.createStatement();
        ResultSet res = stmt.executeQuery(query);
        TableFormatter.displayAsTable(res);
    }

    public void getCountByState(Connection conn, String AddressBookName, String city, String State)
            throws SQLException {
        String query = "select state, count(*) as count from " + AddressBookName + " group by state";
        Statement stmt = conn.createStatement();
        ResultSet res = stmt.executeQuery(query);
        TableFormatter.displayAsTable(res);
    }

    public void sortedList(Connection conn, String city, String bookName) {
        String query = "select FirstName, LastName from " + bookName + " where city = '" + city
                + "' order by FirstName";
        try {
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            TableFormatter.displayAsTable(res);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
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
