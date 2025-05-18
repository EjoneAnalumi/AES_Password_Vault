package repository;

import dto.LoginDTO;
import model.DatabaseManager;
import model.User;
import java.sql.*;

public class UserRepository {
    private final Connection connection;

    public UserRepository() throws SQLException {
        this.connection = DatabaseManager.getConnection();
    }


    public User getUser(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setEncryptedMasterPassword(rs.getString("encrypted_master_password"));
                user.setSalt(rs.getString("salt"));
                user.setEncryptionKey(rs.getString("encryption_key"));
                return user;
            }
            return null;
        }
    }


    public void createUser(User user) throws SQLException {
        String sql = "INSERT INTO users(username, encrypted_master_password, salt, encryption_key) VALUES(?,?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEncryptedMasterPassword());
            stmt.setString(3, user.getSalt());
            stmt.setString(4, user.getEncryptionKey());
            stmt.executeUpdate();
        }
    }
    /*
    public LoginDTO convertToLoginDTO(User user) {
        LoginDTO dto = new LoginDTO();
        dto.setUserId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEncryptionKey(user.getEncryptionKey());
        return dto;
    }*/
}