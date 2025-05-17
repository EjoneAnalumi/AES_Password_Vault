package model;

public class User {
    private int id;
    private String username;
    private String encryptedMasterPassword;
    private String salt;
    private String encryptionKey;

    // Konstruktorë, getters dhe setters
    public User() {}

    public User(String username, String encryptedMasterPassword, String salt, String encryptionKey) {
        this.username = username;
        this.encryptedMasterPassword = encryptedMasterPassword;
        this.salt = salt;
        this.encryptionKey = encryptionKey;
    }

    // Getters dhe Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEncryptedMasterPassword() { return encryptedMasterPassword; }
    public void setEncryptedMasterPassword(String encryptedMasterPassword) { this.encryptedMasterPassword = encryptedMasterPassword; }
    public String getSalt() { return salt; }
    public void setSalt(String salt) { this.salt = salt; }
    public String getEncryptionKey() { return encryptionKey; }
    public void setEncryptionKey(String encryptionKey) { this.encryptionKey = encryptionKey; }
}
