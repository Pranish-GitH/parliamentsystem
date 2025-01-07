package src;

import src.controller.LoginRegisterController;
import src.model.UserModel;
import src.view.LoginRegisterView;
import src.view.HomepageView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginRegisterView loginRegisterView = new LoginRegisterView();
            UserModel userModel = new UserModel();

            loginRegisterView.setVisible(true);

            loginRegisterView.addLoginListener(e -> {
                String username = loginRegisterView.getUsername();
                String password = loginRegisterView.getPassword();

                // Retrieve the salt and stored hash for the username
                String storedSalt = userModel.getSalt(username);
                if (storedSalt != null) {
                    // Hash the entered password with the retrieved salt
                    String hashedPassword = LoginRegisterController.hashPassword(password, storedSalt);
                    String storedHash = userModel.getPasswordHash(username);

                    if (hashedPassword.equals(storedHash)) {
                        loginRegisterView.setMessage("Login successful!");
                        // Navigate to the next screen
                        HomepageView homePageView = new HomepageView();
                        homePageView.show();
                        loginRegisterView.dispose();
                    } else {
                        loginRegisterView.setMessage("Invalid username or password.");
                    }
                } else {
                    loginRegisterView.setMessage("Invalid username or password.");
                }
            });

            loginRegisterView.addRegisterListener(e -> {
                String username = loginRegisterView.getUsername();
                String password = loginRegisterView.getPassword();

                // Generate salt and hash the password
                String salt = LoginRegisterController.generateSalt();
                String hashedPassword = LoginRegisterController.hashPassword(password, salt);

                if (userModel.register(username, hashedPassword, salt)) {
                    loginRegisterView.setMessage("Registration successful!");
                } else {
                    loginRegisterView.setMessage("Username already exists.");
                }
            });
        });
    }
}
