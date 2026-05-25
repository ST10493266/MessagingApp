/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package com.mycompany.registration;
import java.util.regex.Pattern;
/**
 *
 * @author Student
 */
public class Login {
     // Stored credentials (set during registration)
    private String registeredUsername;
    private String registeredPassword;
    private String registeredCellPhone;
    private String firstName;
    private String lastName;

    //  CONSTRUCTOR
    public Login(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

  
    //  1. checkUserName()
    public boolean checkUserName(String username) {
        return username != null
                && username.contains("_")
                && username.length() <= 5;
    }

   
    //  2. checkPasswordComplexity()
    public boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) return false;
        boolean hasUpper   = password.chars().anyMatch(Character::isUpperCase);
        boolean hasDigit   = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecial = password.chars().anyMatch(c -> !Character.isLetterOrDigit(c));
        return hasUpper && hasDigit && hasSpecial;
    }

    
    //  3. checkCellPhoneNumber()
    public boolean checkCellPhoneNumber(String cellPhone) {
        if (cellPhone == null) return false;
        return Pattern.matches("^\\+27\\d{1,7}$", cellPhone)
                && cellPhone.length() <= 10;
    }

    
    //  4. registerUser()
    
    public String registerUser(String username, String password, String cellPhone) {

        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username " +
                   "contains an underscore and is no more than five characters in length.";
        }

        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password " +
                   "contains at least eight characters, a capital letter, a number, and a special character.";
        }

        if (!checkCellPhoneNumber(cellPhone)) {
            return "Cell number is incorrectly formatted or does not contain an international code; " +
                   "please correct the number and try again.";
        }

        // All checks passed — store credentials
        this.registeredUsername = username;
        this.registeredPassword = password;
        this.registeredCellPhone = cellPhone;

        return firstName + " " + lastName + " has been registered successfully.";
    }

   
    //  5. loginUser()
    public boolean loginUser(String username, String password) {
        return username != null
                && password != null
                && username.equals(registeredUsername)
                && password.equals(registeredPassword);
    }

    
    //  6. returnLoginStatus()
    public String returnLoginStatus(String username, String password) {
        if (loginUser(username, password)) {
            return "Welcome " + firstName + " " + lastName + ", it is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }

   
    //  Getters (useful for tests)
    public String getRegisteredUsername()  { return registeredUsername; }
    public String getRegisteredCellPhone() { return registeredCellPhone; }
}

