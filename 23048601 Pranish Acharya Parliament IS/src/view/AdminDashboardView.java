package src.view;

import src.controller.StateController;
import src.model.State;
import src.util.InputValidator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import src.view.HomepageView;

public class AdminDashboardView {
    private final JFrame frame;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final JTextField nameField;
    private final JTextField capitalField;
    private final JTextField headField;
    private final JTextField populationField;
    private final JTextField areaField;
    private final JTextField languagesField;

    public AdminDashboardView() {
        frame = new JFrame("Parliament Administration Dashboard");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Initialize form fields
        nameField = new JTextField(20);
        capitalField = new JTextField(20);
        headField = new JTextField(20);
        populationField = new JTextField(20);
        areaField = new JTextField(20);
        languagesField = new JTextField(20);

        // Create table with non-editable model
        String[] columns = {"ID", "Parliament Name", "Capital", "Head of State", "Population", "Area", "Languages"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);

        // Create form panel
        JPanel formPanel = createFormPanel();

        // Create button panel
        JPanel buttonPanel = createButtonPanel();

        // Add panels to main panel
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add table selection listener
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                populateFormFields();
            }
        });

        frame.add(mainPanel);
        refreshTable();
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add form components
        addFormField(formPanel, "Parliament Name:", nameField, gbc, 0);
        addFormField(formPanel, "Capital:", capitalField, gbc, 1);
        addFormField(formPanel, "Head of State:", headField, gbc, 2);
        addFormField(formPanel, "Population:", populationField, gbc, 3);
        addFormField(formPanel, "Area (sq km):", areaField, gbc, 4);
        addFormField(formPanel, "Languages:", languagesField, gbc, 5);

        return formPanel;
    }

    private void addFormField(JPanel panel, String label, JTextField field, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));

        JButton addButton = new JButton("Add State");
        JButton updateButton = new JButton("Update Selected");
        JButton deleteButton = new JButton("Delete Selected");
        JButton clearButton = new JButton("Clear Fields");
        JButton userViewButton = new JButton("User View");

        addButton.addActionListener(e -> handleAddState());
        updateButton.addActionListener(e -> handleUpdateState());
        deleteButton.addActionListener(e -> handleDeleteState());
        clearButton.addActionListener(e -> clearFields());
        userViewButton.addActionListener(e -> {
            frame.dispose();
            new HomepageView().show();
        });

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(userViewButton);

        return buttonPanel;
    }

    private void handleAddState() {
        try {
            if (validateInputs()) {
                int stateId = StateController.getStates().size() + 1;
                State newState = createStateFromFields(stateId);
                StateController.addState(newState);
                refreshTable();
                clearFields();
                JOptionPane.showMessageDialog(frame, "State added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error adding state: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

// AdminDashboardView.java - Updated methods
private void handleUpdateState() {
    int selectedRow = table.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(frame, 
            "Please select a state to update", 
            "Warning", 
            JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        if (validateInputs()) {
            Integer stateId = (Integer) tableModel.getValueAt(selectedRow, 0);
            if (stateId == null) {
                JOptionPane.showMessageDialog(frame, 
                    "Invalid state ID", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            State updatedState = createStateFromFields(stateId);
            boolean updated = StateController.updateState(stateId, updatedState);

            if (updated) {
                refreshTable();
                JOptionPane.showMessageDialog(frame, 
                    "State updated successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, 
                    "State not found in the database", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    } catch (RuntimeException ex) {
        JOptionPane.showMessageDialog(frame, 
            "Error updating state: " + ex.getMessage(), 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
    }
}

private State createStateFromFields(int stateId) {
    try {
        String name = nameField.getText().trim();
        String capital = capitalField.getText().trim();
        String headOfState = headField.getText().trim();
        long population = Long.parseLong(populationField.getText().trim());
        double area = Double.parseDouble(areaField.getText().trim());
        String languages = languagesField.getText().trim();

        return new State(stateId, name, capital, headOfState, population, area, languages);
    } catch (NumberFormatException e) {
        throw new RuntimeException("Invalid number format: " + e.getMessage());
    }
}

   private void handleDeleteState() {
    int selectedRow = table.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(frame, "Please select a state to delete", "Warning", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        Integer stateId = (Integer) tableModel.getValueAt(selectedRow, 0);
        if (stateId == null) {
            JOptionPane.showMessageDialog(frame, "Invalid state ID", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(frame, 
            "Are you sure you want to delete this state?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            boolean deleted = StateController.deleteState(stateId);
            
            if (deleted) {
                refreshTable();
                clearFields();
                JOptionPane.showMessageDialog(frame, "State deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "State not found in the database", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    } catch (RuntimeException ex) {
        JOptionPane.showMessageDialog(frame, 
            "Error deleting state: " + ex.getMessage(), 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
    }
}

    private void populateFormFields() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            nameField.setText((String) table.getValueAt(selectedRow, 1));
            capitalField.setText((String) table.getValueAt(selectedRow, 2));
            headField.setText((String) table.getValueAt(selectedRow, 3));
            populationField.setText(String.valueOf(table.getValueAt(selectedRow, 4)));
            areaField.setText(String.valueOf(table.getValueAt(selectedRow, 5)));
            languagesField.setText((String) table.getValueAt(selectedRow, 6));
        }
    }

    private boolean validateInputs() {
        return InputValidator.validateString(nameField.getText(), "Parliament Name") &&
               InputValidator.validateString(capitalField.getText(), "Capital") &&
               InputValidator.validateString(headField.getText(), "Head of State") &&
               InputValidator.validateLong(populationField.getText(), "Population") &&
               InputValidator.validateDouble(areaField.getText(), "Area") &&
               InputValidator.validateString(languagesField.getText(), "Languages");
    }

  

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<State> states = StateController.getStates();
        for (State state : states) {
            Object[] row = {
                state.getStateId(),
                state.getName(),
                state.getCapital(),
                state.getHeadOfState(),
                state.getPopulation(),
                state.getArea(),
                state.getLanguages()
            };
            tableModel.addRow(row);
        }
    }

    private void clearFields() {
        nameField.setText("");
        capitalField.setText("");
        headField.setText("");
        populationField.setText("");
        areaField.setText("");
        languagesField.setText("");
        table.clearSelection();
    }

    private void updateState(int stateId) {
        State state = findStateById(stateId);
        if (state != null) {
            state.setName(nameField.getText());
            state.setCapital(capitalField.getText());
            state.setHeadOfState(headField.getText());
            state.setPopulation(Long.parseLong(populationField.getText()));
            state.setArea(Double.parseDouble(areaField.getText()));
            state.setLanguages(languagesField.getText());
            
            StateController.updateState(StateController.getStates().indexOf(state), state);
            refreshTable();
        }
    }

    private State findStateById(int stateId) {
        if (stateId < 0) {
            throw new IllegalArgumentException("Invalid state ID: " + stateId);
        }
        
        return StateController.getStates().stream()
                .filter(s -> s.getStateId() == stateId)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("State not found with ID: " + stateId));
    }

    public void display() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}