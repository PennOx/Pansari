package project.pansari.models;

import android.util.Patterns;

import java.util.regex.Pattern;

public class LoginCredential {

    private String email;
    private String password;
    private int validStatus;
    private String message;

    public LoginCredential(String email, String password) {
        this.email = email;
        this.password = password;

        if (email.isEmpty()) {
            validStatus = 1;
            message = "Email required";
        } else if (password.isEmpty()) {
            validStatus = 2;
            message = "Password required";
        } else if (!Pattern.matches(String.valueOf(Patterns.EMAIL_ADDRESS), email)) {
            validStatus = 1;
            message = "Invalid email address";

        } else if (password.length() < 6 || password.length() > 12) {
            validStatus = 2;
            message = "Password should be at least 6 to 12 characters long";
        } else {
            validStatus = 0;
        }
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getValidStatus() {
        return validStatus;
    }

    public String getMessage() {
        return message;
    }
}
