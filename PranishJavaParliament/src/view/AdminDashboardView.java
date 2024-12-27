package src.view;

import src.controller.StateController;
import src.model.State;
import src.util.InputValidator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminDashboardView {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;

    public AdminDashboardView() {
        frame = new JFrame("Parliament Administration Dashboard");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create table
        String[] columns = {"ID", "Parliament Name", "Capital", "Head of State", "Population", "Area", "Languages"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);

        // Form panel for input fields
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JTextField nameField = new JTextField(20);
        JTextField capitalField = new JTextField(20);
        JTextField headField = new JTextField(20);
        JTextField populationField = new JTextField(20);
        JTextField areaField = new JTextField(20);
        JTextField languagesField = new JTextField(20);

        // Add form components
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Parliament Name:"), gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Capital:"), gbc);
        gbc.gridx = 1;
        formPanel.add(capitalField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Head of State:"), gbc);
        gbc.gridx = 1;
        formPanel.add(headField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Population:"), gbc);
        gbc.gridx = 1;
        formPanel.add(populationField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Area (sq km):"), gbc);
        gbc.gridx = 1;
        formPanel.add(areaField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Languages:"), gbc);
        gbc.gridx = 1;
        formPanel.add(languagesField, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton addButton = new JButton("Add State");
        JButton updateButton = new JButton("Update Selected");
        JButton deleteButton = new JButton("Delete Selected");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        // Add panels to main panel
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String capital = capitalField.getText();
            String head = headField.getText();
            String populationText = populationField.getText();
            String areaText = areaField.getText();
            String languages = languagesField.getText();

            if (validateInputs(name, capital, head, populationText, areaText, languages)) {
                long population = Long.parseLong(populationText);
                double area = Double.parseDouble(areaText);
                int stateId = StateController.getStates().size() + 1;
                State newState = new State(stateId, name, capital, head, population, area, languages);
                StateController.addState(newState);
                refreshTable();
                clearFields(nameField, capitalField, headField, populationField, areaField, languagesField);
            }
        });

        updateButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (InputValidator.validateSelection(selectedRow, "update")) {
                int stateId = (int) table.getValueAt(selectedRow, 0);
                String name = nameField.getText();
                String capital = capitalField.getText();
                String head = headField.getText();
                String populationText = populationField.getText();
                String areaText = areaField.getText();
                String languages = languagesField.getText();

                if (validateInputs(name, capital, head, populationText, areaText, languages)) {
                    updateState(stateId, name, capital, head, populationText, areaText, languages);
                }
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (InputValidator.validateSelection(selectedRow, "delete")) {
                int stateId = (int) table.getValueAt(selectedRow, 0);
                deleteState(stateId);
            }
        });

        frame.add(mainPanel);
        refreshTable();
    }

    private boolean validateInputs(String name, String capital, String head, String populationText, String areaText, String languages) {
        return InputValidator.validateString(name, "Parliament Name") &&
               InputValidator.validateString(capital, "Capital") &&
               InputValidator.validateString(head, "Head of State") &&
               InputValidator.validateLong(populationText, "Population") &&
               InputValidator.validateDouble(areaText, "Area") &&
               InputValidator.validateString(languages, "Languages");
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<State> states = StateController.getStates();
        for (State state : states) {
            Object[] row = {state.getStateId(), state.getName(), state.getCapital(),
                    state.getHeadOfState(), state.getPopulation(), state.getArea(), state.getLanguages()};
            tableModel.addRow(row);
        }
    }

    private void clearFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }

    private void updateState(int stateId, String name, String capital, String head, String populationText, String areaText, String languages) {
        State state = StateController.getStates().stream()
                .filter(s -> s.getStateId() == stateId)
                .findFirst()
                .orElse(null);

        if (state != null) {
            long population = Long.parseLong(populationText);
            double area = Double.parseDouble(areaText);
            state.setName(name);
            state.setCapital(capital);
            state.setHeadOfState(head);
            state.setPopulation(population);
            state.setArea(area);
            state.setLanguages(languages);
            StateController.updateState(StateController.getStates().indexOf(state), state);
            refreshTable();
        }
    }

    private void deleteState(int stateId) {
        State state = StateController.getStates().stream()
                .filter(s -> s.getStateId() == stateId)
                .findFirst()
                .orElse(null);

        if (state != null) {
            StateController.deleteState(StateController.getStates().indexOf(state));
            refreshTable();
        }
    }

    public void display() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
