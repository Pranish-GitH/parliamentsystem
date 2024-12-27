package src.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/parliament";
    private static final String USER = "root";
    private static final String PASSWORD = "root"; // Replace with your DB password

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
