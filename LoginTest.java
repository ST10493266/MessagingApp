/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.registration;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class LoginTest {

    private Login login;

    @BeforeEach
    public void setUp() {
        // Initialize with test user's first and last name
        login = new Login("Kyle", "Smith");
    }
    
    //  CHECK USERNAME TESTS
    

    @Test
    public void testCheckUserName_ValidUsername_ReturnsTrue() {
        // Valid username: contains underscore and ≤ 5 characters
        assertTrue(login.checkUserName("kyl_1"));
        assertTrue(login.checkUserName("a_1"));
        assertTrue(login.checkUserName("us_er"));
    }

    @Test
    public void testCheckUserName_InvalidUsername_ReturnsFalse() {
        // Invalid: no underscore
        assertFalse(login.checkUserName("kyle"));
        
        // Invalid: too long (>5 chars)
        assertFalse(login.checkUserName("kyle_smith"));
        
        // Invalid: both issues
        assertFalse(login.checkUserName("kyleeeeeeee"));
    }

   
    //  CHECK PASSWORD COMPLEXITY TESTS
    @Test
    public void testCheckPasswordComplexity_ValidPassword_ReturnsTrue() {
        // Valid password: ≥8 chars, capital letter, number, special character
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
        assertTrue(login.checkPasswordComplexity("Password123!"));
        assertTrue(login.checkPasswordComplexity("Test@2024"));
    }

    @Test
    public void testCheckPasswordComplexity_InvalidPassword_ReturnsFalse() {
        // Too short
        assertFalse(login.checkPasswordComplexity("Pass1!"));
        
        // No capital letter
        assertFalse(login.checkPasswordComplexity("password123!"));
        
        // No number
        assertFalse(login.checkPasswordComplexity("Password!!"));
        
        // No special character
        assertFalse(login.checkPasswordComplexity("Password123"));
        
        // All issues combined
        assertFalse(login.checkPasswordComplexity("pass"));
    }

   
    //  CHECK CELL PHONE TESTS
    @Test
    public void testCheckCellPhoneNumber_ValidPhone_ReturnsTrue() {
        // Valid South African cell number format: +27 followed by 9 digits
        assertTrue(login.checkCellPhoneNumber("+2783896897"));
        assertTrue(login.checkCellPhoneNumber("+27123456789"));
        assertTrue(login.checkCellPhoneNumber("+27820001111"));
    }

    @Test
    public void testCheckCellPhoneNumber_InvalidPhone_ReturnsFalse() {
        // Missing +27 prefix
        assertFalse(login.checkCellPhoneNumber("083896897"));
        
        // Wrong format
        assertFalse(login.checkCellPhoneNumber("08966553"));
        
        // Too short
        assertFalse(login.checkCellPhoneNumber("+27123"));
        
        // Too long
        assertFalse(login.checkCellPhoneNumber("+271234567890"));
        
        // Invalid characters
        assertFalse(login.checkCellPhoneNumber("+27abc123456"));
    }

    
    //  REGISTER USER TESTS
    @Test
    public void testRegisterUser_ValidInputs_ReturnsSuccessMessage() {
        String result = login.registerUser("kyl_1", "Ch&&sec@ke99!", "+2783896897");
        assertEquals("User registered successfully.", result);
        
        // Verify that registration data was stored
        assertEquals("kyl_1", login.getRegisteredUsername());
        assertEquals("+2783896897", login.getRegisteredCellPhone());
    }

    @Test
    public void testRegisterUser_InvalidUsername_ReturnsErrorMessage() {
        String result = login.registerUser("invalid", "Ch&&sec@ke99!", "+2783896897");
        assertEquals(
            "Username is not correctly formatted; please ensure that your username " +
            "contains an underscore and is no more than five characters in length.",
            result
        );
        
        // Verify that no registration occurred
        assertNull(login.getRegisteredUsername());
    }

    @Test
    public void testRegisterUser_InvalidPassword_ReturnsErrorMessage() {
        String result = login.registerUser("kyl_1", "weak", "+2783896897");
        assertEquals(
            "Password is not correctly formatted; please ensure that the password " +
            "contains at least eight characters, a capital letter, a number, and a special character.",
            result
        );
        
        // Verify that no registration occurred
        assertNull(login.getRegisteredUsername());
    }

    @Test
    public void testRegisterUser_InvalidCellPhone_ReturnsErrorMessage() {
        String result = login.registerUser("kyl_1", "Ch&&sec@ke99!", "0834567890");
        assertEquals(
            "Cell number is incorrectly formatted or does not contain an international code; " +
            "please correct the number and try again.",
            result
        );
        
        // Verify that no registration occurred
        assertNull(login.getRegisteredUsername());
    }

    @Test
    public void testRegisterUser_MultipleInvalidFields_ReturnsFirstErrorMessage() {
        // Should return username error first
        String result = login.registerUser("invalid", "weak", "0834567890");
        assertTrue(result.contains("Username is not correctly formatted"));
        
        // Should return password error when username is valid but password invalid
        result = login.registerUser("kyl_1", "weak", "+2783896897");
        assertTrue(result.contains("Password is not correctly formatted"));
    }

    
    //  LOGIN USER TESTS
    @Test
    public void testLoginUser_CorrectCredentials_ReturnsTrue() {
        // First register a user
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+2783896897");
        
        // Then test login with correct credentials
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginUser_WrongPassword_ReturnsFalse() {
        // Register a user
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+2783896897");
        
        // Test login with wrong password
        assertFalse(login.loginUser("kyl_1", "wrongpassword"));
    }

    @Test
    public void testLoginUser_WrongUsername_ReturnsFalse() {
        // Register a user
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+2783896897");
        
        // Test login with wrong username
        assertFalse(login.loginUser("wronguser", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginUser_NoRegistration_ReturnsFalse() {
        // Test login without any registration
        assertFalse(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    
    //  RETURN LOGIN STATUS TESTS
    @Test
    public void testReturnLoginStatus_ValidLogin_ReturnsWelcomeMessage() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+2783896897");
        String result = login.returnLoginStatus("kyl_1", "Ch&&sec@ke99!");
        assertEquals("Welcome Kyle Smith, it is great to see you again.", result);
    }

    @Test
    public void testReturnLoginStatus_InvalidLogin_ReturnsErrorMessage() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+2783896897");
        String result = login.returnLoginStatus("kyl_1", "wrongpassword");
        assertEquals("Username or password incorrect, please try again.", result);
    }

    @Test
    public void testReturnLoginStatus_NoRegistration_ReturnsErrorMessage() {
        String result = login.returnLoginStatus("kyl_1", "Ch&&sec@ke99!");
        assertEquals("Username or password incorrect, please try again.", result);
    }

    
    //  GETTER METHODS TESTS
    @Test
    public void testGetRegisteredUsername_AfterRegistration_ReturnsUsername() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+2783896897");
        assertEquals("kyl_1", login.getRegisteredUsername());
    }

    @Test
    public void testGetRegisteredUsername_BeforeRegistration_ReturnsNull() {
        assertNull(login.getRegisteredUsername());
    }

    @Test
    public void testGetRegisteredCellPhone_AfterRegistration_ReturnsCellPhone() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+2783896897");
        assertEquals("+2783896897", login.getRegisteredCellPhone());
    }

    @Test
    public void testGetRegisteredCellPhone_BeforeRegistration_ReturnsNull() {
        assertNull(login.getRegisteredCellPhone());
    }

    
    //  INTEGRATION TESTS
    @Test
    public void testFullRegistrationAndLoginFlow() {
        // Step 1: Register new user
        String regResult = login.registerUser("john_1", "JohnDoe@2024", "+27821234567");
        assertEquals("User registered successfully.", regResult);
        
        // Step 2: Verify stored data
        assertEquals("john_1", login.getRegisteredUsername());
        assertEquals("+27821234567", login.getRegisteredCellPhone());
        
        // Step 3: Successful login
        assertTrue(login.loginUser("john_1", "JohnDoe@2024"));
        
        // Step 4: Verify welcome message
        String status = login.returnLoginStatus("john_1", "JohnDoe@2024");
        assertEquals("Welcome Kyle Smith, it is great to see you again.", status);
    }

    @Test
    public void testMultipleRegistrationAttempts() {
        // First registration should succeed
        String result1 = login.registerUser("user_1", "ValidPass123!", "+27831234567");
        assertEquals("User registered successfully.", result1);
        assertEquals("user_1", login.getRegisteredUsername());
        
        // Second registration (should overwrite first)
        String result2 = login.registerUser("user_2", "NewPass456@", "+27839876543");
        assertEquals("User registered successfully.", result2);
        assertEquals("user_2", login.getRegisteredUsername());
        assertEquals("+27839876543", login.getRegisteredCellPhone());
        
        // Old credentials should no longer work
        assertFalse(login.loginUser("user_1", "ValidPass123!"));
        
        // New credentials should work
        assertTrue(login.loginUser("user_2", "NewPass456@"));
    }

    @Test
    public void testDifferentUserNames() {
        // Test with different first and last names
        Login differentUser = new Login("Jane", "Doe");
        differentUser.registerUser("jane_1", "JaneDoe@2024", "+27834567890");
        
        String status = differentUser.returnLoginStatus("jane_1", "JaneDoe@2024");
        assertEquals("Welcome Jane Doe, it is great to see you again.", status);
    }
}


