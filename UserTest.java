/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.registration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
 
    private User user;
    private Login login;

    // Runs before each test — creates a fresh user
    @BeforeEach
    public void setUp() {
        login = new Login("John", "Doe");
        login.registerUser("jo_hn", "Password1!", "+2712345");
        user = new User("jo_hn", "Password1!", login);
    }

    // Test that username is stored correctly
    @Test
    public void testGetUsername() {
        assertEquals("jo_hn", user.getUsername());
    }

    // Test correct password returns true
    @Test
    public void testCheckPasswordCorrect() {
        assertTrue(user.checkPassword("Password1!"));
    }

    // Test wrong password returns false
    @Test
    public void testCheckPasswordWrong() {
        assertFalse(user.checkPassword("wrongpassword"));
    }

    // Test inbox is empty when user is first created
    @Test
    public void testInboxStartsEmpty() {
        assertTrue(user.inbox.isEmpty());
    }

    // Test receiving a message adds it to the inbox
    @Test
    public void testReceiveMessage() {
        Message msg = new Message("sa_ra", "Hi John!");
        user.receiveMessage(msg);
        assertEquals(1, user.inbox.size());
    }

    // Test receiving multiple messages
    @Test
    public void testReceiveMultipleMessages() {
        user.receiveMessage(new Message("sa_ra", "Hi!"));
        user.receiveMessage(new Message("sa_ra", "How are you?"));
        user.receiveMessage(new Message("sa_ra", "Reply please!"));
        assertEquals(3, user.inbox.size());
    }

    // Test that the login object is stored and accessible
    @Test
    public void testGetLogin() {
        assertNotNull(user.getLogin());
        assertEquals("John", user.getLogin().getFirstName());
    }
    
}
