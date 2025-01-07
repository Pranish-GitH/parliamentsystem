package src.controller;

import src.model.UserModel;
import src.view.LoginRegisterView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class LoginRegisterController {
    private UserModel model;
    private LoginRegisterView view;

    public LoginRegisterController(UserModel model, LoginRegisterView view) {
        this.model = model;
        this.view = view;

        view.addLoginListener(new LoginListener());
        view.addRegisterListener(new RegisterListener());
    }

    class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = view.getUsername();
            String password = view.getPassword();

            String storedHash = model.getPasswordHash(username);
            String storedSalt = model.getSalt(username);

            if (storedHash != null && storedSalt != null) {
                String hashedPassword = hashPassword(password, storedSalt);
                if (hashedPassword.equals(storedHash)) {
                    view.setMessage("Login successful!");
                } else {
                    view.setMessage("Invalid username or password.");
                }
            } else {
                view.setMessage("Invalid username or password.");
            }
        }
    }

    class RegisterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = view.getUsername();
            String password = view.getPassword();

            if (model.userExists(username)) {
                view.setMessage("Username already exists.");
                return;
            }

            String salt = generateSalt();
            String hashedPassword = hashPassword(password, salt);

            if (model.register(username, hashedPassword, salt)) {
                view.setMessage("Registration successful!");
            } else {
                view.setMessage("An error occurred during registration.");
            }
        }
    }

    // Helper method to generate a unique salt
    public static String generateSalt() {
        try {
            SecureRandom random = new SecureRandom();
            byte[] saltBytes = new byte[16];
            random.nextBytes(saltBytes);
            return Base64.getEncoder().encodeToString(saltBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error generating salt: " + e.getMessage(), e);
        }
    }

    // Helper method to hash a password with a salt
    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String saltedPassword = salt + password;
            byte[] hash = digest.digest(saltedPassword.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password: " + e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        UserModel model = new UserModel();
        LoginRegisterView view = new LoginRegisterView();
        new LoginRegisterController(model, view);
        view.setVisible(true);
    }
}
