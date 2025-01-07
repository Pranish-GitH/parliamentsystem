package src.controller;

import src.model.Parliament;
import src.model.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ParliamentController {

    // Add new Parliament to database
    public static void addParliament(Parliament parliament) {
        String query = "INSERT INTO parliament (house_name, house_type) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, parliament.getHouseName());
            stmt.setString(2, parliament.getHouseType());
            stmt.executeUpdate();

            System.out.println("Parliament added successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error adding parliament to database.");
        }
    }

    // Get all Parliaments from database
    public static List<Parliament> getParliaments() {
        List<Parliament> parliaments = new ArrayList<>();
        String query = "SELECT * FROM parliament";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                String houseName = resultSet.getString("house_name");
                String houseType = resultSet.getString("house_type");
                Parliament parliament = new Parliament(houseName, houseType);
                parliaments.add(parliament);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return parliaments;
    }

    // Update Parliament in the database
    public static void updateParliament(int parliamentId, Parliament newParliament) {
        String query = "UPDATE parliament SET house_name = ?, house_type = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, newParliament.getHouseName());
            stmt.setString(2, newParliament.getHouseType());
            stmt.setInt(3, parliamentId);
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Parliament updated successfully.");
            } else {
                System.out.println("No parliament found with the given ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete Parliament from the database
    public static void deleteParliament(int parliamentId) {
        String query = "DELETE FROM parliament WHERE id = ?";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, parliamentId);
            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Parliament deleted successfully.");
            } else {
                System.out.println("No parliament found with the given ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
