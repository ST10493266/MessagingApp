/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.registration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

/**
 *
 * @author Student
 */
public class Message {
   private String messageID;
    private int messageNumber;       // auto-incremented count for this message
    private String recipientCell;
    private String messageText;
    private String messageHash;

    // Tracks all sent messages across the session
    private static List<Message> sentMessages = new ArrayList<>();
    private static List<Message> storedMessages = new ArrayList<>();
    private static int totalMessagesSent = 0;

    // -----------------------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------------------

    public Message(int messageNumber, String recipientCell, String messageText) {
        this.messageNumber = messageNumber;
        this.recipientCell = recipientCell;
        this.messageText   = messageText;
        this.messageID     = generateMessageID();
        this.messageHash   = createMessageHash();
    }

    // -----------------------------------------------------------------------
    // ID generation
    // -----------------------------------------------------------------------

    /** Generates a random 10-digit message ID. */
    private String generateMessageID() {
        Random random = new Random();
        long id = (long)(random.nextDouble() * 9_000_000_000L) + 1_000_000_000L;
        return String.valueOf(id);
    }

    // -----------------------------------------------------------------------
    // Required methods from spec
    // -----------------------------------------------------------------------

    /**
     * Checks that the message ID is not more than 10 characters.
     * @return true if valid, false otherwise
     */
    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    /**
     * Validates the recipient cell number:
     * must be no more than 10 characters and start with an international code (+).
     * @return success or failure message
     */
    public String checkRecipientCell() {
        if (recipientCell != null
                && recipientCell.startsWith("+")
                && recipientCell.length() <= 10) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. " +
               "Please correct the number and try again.";
    }

    /**
     * Validates message length (max 250 characters).
     * @return "Message ready to send." or an error with how many chars over the limit
     */
    public String checkMessageLength() {
        if (messageText == null || messageText.length() <= 250) {
            return "Message ready to send.";
        }
        int over = messageText.length() - 250;
        return "Message exceeds 250 characters by " + over + "; please reduce the size.";
    }

    /**
     * Creates the Message Hash in format:
     * [first 2 digits of ID]:[message number]:[FIRST WORD LAST WORD]
     * Example: 00:0:HITONIGHT
     */
    public String createMessageHash() {
        String idPrefix = messageID.length() >= 2 ? messageID.substring(0, 2) : messageID;

        String[] words = messageText.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord  = words[words.length - 1];

        // Remove any punctuation from last word
        lastWord = lastWord.replaceAll("[^a-zA-Z0-9]", "");

        String hash = idPrefix + ":" + messageNumber + ":" + firstWord + lastWord;
        return hash.toUpperCase();
    }

    /**
     * Handles the send/disregard/store choice for this message.
     * @param choice "send", "disregard", or "store"
     * @return status message
     */
    public String sentMessage(String choice) {
        switch (choice.toLowerCase()) {
            case "send":
                sentMessages.add(this);
                totalMessagesSent++;
                return "Message successfully sent.";
            case "disregard":
                return "Press 0 to delete the message.";
            case "store":
                storedMessages.add(this);
                return "Message successfully stored.";
            default:
                return "Invalid choice. Please choose send, disregard, or store.";
        }
    }

    /**
     * Returns a formatted string of all sent messages.
     */
    public static String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages have been sent.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("=== Sent Messages ===\n");
        for (Message m : sentMessages) {
            sb.append("Message ID   : ").append(m.messageID).append("\n");
            sb.append("Message Hash : ").append(m.messageHash).append("\n");
            sb.append("Recipient    : ").append(m.recipientCell).append("\n");
            sb.append("Message      : ").append(m.messageText).append("\n");
            sb.append("-----------------------------\n");
        }
        return sb.toString();
    }

    /**
     * Returns the total number of messages sent.
     */
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    /**
     * Stores the message as a JSON-formatted string (basic implementation).
     * In a full app this would write to a .json file.
     */
    public String storeMessage() {
        return "{\n" +
               "  \"messageID\": \"" + messageID + "\",\n" +
               "  \"messageNumber\": " + messageNumber + ",\n" +
               "  \"recipient\": \"" + recipientCell + "\",\n" +
               "  \"message\": \"" + messageText.replace("\"", "\\\"") + "\",\n" +
               "  \"messageHash\": \"" + messageHash + "\"\n" +
               "}";
    }

    // -----------------------------------------------------------------------
    // Getters
    // -----------------------------------------------------------------------

    public String getMessageID()     { return messageID; }
    public String getRecipientCell() { return recipientCell; }
    public String getMessageText()   { return messageText; }
    public String getMessageHash()   { return messageHash; }
    public int    getMessageNumber() { return messageNumber; }

    public static List<Message> getSentMessages()   { return sentMessages; }
    public static List<Message> getStoredMessages() { return storedMessages; }
}
