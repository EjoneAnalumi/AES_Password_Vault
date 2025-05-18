package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.AESEncryption;
import model.DatabaseManager;
import model.User;
import dto.LoginDTO;
import services.PasswordHasher;

import java.io.IOException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Base64;
import javax.crypto.SecretKey;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Button registerButton;
    @FXML private Label errorLabel;

    @FXML
    private void initialize() {
        DatabaseManager.initializeDatabase();
    }

    @FXML
    private void handleLogin() {
//        createDefaultUser();
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Ju lutem plotësoni të gjitha fushat!");
            return;
        }

        try {
            User user = DatabaseManager.getUser(username);
            if (user == null) {
                errorLabel.setText("Përdoruesi nuk ekziston!");
                return;
            }

            String storedPassword = user.getEncryptedMasterPassword();
            String salt = user.getSalt();
            String hashedPassword = PasswordHasher.hashPassword(password, salt);

            if (!hashedPassword.equals(storedPassword)) {
                errorLabel.setText("Fjalëkalimi është i gabuar!");
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mainpage.fxml"));
            Parent root = loader.load();

            LoginDTO loginDTO = new LoginDTO();
            loginDTO.setUserId(user.getId());
            loginDTO.setUsername(user.getUsername());
            loginDTO.setEncryptionKey(user.getEncryptionKey());

            MainController mainController = loader.getController();
            mainController.initData(loginDTO);

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Password Manager - " + username);

        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Gabim gjatë ngarkimit të ndërfaqes!");
        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("Gabim i papritur: " + e.getMessage());
        }
    }

    @FXML
    private void handleRegister() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (!validateRegistrationInput(username, password)) {
            return;
        }

        try {
            if (userExists(username)) {
                errorLabel.setText("Përdoruesi ekziston tashmë!");
                return;
            }

            User newUser = createNewUser(username, password);
            boolean success = DatabaseManager.addUser(newUser);

            if (success) {
                showSuccessMessage("Regjistrimi u krye me sukses! Tani mund të hyni.");
            } else {
                errorLabel.setText("Gabim në regjistrim!");
            }

        } catch (Exception e) {
            if (e instanceof SQLException) {
                handleDatabaseError((SQLException) e);
            } else {
                handleUnexpectedError(e);
            }
        }
    }

    private boolean validateRegistrationInput(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Ju lutem plotësoni të gjitha fushat!");
            return false;
        }
        if (username.length() < 4) {
            errorLabel.setText("Emri i përdoruesit duhet të ketë të paktën 4 karaktere!");
            return false;
        }
        if (password.length() < 8) {
            errorLabel.setText("Fjalëkalimi duhet të ketë të paktën 8 karaktere!");
            return false;
        }
        return true;
    }

    private boolean userExists(String username) throws SQLException {
        return DatabaseManager.getUser(username) != null;
    }

    private User createNewUser(String username, String password) throws Exception {
        String salt = PasswordHasher.generateSalt();
        String hashedPassword = PasswordHasher.hashPassword(password, salt);
        SecretKey key = AESEncryption.generateKey();
        String encryptionKey = AESEncryption.keyToString(key);
        return new User(username, hashedPassword, salt, encryptionKey);
    }

    private void showSuccessMessage(String message) {
        errorLabel.setStyle("-fx-text-fill: green;");
        errorLabel.setText(message);
    }

    private void handleDatabaseError(SQLException e) {
        e.printStackTrace();
        errorLabel.setStyle("-fx-text-fill: red;");
        if (e.getMessage().contains("duplicate key")) {
            errorLabel.setText("Ky emër përdoruesi ekziston tashmë!");
        } else {
            errorLabel.setText("Gabim në lidhje me bazën e të dhënave: " + e.getMessage());
        }
    }

    private void handleUnexpectedError(Exception e) {
        e.printStackTrace();
        errorLabel.setStyle("-fx-text-fill: red;");
        errorLabel.setText("Gabim i papritur: " + e.getMessage());
    }

//    private void createDefaultUser() {
//        try {
//            String username = "admin";
//            String password = "admin123";
//            String salt = PasswordHasher.generateSalt();
//            String hashedPassword = PasswordHasher.hashPassword(password, salt);
//            String encryptionKey = AESEncryption.keyToString(AESEncryption.generateKey());
//
//            if (DatabaseManager.getUser(username) == null) {
//                User defaultUser = new User(username, hashedPassword, salt, encryptionKey);
//                DatabaseManager.addUser(defaultUser);
//                System.out.println("Përdoruesi default u krijua!");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}