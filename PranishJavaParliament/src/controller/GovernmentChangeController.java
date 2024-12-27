package src.controller;

import src.model.DatabaseConnection;
import src.model.GovernmentChange;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GovernmentChangeController {

    public static List<GovernmentChange> getGovernmentChanges() {
        List<GovernmentChange> changes = new ArrayList<>();
        try (Connection connection = DatabaseConnection.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM GovernmentChanges")) {

            while (resultSet.next()) {
                changes.add(new GovernmentChange(
                        resultSet.getInt("changeId"),
                        resultSet.getInt("stateId"),
                        resultSet.getString("dateOfChange"),
                        resultSet.getString("description")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return changes;
    }

    //delete
    public static void deleteGovernmentChange(int changeId) {
        try (Connection connection = DatabaseConnection.connect();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM GovernmentChanges WHERE changeId = " + changeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
