import java.sql.*;

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
}
