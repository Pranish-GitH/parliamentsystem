package src.view;

import src.controller.StateController;
import src.model.State;
import src.util.InputValidator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import src.view.LoginRegisterView;

public class HomepageView {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField; // Search field for states
    private JComboBox<String> sortComboBox; // Dropdown for sorting options

    public HomepageView() {
        frame = new JFrame("Parliament Information System");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("States Directory");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton createButton = createStyledButton("Add New State", Color.BLACK);
        JButton adminButton = createStyledButton("Admin Dashboard", Color.BLACK);
        JButton logoutButton = createStyledButton("Logout", Color.RED);

        buttonPanel.add(createButton);
        buttonPanel.add(adminButton);
        buttonPanel.add(logoutButton);
        headerPanel.add(buttonPanel, BorderLayout.EAST);

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel searchLabel = new JLabel("Search by State Name:");
        searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);

        // Sort Panel
        JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel sortLabel = new JLabel("Sort by:");
        sortComboBox = new JComboBox<>(new String[]{"Select", "State Name", "Capital", "Head of State"});
        sortPanel.add(sortLabel);
        sortPanel.add(sortComboBox);

        headerPanel.add(searchPanel, BorderLayout.CENTER);
        headerPanel.add(sortPanel, BorderLayout.SOUTH);

        // Table setup
        String[] columns = {"ID", "State Name", "Capital", "Head of State"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        JScrollPane scrollPane = new JScrollPane(table);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Action Listeners
        createButton.addActionListener(e -> createState());
        adminButton.addActionListener(e -> openAdminDashboard());
        logoutButton.addActionListener(e -> logout());

        // Adding a listener to the search field to filter results in real-time
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filterTable(searchField.getText());
            }
        });

        // Adding listener for the sorting dropdown
        sortComboBox.addActionListener(e -> {
    System.out.println("Sorting by: " + sortComboBox.getSelectedItem()); // Optional debug
    sortTable();
});

        frame.add(mainPanel);
        refreshTable();
        frame.setLocationRelativeTo(null);
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(150, 40));
        return button;
    }

    private void createState() {
        JTextField nameField = new JTextField();
        JTextField capitalField = new JTextField();
        JTextField headField = new JTextField();
        JTextField populationField = new JTextField();
        JTextField areaField = new JTextField();
        JTextField languagesField = new JTextField();

        Object[] fields = {
            "State Name:", nameField,
            "Capital:", capitalField,
            "Head of State:", headField,
            "Population:", populationField,
            "Area:", areaField,
            "Languages:", languagesField
        };

        int result = JOptionPane.showConfirmDialog(frame, fields, "Add New State",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
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
            }
        }
    }

    private boolean validateInputs(String name, String capital, String head, String populationText, String areaText, String languages) {
        if (!InputValidator.isNotEmpty(name)) {
            showError("State Name cannot be empty.");
            return false;
        }
        if (!InputValidator.isNotEmpty(capital)) {
            showError("Capital cannot be empty.");
            return false;
        }
        if (!InputValidator.isNotEmpty(head)) {
            showError("Head of State cannot be empty.");
            return false;
        }
        if (!InputValidator.isLong(populationText)) {
            showError("Population must be a valid number.");
            return false;
        }
        if (!InputValidator.isNumeric(areaText)) {
            showError("Area must be a valid number.");
            return false;
        }
        if (!InputValidator.isNotEmpty(languages)) {
            showError("Languages cannot be empty.");
            return false;
        }
        return true;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Input Error", JOptionPane.ERROR_MESSAGE);
    }
private void refreshTable() {
    tableModel.setRowCount(0); // Clear existing rows

    List<State> states = StateController.getStates();
    for (State state : states) {
        Object[] row = {state.getStateId(), state.getName(), state.getCapital(), state.getHeadOfState()};
        tableModel.addRow(row);
    }

    tableModel.fireTableDataChanged(); // Update the model

    // Force a repaint of the table view to reflect changes
    table.revalidate();
    table.repaint();
}





    private void filterTable(String query) {
        tableModel.setRowCount(0);
        List<State> states = StateController.getStates();

        // Filter the states by name
        for (State state : states) {
            if (state.getName().toLowerCase().contains(query.toLowerCase())) {
                Object[] row = {state.getStateId(), state.getName(), state.getCapital(), state.getHeadOfState()};
                tableModel.addRow(row);
            }
        }
    }

private void sortTable() {
    String selectedOption = (String) sortComboBox.getSelectedItem();
    List<State> states = StateController.getStates(); // Get the current list of states

    if (selectedOption == null || selectedOption.equals("Select")) {
        return; // No sorting if 'Select' is chosen
    }

    // Sort the states based on the selected option
    switch (selectedOption) {
        case "State Name":
            Collections.sort(states, Comparator.comparing(State::getName, String.CASE_INSENSITIVE_ORDER));
            break;
        case "Capital":
            Collections.sort(states, Comparator.comparing(State::getCapital, String.CASE_INSENSITIVE_ORDER));
            break;
        case "Head of State":
            Collections.sort(states, Comparator.comparing(State::getHeadOfState, String.CASE_INSENSITIVE_ORDER));
            break;
        default:
            break;
    }

    // Clear existing rows and re-add the sorted rows
    tableModel.setRowCount(0); // Clear existing rows
    for (State state : states) {
        Object[] row = {state.getStateId(), state.getName(), state.getCapital(), state.getHeadOfState()};
        tableModel.addRow(row); // Add each state row
    }

    // Fire table data change and ensure it updates the table visually
    tableModel.fireTableDataChanged();
    table.revalidate();
    table.repaint();

    // Debugging print
    System.out.println("Table sorted and refreshed.");
}





    private void openAdminDashboard() {
        new AdminDashboardView().display();
        frame.dispose();
    }

    private void logout() {
        // Implement logout functionality here
        LoginRegisterView loginRegisterView = new LoginRegisterView();
        loginRegisterView.show();
        
    }

    public void show() {
        frame.setVisible(true);
    }
}
