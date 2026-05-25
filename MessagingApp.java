/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.registration;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MessagingApp {
    private Map<String, User> users = new HashMap<>();
    private User currentUser = null;


    // Registration
    public boolean register(String firstName, String lastName,
                            String username, String password, String cellPhone) {
        if (users.containsKey(username)) {
            System.out.println("Registration failed: username '" + username + "' is already taken.");
            return false;
        }
        Login login = new Login(firstName, lastName);
        String result = login.registerUser(username, password, cellPhone);
        System.out.println(result);
        if (result.contains("registered successfully")) {
            users.put(username, new User(username, password, login));
            return true;
        }
        return false;
    }

    // Login / Logout
    public boolean login(String username, String password) {
        User user = users.get(username);
        if (user == null) {
            System.out.println("Login failed: username not found.");
            return false;
        }
        Login login = user.getLogin();
        String status = login.returnLoginStatus(username, password);
        System.out.println(status);
        if (login.loginUser(username, password)) {
            currentUser = user;
            return true;
        }
        return false;
    }

    public void logout() {
        if (currentUser != null) {
            System.out.println("Goodbye, " + currentUser.getUsername() + "!");
            currentUser = null;
        } else {
            System.out.println("No user is currently logged in.");
        }
    }

    // Send Messages feature

    /**
     * Runs the send-messages flow:
     * asks how many messages, then loops for each one.
     */
    private void sendMessagesFlow(Scanner scanner) {
        if (currentUser == null) {
            System.out.println("You must be logged in to send messages.");
            return;
        }

        System.out.print("How many messages do you want to send? ");
        int numMessages;
        try {
            numMessages = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Returning to menu.");
            return;
        }

        for (int i = 0; i < numMessages; i++) {
            System.out.println("\n--- Message " + (i + 1) + " of " + numMessages + " ---");

            // Recipient cell number
            String recipientCell;
            while (true) {
                System.out.print("Enter recipient cell number (+international format, max 10 chars): ");
                recipientCell = scanner.nextLine().trim();
                // Temporarily create message to validate cell
                Message tempMsg = new Message(i + 1, recipientCell, "test");
                String cellCheck = tempMsg.checkRecipientCell();
                System.out.println(cellCheck);
                if (cellCheck.equals("Cell phone number successfully captured.")) break;
            }

            // Message text
            String messageText;
            while (true) {
                System.out.print("Enter message (max 250 characters): ");
                messageText = scanner.nextLine().trim();
                Message tempMsg = new Message(i + 1, recipientCell, messageText);
                String lengthCheck = tempMsg.checkMessageLength();
                if (lengthCheck.equals("Message ready to send.")) {
                    System.out.println(lengthCheck);
                    break;
                } else {
                    System.out.println(lengthCheck);
                }
            }

            // Create the real message
            Message message = new Message(i + 1, recipientCell, messageText);

            // Show generated details
            System.out.println("\nMessage ID generated: " + message.getMessageID());
            System.out.println("Message Hash       : " + message.getMessageHash());

            // Ask send / disregard / store
            System.out.println("\nWhat would you like to do with this message?");
            System.out.println("1. Send Message");
            System.out.println("2. Disregard Message");
            System.out.println("3. Store Message to send later");
            System.out.print("Choose (1/2/3): ");
            String action = scanner.nextLine().trim();

            String actionResult;
            switch (action) {
                case "1": actionResult = message.sentMessage("send");      break;
                case "2": actionResult = message.sentMessage("disregard"); break;
                case "3": actionResult = message.sentMessage("store");     break;
                default:  actionResult = message.sentMessage("send");      break;
            }
            System.out.println(actionResult);

            // Show full message details after sending
            if (action.equals("1")) {
                System.out.println("\n--- Message Details ---");
                System.out.println("Message ID   : " + message.getMessageID());
                System.out.println("Message Hash : " + message.getMessageHash());
                System.out.println("Recipient    : " + message.getRecipientCell());
                System.out.println("Message      : " + message.getMessageText());
            }
        }

        // After all messages — show totals
        System.out.println("\n=============================");
        System.out.println("Total messages sent: " + Message.returnTotalMessages());
        System.out.println("=============================");
    }

    // Main menu loop
    public void run() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to QuickChat.");
        System.out.println();

        // Registration
        System.out.println("=== Register to get started ===");
        boolean registered = false;
        while (!registered) {
            System.out.print("First name: ");
            String firstName = scanner.nextLine().trim();
            System.out.print("Last name: ");
            String lastName = scanner.nextLine().trim();
            System.out.print("Username (must contain '_', max 5 chars): ");
            String username = scanner.nextLine().trim();
            System.out.print("Password (min 8 chars, 1 capital, 1 number, 1 special): ");
            String password = scanner.nextLine().trim();
            System.out.print("Cell phone (+27XXXXXXXXX): ");
            String phone = scanner.nextLine().trim();
            registered = register(firstName, lastName, username, password, phone);
            if (!registered) System.out.println("Please try again.\n");
        }

        // Login
        System.out.println("\n=== Login ===");
        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.print("Username: ");
            String u = scanner.nextLine().trim();
            System.out.print("Password: ");
            String p = scanner.nextLine().trim();
            loggedIn = login(u, p);
            if (!loggedIn) System.out.println("Please try again.\n");
        }

        // Main menu
        boolean running = true;
        while (running) {
            System.out.println("\n--- QuickChat Menu ---");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    sendMessagesFlow(scanner);
                    break;
                case "2":
                    System.out.println("Coming Soon.");
                    break;
                case "3":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1, 2, or 3.");
            }
        }

        scanner.close();
    }

    // Entry point

    public static void main(String[] args) {
        new MessagingApp().run();
    }
    
}
