/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.registration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageAppTest {
     private MessagingApp app;

    // Runs before each test — creates a fresh app with two registered users
    @BeforeEach
    public void setUp() {
        app = new MessagingApp();
        // Register two users to use across tests
        app.register("John", "Doe",  "jo_hn", "Password1!", "+2712345");
        app.register("Sara", "Smith","sa_ra", "Password2@", "+2798765");
    }


    // Registration tests
    // Test successful registration
    @Test
    public void testRegisterSuccess() {
        boolean result = app.register("Mike", "Jones", "mi_ke", "Password3#", "+2711111");
        assertTrue(result);
    }

    // Test duplicate username is rejected
    @Test
    public void testRegisterDuplicateUsername() {
        boolean result = app.register("Jane", "Doe", "jo_hn", "Password1!", "+2799999");
        assertFalse(result);
    }

    // Test invalid username (no underscore) is rejected
    @Test
    public void testRegisterInvalidUsername() {
        boolean result = app.register("Tim", "Brown", "timbo", "Password1!", "+2711111");
        assertFalse(result);
    }

    // Test invalid password (too simple) is rejected
    @Test
    public void testRegisterInvalidPassword() {
        boolean result = app.register("Tim", "Brown", "ti_mb", "simple", "+2711111");
        assertFalse(result);
    }

    // Test invalid cell phone is rejected
    @Test
    public void testRegisterInvalidPhone() {
        boolean result = app.register("Tim", "Brown", "ti_mb", "Password1!", "0821234567");
        assertFalse(result);
    }

    // Login tests
    // Test successful login
    @Test
    public void testLoginSuccess() {
        boolean result = app.login("jo_hn", "Password1!");
        assertTrue(result);
    }

    // Test login with wrong password fails
    @Test
    public void testLoginWrongPassword() {
        boolean result = app.login("jo_hn", "wrongpassword");
        assertFalse(result);
    }

    // Test login with non-existent username fails
    @Test
    public void testLoginUnknownUser() {
        boolean result = app.login("no_one", "Password1!");
        assertFalse(result);
    }

    // Messaging tests
    // Test sending a message while logged in succeeds
    @Test
    public void testSendMessageSuccess() {
        app.login("jo_hn", "Password1!");
        boolean result = app.sendMessage("sa_ra", "Hello Sara!");
        assertTrue(result);
    }

    // Test sending a message while NOT logged in fails
    @Test
    public void testSendMessageNotLoggedIn() {
        boolean result = app.sendMessage("sa_ra", "Hello Sara!");
        assertFalse(result);
    }

    // Test sending a message to a non-existent user fails
    @Test
    public void testSendMessageUnknownRecipient() {
        app.login("jo_hn", "Password1!");
        boolean result = app.sendMessage("no_one", "Hello?");
        assertFalse(result);
    }

    // Logout tests
    // Test that logout works after login
    @Test
    public void testLogoutAfterLogin() {
        app.login("jo_hn", "Password1!");
        app.logout();
        // After logout, sending a message should fail
        boolean result = app.sendMessage("sa_ra", "Should fail");
        assertFalse(result);
    }
}
