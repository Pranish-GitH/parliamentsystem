package src.controller;

import src.model.State;
import src.model.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StateController {

    



    // Add a new state to the database
    public static void addState(State state) {
        String query = "INSERT INTO state (state_id, name, capital, head_of_state, population, area, languages) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, state.getStateId());
            stmt.setString(2, state.getName());
            stmt.setString(3, state.getCapital());
            stmt.setString(4, state.getHeadOfState());
            stmt.setLong(5, state.getPopulation());
            stmt.setDouble(6, state.getArea());
            stmt.setString(7, state.getLanguages());

            stmt.executeUpdate();
            System.out.println("State added: " + state.getName());

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error adding state to database.");
        }
    }

    // Get all states from the database
    public static List<State> getStates() {
        List<State> states = new ArrayList<>();
        String query = "SELECT * FROM state";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                int stateId = resultSet.getInt("state_id");
                String name = resultSet.getString("name");
                String capital = resultSet.getString("capital");
                String headOfState = resultSet.getString("head_of_state");
                long population = resultSet.getLong("population");
                double area = resultSet.getDouble("area");
                String languages = resultSet.getString("languages");

                State state = new State(stateId, name, capital, headOfState, population, area, languages);
                states.add(state);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return states;
    }

    // Find a state by stateId from the database
    public static State findStateById(int stateId) {
        String query = "SELECT * FROM state WHERE state_id = ?";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, stateId);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String capital = resultSet.getString("capital");
                String headOfState = resultSet.getString("head_of_state");
                long population = resultSet.getLong("population");
                double area = resultSet.getDouble("area");
                String languages = resultSet.getString("languages");

                return new State(stateId, name, capital, headOfState, population, area, languages);
            } else {
                System.out.println("State with ID " + stateId + " not found.");
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Update an existing state in the database by stateId
 public static boolean updateState(int stateId, State newState) {
        String query = "UPDATE state SET name = ?, capital = ?, head_of_state = ?, " +
                      "population = ?, area = ?, languages = ? WHERE state_id = ?";
                      
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            // Set parameters
            stmt.setString(1, newState.getName());
            stmt.setString(2, newState.getCapital());
            stmt.setString(3, newState.getHeadOfState());
            stmt.setLong(4, newState.getPopulation());
            stmt.setDouble(5, newState.getArea());
            stmt.setString(6, newState.getLanguages());
            stmt.setInt(7, stateId);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("State updated successfully: " + newState.getName());
                return true;
            } else {
                System.out.println("State with ID " + stateId + " not found.");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Error updating state with ID " + stateId + ": " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to update state: " + e.getMessage());
        }
    }

    // Delete a state from the database by stateId
   // StateController.java

    
    public static boolean deleteState(int stateId) {
        String query = "DELETE FROM state WHERE state_id = ?";
        
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            
            stmt.setInt(1, stateId);
            int rowsDeleted = stmt.executeUpdate();
            
            if (rowsDeleted > 0) {
                System.out.println("State deleted: ID " + stateId);
                return true;
            } else {
                System.out.println("State with ID " + stateId + " not found.");
                return false;
            }
            
        } catch (SQLException e) {
            System.err.println("Error deleting state with ID " + stateId + ": " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to delete state: " + e.getMessage());
        }
    }


    // Display all states from the database
    public static void displayStates() {
        List<State> states = getStates();
        if (states.isEmpty()) {
            System.out.println("No states available.");
        } else {
            for (State state : states) {
                System.out.println(state);
            }
        }
    }
}
