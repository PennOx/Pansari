package project.pansari.Models;

import android.util.Patterns;

import java.util.regex.Pattern;

public class SignupCredential {

    private String name;
    private String shopName;
    private String email;
    private String contactNumber;
    private String shopAddress;
    private int pinCode;
    private String password;
    private String confirmPassword;

    private int validStatus;
    private String message;

    public SignupCredential(String name, String shopName, String email, String contactNumber, String shopAddress, String pinCode, String password, String confirmPassword) {
        this.name = name;
        this.shopName = shopName;
        this.email = email;
        this.contactNumber = contactNumber;
        this.shopAddress = shopAddress;
        this.password = password;
        this.confirmPassword = confirmPassword;

        if (name.isEmpty()) {
            validStatus = 1;
            message = "Name require";
        } else if (shopName.isEmpty()) {
            validStatus = 2;
            message = "Shop name require";
        } else if (email.isEmpty()) {
            validStatus = 3;
            message = "Email require";
        } else if (!Pattern.matches(String.valueOf(Patterns.EMAIL_ADDRESS), email)) {
            validStatus = 3;
            message = "Invalid email address";
        } else if (contactNumber.isEmpty()) {
            validStatus = 4;
            message = "Contact number required";
        } else if (contactNumber.length() != 10) {
            validStatus = 4;
            message = "Invalid contact number";
        } else if (shopAddress.isEmpty()) {
            validStatus = 5;
            message = "Shop name require";
        } else if (pinCode.isEmpty()) {
            validStatus = 6;
            message = "Area PIN code require";
        } else if (pinCode.length() != 6) {
            validStatus = 6;
            message = "Invalid area PIN code";
        } else if (password.isEmpty()) {
            validStatus = 7;
            message = "Password required";
        } else if (password.length() < 6 || password.length() > 12) {
            validStatus = 7;
            message = "Password should be at least 6 to 12 characters long";
        } else if (confirmPassword.isEmpty()) {
            validStatus = 8;
            message = "Confirm password";
        } else if (!confirmPassword.equals(password)) {
            validStatus = 8;
            message = "Password mismatched";
        } else {
            validStatus = 0;
            this.pinCode = Integer.parseInt(pinCode);
        }
    }

    public int getValidStatus() {
        return validStatus;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    public String getShopName() {
        return shopName;
    }

    public String getEmail() {
        return email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public int getPinCode() {
        return pinCode;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}
