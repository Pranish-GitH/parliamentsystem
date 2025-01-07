package src.view;

import src.controller.StateController;
import src.model.State;
import src.util.InputValidator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginRegisterView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel messageLabel;
    private static final Color PRIMARY_COLOR = new Color(70, 130, 180); // Steel Blue
    private static final Color SECONDARY_COLOR = new Color(240, 248, 255); // Alice Blue
    private static final Font MAIN_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 24);

    public LoginRegisterView() {
        setTitle("Welcome");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Create main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        // Create content panel for components
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);

        // Header
        JLabel headerLabel = new JLabel("Sign In", SwingConstants.CENTER);
        headerLabel.setFont(HEADER_FONT);
        headerLabel.setForeground(PRIMARY_COLOR);

        // Username section
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(MAIN_FONT);
        usernameField = createStyledTextField();

        // Password section
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(MAIN_FONT);
        passwordField = createStyledPasswordField();

        // Message label
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        messageLabel.setForeground(Color.RED);

        // Buttons
        loginButton = createStyledButton("Sign In", Color.WHITE, PRIMARY_COLOR);
        registerButton = createStyledButton("Create Account", Color.WHITE, PRIMARY_COLOR);

        // Add components to content panel
        gbc.insets = new Insets(0, 0, 20, 0);
        contentPanel.add(headerLabel, gbc);

        gbc.insets = new Insets(5, 0, 5, 0);
        contentPanel.add(usernameLabel, gbc);
        contentPanel.add(usernameField, gbc);
        
        gbc.insets = new Insets(10, 0, 5, 0);
        contentPanel.add(passwordLabel, gbc);
        gbc.insets = new Insets(5, 0, 15, 0);
        contentPanel.add(passwordField, gbc);

        contentPanel.add(messageLabel, gbc);
        
        gbc.insets = new Insets(10, 0, 10, 0);
        contentPanel.add(loginButton, gbc);
        contentPanel.add(registerButton, gbc);

        // Add content panel to main panel
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Add main panel to frame
        add(mainPanel);
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(300, 35));
        field.setFont(MAIN_FONT);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setPreferredSize(new Dimension(300, 35));
        field.setFont(MAIN_FONT);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }

    private JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(MAIN_FONT);
        button.setPreferredSize(new Dimension(300, 40));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(fgColor),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void setMessage(String message) {
        messageLabel.setText(message);
    }

    public void addLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }

    public void addRegisterListener(ActionListener listener) {
        registerButton.addActionListener(listener);
    }
}