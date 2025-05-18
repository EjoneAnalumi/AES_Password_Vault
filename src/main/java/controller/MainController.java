package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import dto.LoginDTO;
import dto.PasswordEntryDTO;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Clipboard;
import model.*;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class MainController {
    @FXML private TableView<PasswordEntryDTO> passwordTable;
    @FXML private TableColumn<PasswordEntryDTO, String> websiteColumn;
    @FXML private TableColumn<PasswordEntryDTO, String> usernameColumn;
    @FXML private TableColumn<PasswordEntryDTO, String> strengthColumn;

    @FXML private TextField websiteField;
    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private RadioButton weakRadio;
    @FXML private RadioButton mediumRadio;
    @FXML private RadioButton strongRadio;
    @FXML private Button generateButton;
    @FXML private Button saveButton;
    @FXML private Button deleteButton;
    @FXML private Button copyButton;
    @FXML private Label statusLabel;

    private int currentUserId;
    private SecretKey encryptionKey;
    private ObservableList<PasswordEntryDTO> passwordData;

    public void initData(LoginDTO loginDTO) {
        this.currentUserId = loginDTO.getUserId();
        this.encryptionKey = AESEncryption.stringToKey(loginDTO.getEncryptionKey());

        websiteColumn.setCellValueFactory(new PropertyValueFactory<>("website"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        strengthColumn.setCellValueFactory(new PropertyValueFactory<>("strength"));

        loadPasswordData();
    }

    private void loadPasswordData() {
        List<PasswordEntry> entries = DatabaseManager.getPasswordEntries(currentUserId);

        List<PasswordEntryDTO> entryDTOs = entries.stream()
                .map(entry -> {
                    try {
                        String decryptedPassword = AESEncryption.decrypt(
                                entry.getEncryptedPassword(),
                                encryptionKey,
                                new IvParameterSpec(Base64.getDecoder().decode(entry.getIv()))
                        );
                        return new PasswordEntryDTO(
                                entry.getId(),
                                entry.getWebsite(),
                                entry.getUsername(),
                                decryptedPassword,
                                entry.getStrength()
                        );
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        return null;
                    }
                })
                .filter(dto -> dto != null)
                .collect(Collectors.toList());

        passwordData = FXCollections.observableArrayList(entryDTOs);
        passwordTable.setItems(passwordData);
    }

    @FXML
    private void handleGeneratePassword() {
        PasswordGenerator.PasswordStrength strength = getSelectedStrength();
        String generatedPassword = PasswordGenerator.generatePassword(strength);
        passwordField.setText(generatedPassword);
    }

    private PasswordGenerator.PasswordStrength getSelectedStrength() {
        if (weakRadio.isSelected()) return PasswordGenerator.PasswordStrength.WEAK;
        if (mediumRadio.isSelected()) return PasswordGenerator.PasswordStrength.MEDIUM;
        return PasswordGenerator.PasswordStrength.STRONG;
    }

    @FXML
    private void handleSavePassword() {
        String website = websiteField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String strength = getSelectedStrength().toString();

        if (website.isEmpty() || username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please fill all fields!");
            return;
        }

        try {
            IvParameterSpec iv = AESEncryption.generateIv();
            String encryptedPassword = AESEncryption.encrypt(password, encryptionKey, iv);
            String ivString = Base64.getEncoder().encodeToString(iv.getIV());

            PasswordEntry newEntry = new PasswordEntry(
                    currentUserId, website, username, encryptedPassword, ivString, strength);

            DatabaseManager.addPasswordEntry(currentUserId, newEntry);
            loadPasswordData();

            websiteField.clear();
            usernameField.clear();
            passwordField.clear();

            statusLabel.setText("Password saved successfully!");
        } catch (Exception e) {
            statusLabel.setText("Error saving password!");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeletePassword() {
        PasswordEntryDTO selectedEntry = passwordTable.getSelectionModel().getSelectedItem();
        if (selectedEntry == null) {
            statusLabel.setText("Please select a password to delete!");
            return;
        }

        boolean success = DatabaseManager.deletePasswordEntry(selectedEntry.getId());
        if (success) {
            loadPasswordData();
            statusLabel.setText("Password deleted successfully!");
        } else {
            statusLabel.setText("Error deleting password!");
        }
    }


    @FXML
    private void handleCopyPassword() {
        PasswordEntryDTO selectedEntry = passwordTable.getSelectionModel().getSelectedItem();

        if (selectedEntry == null) {
            statusLabel.setText("Please select a password to copy!");
            return;
        }

        String password = selectedEntry.getPassword();
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(password);
        clipboard.setContent(content);

        statusLabel.setText("Password copied to clipboard!");
    }
}