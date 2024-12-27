package src.controller;

import src.model.State;

import java.util.ArrayList;
import java.util.List;

public class StateController {
    private static List<State> states = new ArrayList<>();

    // Add a new state
    public static void addState(State state) {
        if (state == null) {
            System.out.println("Cannot add a null state.");
            return;
        }
        states.add(state);
        System.out.println("State added: " + state.getName());
    }

    // Get all states
    public static List<State> getStates() {
        return states;
    }

    // Find a state by stateId
    public static State findStateById(int stateId) {
        for (State state : states) {
            if (state.getStateId() == stateId) {
                return state;
            }
        }
        System.out.println("State with ID " + stateId + " not found.");
        return null;
    }

    // Update an existing state by stateId
    public static void updateState(int stateId, State newState) {
        State existingState = findStateById(stateId);
        if (existingState != null) {
            int index = states.indexOf(existingState);
            if (index != -1) {
                states.set(index, newState);
                System.out.println("State updated: " + newState.getName());
            }
        } else {
            System.out.println("State with ID " + stateId + " not found.");
        }
    }

    // Delete a state by stateId
    public static void deleteState(int stateId) {
        State state = findStateById(stateId);
        if (state != null) {
            states.remove(state);
            System.out.println("State deleted: " + state.getName());
        } else {
            System.out.println("State with ID " + stateId + " not found.");
        }
    }

    // Display all states
    public static void displayStates() {
        if (states.isEmpty()) {
            System.out.println("No states available.");
        } else {
            for (State state : states) {
                System.out.println(state);
            }
        }
    }
}
