package src.controller;

import src.model.Parliament;
import java.util.ArrayList;
import java.util.List;

public class ParliamentController {
    private static List<Parliament> parliaments = new ArrayList<>();

    // Add new Parliament
    public static void addParliament(Parliament parliament) {
        parliaments.add(parliament);
    }

    // Get all Parliaments
    public static List<Parliament> getParliaments() {
        return parliaments;
    }

    // Update Parliament
    public static void updateParliament(int index, Parliament newParliament) {
        parliaments.set(index, newParliament);
    }

    // Delete Parliament
    public static void deleteParliament(int index) {
        parliaments.remove(index);
    }

}
