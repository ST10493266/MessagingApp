/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.registration;

import java.util.ArrayList;
import java.util.List;

public class User {
     private String username;
    private String password;

    /** The Login object that was used to register this user (holds first/last name). */
    private Login login;

    /** The user's inbox of received messages. */
    List<Message> inbox;

    public User(String username, String password, Login login) {
        this.username = username;
        this.password = password;
        this.login    = login;
        this.inbox    = new ArrayList<>();
    }

    public String getUsername() { return username; }

    public Login getLogin() { return login; }

    public boolean checkPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public void receiveMessage(Message message) {
        inbox.add(message);
    }

    public void viewInbox() {
        if (inbox.isEmpty()) {
            System.out.println(username + "'s inbox is empty.");
            return;
        }
        System.out.println("=== Inbox for " + username + " ===");
        for (int i = 0; i < inbox.size(); i++) {
            System.out.println((i + 1) + ". " + inbox.get(i));
        }
    }
}
