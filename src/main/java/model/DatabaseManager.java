package model;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class DatabaseManager {
    private static final String USER_FILE     = "users.csv";
    private static final String PASSWORD_FILE = "passwords.csv";

    public static void initializeDatabase() {
        try {
            if (!Files.exists(Paths.get(USER_FILE))) {
                Files.createFile(Paths.get(USER_FILE));
            }
            if (!Files.exists(Paths.get(PASSWORD_FILE))) {
                Files.createFile(Paths.get(PASSWORD_FILE));
            }
            System.out.println("Databaza e file-ve u inicializua");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Deshtoi inicializimi i databazes se file-ve", e);
        }
    }

    private static synchronized int getNextUserId() {
        int max = 0;
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                int id = Integer.parseInt(parts[0]);
                if (id > max) max = id;
            }
        } catch (IOException ignored) { }
        return max + 1;
    }

    private static synchronized int getNextPasswordId() {
        int max = 0;
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(PASSWORD_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                int id = Integer.parseInt(parts[0]);
                if (id > max) max = id;
            }
        } catch (IOException ignored) { }
        return max + 1;
    }

    public static boolean addUser(User user) {
        int id = getNextUserId();
        user.setId(id);
        String record = String.join(",",
                String.valueOf(id),
                user.getUsername(),
                user.getEncryptedMasterPassword(),
                user.getSalt(),
                user.getEncryptionKey()
        );
        return appendLine(USER_FILE, record);
    }

    public static User getUser(String username) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = line.split(",", -1);
                if (p[1].equals(username)) {
                    User u = new User();
                    u.setId(Integer.parseInt(p[0]));
                    u.setUsername(p[1]);
                    u.setEncryptedMasterPassword(p[2]);
                    u.setSalt(p[3]);
                    u.setEncryptionKey(p[4]);
                    return u;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean addPasswordEntry(int userId, PasswordEntry entry) {
        int id = getNextPasswordId();
        entry.setId(id);
        String record = String.join(",",
                String.valueOf(id),
                String.valueOf(userId),
                entry.getWebsite(),
                entry.getUsername(),
                entry.getEncryptedPassword(),
                entry.getIv(),
                entry.getStrength()
        );
        return appendLine(PASSWORD_FILE, record);
    }

    public static List<PasswordEntry> getPasswordEntries(int userId) {
        List<PasswordEntry> list = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(PASSWORD_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = line.split(",", -1);
                if (Integer.parseInt(p[1]) == userId) {
                    PasswordEntry e = new PasswordEntry();
                    e.setId(Integer.parseInt(p[0]));
                    e.setUserId(userId);
                    e.setWebsite(p[2]);
                    e.setUsername(p[3]);
                    e.setEncryptedPassword(p[4]);
                    e.setIv(p[5]);
                    e.setStrength(p[6]);
                    list.add(e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static boolean deletePasswordEntry(int entryId) {
        try {
            Path path = Paths.get(PASSWORD_FILE);
            List<String> all = Files.readAllLines(path);
            List<String> kept = new ArrayList<>();
            for (String line : all) {
                String[] p = line.split(",", -1);
                if (Integer.parseInt(p[0]) != entryId) {
                    kept.add(line);
                }
            }
            Files.write(path, kept);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean testConnection() {
        return Files.exists(Paths.get(USER_FILE)) && Files.exists(Paths.get(PASSWORD_FILE));
    }


    private static boolean appendLine(String filename, String line) {
        try (BufferedWriter writer = Files.newBufferedWriter(
                Paths.get(filename),
                StandardOpenOption.APPEND)) {
            writer.write(line);
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}