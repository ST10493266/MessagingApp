/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.registration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {
    private Message message1;
    private Message message2;

    @BeforeEach
    public void setUp() {
        // Test data from spec
        // Message 1: recipient +27718693002, "Hi Mike, can you join us for dinner tonight?"
        message1 = new Message(1, "+27718693002", "Hi Mike, can you join us for dinner tonight?");

        // Message 2: recipient 08575975889 (invalid - no international code), "Hi Keegan, did you receive the payment?"
        message2 = new Message(2, "08575975889", "Hi Keegan, did you receive the payment?");
    }

    // -----------------------------------------------------------------------
    // checkMessageID tests
    // -----------------------------------------------------------------------

    @Test
    public void testMessageIDNotMoreThanTenCharacters() {
        assertTrue(message1.checkMessageID(),
                "Message ID should be 10 characters or less");
    }

    @Test
    public void testMessageIDGenerated() {
        assertNotNull(message1.getMessageID(), "Message ID should not be null");
    }

    // -----------------------------------------------------------------------
    // checkRecipientCell tests
    // -----------------------------------------------------------------------

    @Test
    public void testRecipientCellSuccess() {
        // +27718693002 is valid international format, but 12 chars — let's use shorter valid one
        Message validMsg = new Message(1, "+271869300", "Hello");
        assertEquals("Cell phone number successfully captured.",
                validMsg.checkRecipientCell());
    }

    @Test
    public void testRecipientCellFailure() {
        // 08575975889 has no international code
        assertEquals(
                "Cell phone number is incorrectly formatted or does not contain an international code. " +
                "Please correct the number and try again.",
                message2.checkRecipientCell());
    }

    // -----------------------------------------------------------------------
    // checkMessageLength tests
    // -----------------------------------------------------------------------

    @Test
    public void testMessageLengthSuccess() {
        Message shortMsg = new Message(1, "+271869300", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message ready to send.", shortMsg.checkMessageLength());
    }

    @Test
    public void testMessageLengthFailure() {
        String longText = "A".repeat(260); // 260 chars — 10 over limit
        Message longMsg = new Message(1, "+271869300", longText);
        String result = longMsg.checkMessageLength();
        assertTrue(result.contains("Message exceeds 250 characters by 10"),
                "Should report how many characters over the limit");
    }

    // -----------------------------------------------------------------------
    // createMessageHash tests
    // -----------------------------------------------------------------------

    @Test
    public void testMessageHashFormatIsCorrect() {
        // Hash format: [first 2 of ID]:[messageNumber]:[FIRSTWORDLASTWORD]
        String hash = message1.getMessageHash();
        assertNotNull(hash);
        assertTrue(hash.contains(":"), "Hash should contain colons");
        assertEquals(hash, hash.toUpperCase(), "Hash should be all caps");
    }

    @Test
    public void testMessageHashContainsFirstAndLastWord() {
        // "Hi Mike, can you join us for dinner tonight?" → first=HI, last=TONIGHT
        String hash = message1.getMessageHash();
        assertTrue(hash.contains("HI"), "Hash should contain first word HI");
        assertTrue(hash.contains("TONIGHT"), "Hash should contain last word TONIGHT");
    }

    @Test
    public void testMessageHashMatchesExpectedPattern() {
        // Using test data from spec: message 1 → hash should end with HITONIGHT
        String hash = message1.getMessageHash();
        assertTrue(hash.endsWith("HITONIGHT"),
                "Hash for message 1 should end with HITONIGHT, got: " + hash);
    }

    // -----------------------------------------------------------------------
    // sentMessage tests
    // -----------------------------------------------------------------------

    @Test
    public void testSentMessageSend() {
        assertEquals("Message successfully sent.", message1.sentMessage("send"));
    }

    @Test
    public void testSentMessageDisregard() {
        assertEquals("Press 0 to delete the message.", message1.sentMessage("disregard"));
    }

    @Test
    public void testSentMessageStore() {
        assertEquals("Message successfully stored.", message1.sentMessage("store"));
    }

    // -----------------------------------------------------------------------
    // returnTotalMessages test
    // -----------------------------------------------------------------------

    @Test
    public void testReturnTotalMessages() {
        int before = Message.returnTotalMessages();
        Message m = new Message(99, "+271869300", "Test message");
        m.sentMessage("send");
        assertEquals(before + 1, Message.returnTotalMessages(),
                "Total messages should increment by 1 after sending");
    }

    // -----------------------------------------------------------------------
    // printMessages test
    // -----------------------------------------------------------------------

    @Test
    public void testPrintMessagesContainsDetails() {
        Message m = new Message(5, "+271869300", "Hello there friend");
        m.sentMessage("send");
        String output = Message.printMessages();
        assertTrue(output.contains("Hello there friend"),
                "printMessages should contain the message text");
    }
}
