import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Database credentials
    private static final String URL = "jdbc:mysql://localhost:3306/contact";
    private static final String USER = "root";
    private static final String PASSWORD = "MySQL!2025_Str0ngP@ssw0rd";

    public static Connection getConnection() throws SQLException {  // Declare it can throw SQLException
        try {
            // Step 1: Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver"); // May not be needed in modern JDBC

            // Step 2: Establish the connection
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            return conn;  // Return the Connection object
        } catch (ClassNotFoundException e) {
            throw new SQLException("JDBC Driver not found", e); // Wrap in SQLException
        }
    }

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {  // Try-with-resources
            System.out.println("Connection successful!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}