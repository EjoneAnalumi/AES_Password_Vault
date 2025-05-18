package model;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PasswordGenerator {
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "!@#$%^&*()-_=+[]{}|;:'\",.<>?/";

    public static String generatePassword(PasswordStrength strength) {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        List<Character> allChars = new ArrayList<>();

        password.append(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        password.append(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));

        if (strength != PasswordStrength.WEAK) {
            password.append(SPECIAL.charAt(random.nextInt(SPECIAL.length())));
        }

        int length;
        switch (strength) {
            case WEAK:
                length = 8;
                break;
            case MEDIUM:
                length = 12;
                break;
            case STRONG:
                length = 16;
                break;
            default:
                length = 12;
        }

        allChars.addAll(convertToCharList(LOWERCASE));
        allChars.addAll(convertToCharList(UPPERCASE));
        allChars.addAll(convertToCharList(DIGITS));

        if (strength != PasswordStrength.WEAK) {
            allChars.addAll(convertToCharList(SPECIAL));
        }

        while (password.length() < length) {
            password.append(allChars.get(random.nextInt(allChars.size())));
        }

        List<Character> passwordChars = convertToCharList(password.toString());
        Collections.shuffle(passwordChars, random);

        StringBuilder finalPassword = new StringBuilder();
        passwordChars.forEach(finalPassword::append);

        return finalPassword.toString();
    }

    private static List<Character> convertToCharList(String str) {
        List<Character> chars = new ArrayList<>();
        for (char c : str.toCharArray()) {
            chars.add(c);
        }
        return chars;
    }

    public enum PasswordStrength {
        WEAK, MEDIUM, STRONG
    }
}