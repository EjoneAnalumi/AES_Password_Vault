package utils;

public class PasswordStrengthChecker {
    public enum Strength {
        WEAK, MEDIUM, STRONG
    }

    /**
     * Very simple scoring:
     * • +1 if ≥8 chars
     * • +1 if has lowercase
     * • +1 if has uppercase
     * • +1 if has digit
     * • +1 if has special character
     *
     * 0–2 ⇒ WEAK, 3–4 ⇒ MEDIUM, 5 ⇒ STRONG
     */
    public static Strength calculateStrength(String password) {
        int score = 0;
        if (password.length() >= 8)
            score++;
        if (password.matches(".*[a-z].*"))
            score++;
        if (password.matches(".*[A-Z].*"))
            score++;
        if (password.matches(".*\\d.*"))
            score++;
        if (password.matches(".*[!@#$%^&*()\\-+].*"))
            score++;

        if (score <= 2)   return Strength.WEAK;
        if (score <= 4)   return Strength.MEDIUM;
        return Strength.STRONG;
    }
}
