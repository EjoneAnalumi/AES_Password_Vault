package model;

public class PasswordEntry {
    private int id;
    private int userId;
    private String website;
    private String username;
    private String encryptedPassword;
    private String iv;
    private String strength; // Now only stores password strength

    public PasswordEntry() {}

    public PasswordEntry(int userId, String website, String username,
                         String encryptedPassword, String iv, String strength) {
        this.userId = userId;
        this.website = website;
        this.username = username;
        this.encryptedPassword = encryptedPassword;
        this.iv = iv;
        this.strength = strength;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEncryptedPassword() { return encryptedPassword; }
    public void setEncryptedPassword(String encryptedPassword) { this.encryptedPassword = encryptedPassword; }
    public String getIv() { return iv; }
    public void setIv(String iv) { this.iv = iv; }
    public String getStrength() { return strength; }
    public void setStrength(String strength) { this.strength = strength; }
}