package repository;

import dto.LoginDTO;
import model.DatabaseManager;
import model.User;

public class UserRepository {

    public UserRepository() {
        // file-based storage - nuk duhet objekt per connection
    }

    public User getUser(String username) {
        return DatabaseManager.getUser(username);
    }

    public boolean createUser(User user) {
        return DatabaseManager.addUser(user);
    }

    public LoginDTO convertToLoginDTO(User user) {
        LoginDTO dto = new LoginDTO();
        dto.setUserId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEncryptionKey(user.getEncryptionKey());
        return dto;
    }
}