import java.sql.*;
import java.util.*;

public class TableFormatter {
    // Helper method to get column widths without requiring scrollable ResultSet
    private static Map<String, Integer> getColumnWidths(ResultSet rs, ResultSetMetaData metaData) throws SQLException {
        Map<String, Integer> columnWidths = new HashMap<>();
        List<List<String>> data = new ArrayList<>();
        int columnCount = metaData.getColumnCount();

        // Initialize with column header lengths
        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);
            columnWidths.put(columnName, columnName.length());
        }

        // Store all rows and track maximum widths
        while (rs.next()) {
            List<String> row = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                String value = rs.getString(i);
                row.add(value);
                if (value != null) {
                    String columnName = metaData.getColumnName(i);
                    columnWidths.put(columnName,
                            Math.max(columnWidths.get(columnName), value.length()));
                }
            }
            data.add(row);
        }

        // Print table header
        printLine(columnWidths);
        printHeader(metaData, columnWidths);
        printLine(columnWidths);

        // Print all stored rows
        for (List<String> row : data) {
            printRow(row, metaData, columnWidths);
        }
        printLine(columnWidths);

        return columnWidths;
    }

    // Print horizontal line
    private static void printLine(Map<String, Integer> columnWidths) {
        for (int width : columnWidths.values()) {
            System.out.print("+" + "-".repeat(width + 2));
        }
        System.out.println("+");
    }

    // Print header row
    private static void printHeader(ResultSetMetaData metaData, Map<String, Integer> columnWidths)
            throws SQLException {
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            String columnName = metaData.getColumnName(i);
            System.out.printf("| %-" + columnWidths.get(columnName) + "s ", columnName);
        }
        System.out.println("|");
    }

    // Print data row
    private static void printRow(List<String> row, ResultSetMetaData metaData,
            Map<String, Integer> columnWidths) throws SQLException {
        for (int i = 0; i < row.size(); i++) {
            String value = row.get(i);
            String columnName = metaData.getColumnName(i + 1);
            System.out.printf("| %-" + columnWidths.get(columnName) + "s ",
                    value != null ? value : "");
        }
        System.out.println("|");
    }

    // Main method to display result set as table
    public static void displayAsTable(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        getColumnWidths(rs, metaData);
    }
}