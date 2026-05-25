# MessagingApp
Part 2 of Registration
QuickChat Messaging Application 

Overview 

QuickChat is a simple Java-based messaging application that allows users to register, log in, send messages, and view received messages in an inbox. The project demonstrates the use of object-oriented programming principles such as classes, objects, encapsulation, collections, and validation. The application also includes JUnit 5 unit tests to verify that all major features work correctly. 

Features 
User registration with validation  
Username, password, and phone number checking  

User login and logout  
Send messages between users  
Store and display sent messages  
Inbox system for receiving messages  
Message ID and message hash generation  
JUnit 5 testing for all major classes 

 

Technologies Used 

Java  

NetBeans IDE  

JUnit 5  

Java Collections Framework (HashMap, ArrayList)  

Regular Expressions (Pattern) 

 

Project Structure 
 
QuickChat/ 

│ 
├── Source Packages 

│   ├── Login.java 

│   ├── User.java 

│   ├── Message.java 

│   └── MessagingApp.java 

│ 

├── Test Packages 

│   ├── LoginTest.java 

│   ├── UserTest.java 

│   ├── MessageTest.java 

│   └── MessagingAppTest.java 

│ 

└── README.md 

 

Class Descriptions 
Login Class 
Handles user registration and login validation. It checks username formatting, password complexity, and South African phone number formatting before storing user details. 

User Class 
Represents a user account in the system. It stores login information and manages the user inbox where received messages are saved. 

Message Class 
Handles message creation, validation, hashing, storing, and displaying. It also tracks the total number of sent messages. 

MessagingApp Class 
Acts as the main controller of the application. It manages users, login sessions, messaging functionality, and the menu system. 

Validation Rules 
Username Rules 
Must contain an underscore _  
Must follow the required length restrictions  
Password Rules 

Password must contain: 
At least 8 characters  
One uppercase letter  
One number  
One special character  
Phone Number Rules 
Must use South African international format 
+27831234567 

 

JUnit Testing 
The application includes unit tests for: 
Login validation  
Password checking  
Message validation  
Sending messages  
User inbox functionality  
Registration and login systems  
The tests ensure that all methods behave correctly and help detect errors early during development. 

 

Example Login Details 
Username: jo_hn 
Password: Password1! 
Phone: +27831234567 

 

Future Improvements 
Graphical User Interface (GUI)  

Database integration  
Message encryption  
Delete messages feature  
Search messages feature  
Real-time messaging  
File attachments  


Author 
Developed as a Java Object-Oriented Programming and Unit Testing project using NetBeans and JUnit 5. 

 

License 

This project is for educational purposes. 
