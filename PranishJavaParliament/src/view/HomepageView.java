package src.view;

import src.controller.StateController;
import src.model.State;
import src.util.InputValidator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HomepageView {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;

    public HomepageView() {
        frame = new JFrame("State Information System");
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
        buttonPanel.add(createButton);
        buttonPanel.add(adminButton);
        headerPanel.add(buttonPanel, BorderLayout.EAST);

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
        tableModel.setRowCount(0);
        List<State> states = StateController.getStates();
        for (State state : states) {
            Object[] row = {state.getStateId(), state.getName(), state.getCapital(), state.getHeadOfState()};
            tableModel.addRow(row);
        }
    }

    private void openAdminDashboard() {
        new AdminDashboardView().display();
        frame.dispose();
    }

    public void show() {
        frame.setVisible(true);
    }
}
