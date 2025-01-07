package src.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {

    /**
     * Registers a new user by saving their username, hashed password, and salt to the database.
     *
     * @param username The username.
     * @param hashedPassword The hashed password.
     * @param salt The salt used for hashing.
     * @return True if the registration was successful, false otherwise.
     */
    public boolean register(String username, String hashedPassword, String salt) {
        String query = "INSERT INTO users (username, password, salt) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            stmt.setString(3, salt);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // Duplicate entry error
                System.out.println("Error: Username already exists.");
                return false;
            }
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks if a username already exists in the database.
     *
     * @param username The username to check.
     * @return True if the user exists, false otherwise.
     */
    public boolean userExists(String username) {
        String query = "SELECT 1 FROM users WHERE username = ?";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet resultSet = stmt.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves the hashed password for a given username.
     *
     * @param username The username.
     * @return The hashed password, or null if the user does not exist.
     */
    public String getPasswordHash(String username) {
        String query = "SELECT password FROM users WHERE username = ?";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves the salt for a given username.
     *
     * @param username The username.
     * @return The salt, or null if the user does not exist.
     */
    public String getSalt(String username) {
        String query = "SELECT salt FROM users WHERE username = ?";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("salt");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
