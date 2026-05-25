/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.registration;
import java.util.Scanner;
/**
 *
 * @author Student
 */
public class Registration {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== User Registration System ===");
        
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        
        Login login = new Login(firstName, lastName);
        
        // Registration process
        System.out.println("\n=== Registration ===");
        
        String username;
        String password;
        String cellPhone;
        String registrationResult;
        
        do {
            System.out.print("Create username (must contain underscore and be ≤5 chars): ");
            username = scanner.nextLine();
            
            System.out.print("Create password (min 8 chars, 1 capital, 1 number, 1 special char): ");
            password = scanner.nextLine();
            
            System.out.print("Enter cell phone number (format: +27XXXXXXXXX): ");
            cellPhone = scanner.nextLine();
            
            registrationResult = login.registerUser(username, password, cellPhone);
            System.out.println(registrationResult);
            
            if (!registrationResult.equals("User registered successfully.")) {
                System.out.println("\nPlease try again with correct format.\n");
            }
            
        } while (!registrationResult.equals("User registered successfully."));
        
        System.out.println("\nRegistration successful!");
        
        // Login process
        System.out.println("\n=== Login ===");
        
        String loginUsername;
        String loginPassword;
        boolean loggedIn = false;
        
        do {
            System.out.print("Enter username: ");
            loginUsername = scanner.nextLine();
            
            System.out.print("Enter password: ");
            loginPassword = scanner.nextLine();
            
            loggedIn = login.loginUser(loginUsername, loginPassword);
            String loginStatus = login.returnLoginStatus(loginUsername, loginPassword);
            System.out.println(loginStatus);
            
            if (!loggedIn) {
                System.out.println("Please try again.\n");
            }
            
        } while (!loggedIn);
        
        scanner.close();
    }
}


