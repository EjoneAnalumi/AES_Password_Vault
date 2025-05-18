package dto;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PasswordEntryDTO {
    private final int id;
    private final StringProperty website;
    private final StringProperty username;
    private final StringProperty password;
    private final StringProperty strength;
    public PasswordEntryDTO(int id, String website, String username,
                            String password, String strength) {
        this.id = id;
        this.website = new SimpleStringProperty(website);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.strength = new SimpleStringProperty(strength);
    }

    // Getters
    public int getId() { return id; }
    public String getWebsite() { return website.get(); }
    public String getUsername() { return username.get(); }
    public String getPassword() { return password.get(); }
    public String getStrength() { return strength.get(); }

    // Property getters for TableView
    public StringProperty websiteProperty() { return website; }
    public StringProperty usernameProperty() { return username; }
    public StringProperty strengthProperty() { return strength; }
}
