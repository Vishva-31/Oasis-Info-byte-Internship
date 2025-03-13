package com.jdbc;
import java.sql.*;
import java.util.Scanner;

public class OnlineReservationSys {
    private static final String URL = "jdbc:mysql://localhost:3306/reservation_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Vishva@2003";
    
    private static Connection conn;
    
    public static void main(String[] args) {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            Scanner sc = new Scanner(System.in);
            
            System.out.println("Welcome to the Online Reservation System");
            System.out.print("Enter Login ID: ");   // login Id = admin
            String loginId = sc.next();
            System.out.print("Enter Password: ");	//Password = admin123;
            String password = sc.next();
            
            if (authenticateUser(loginId, password)) {
                System.out.println("Login Successful!");
                while (true) {
                    System.out.println("1. Book Ticket");
                    System.out.println("2. Cancel Ticket");
                    System.out.println("3. Exit");
                    System.out.print("Enter your choice: ");
                    int choice = sc.nextInt();
                    
                    switch (choice) {
                        case 1:
                            bookTicket(sc);
                            break;
                        case 2:
                            cancelTicket(sc);
                            break;
                        case 3:
                            System.out.println("Thank you for using the system.");
                            conn.close();
                            return;
                        default:
                            System.out.println("Invalid choice. Try again.");
                    }
                }
            } else {
                System.out.println("Invalid Credentials. Exiting...");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static boolean authenticateUser(String loginId, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE login_id = ? AND password = ?";
        PreparedStatement pt = conn.prepareStatement(query);
        pt.setString(1, loginId);
        pt.setString(2, password);
        ResultSet rs = pt.executeQuery();
        return rs.next();
    }
    
    private static void bookTicket(Scanner scanner) throws SQLException {
        System.out.print("Enter Train Number: ");
        int trainNo = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Enter Passenger Name: ");
        String passengerName = scanner.nextLine();
        System.out.print("Enter Class Type: ");
        String classType = scanner.next();
        System.out.print("Enter Journey Date (YYYY-MM-DD): ");
        String dateOfJourney = scanner.next();
        System.out.print("Enter From Location: ");
        String from = scanner.next();
        System.out.print("Enter Destination: ");
        String to = scanner.next();
        
        String query = "INSERT INTO reservations (train_no, passenger_name, class_type, journey_date, source, destination) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, trainNo);
        pstmt.setString(2, passengerName);
        pstmt.setString(3, classType);
        pstmt.setString(4, dateOfJourney);
        pstmt.setString(5, from);
        pstmt.setString(6, to);
        
        int rows = pstmt.executeUpdate();
        if (rows > 0) {
            System.out.println("Ticket Booked Successfully!");
        } else {
            System.out.println("Booking Failed.");
        }
    }
    
    private static void cancelTicket(Scanner scanner) throws SQLException {
        System.out.print("Enter PNR Number: ");
        int pnr = scanner.nextInt();
        
        String query = "DELETE FROM reservations WHERE pnr = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, pnr);
        
        int rows = pstmt.executeUpdate();
        if (rows > 0) {
            System.out.println("Ticket Cancelled Successfully!");
        } else {
            System.out.println("Invalid PNR Number. Cancellation Failed.");
        }
    }
}


// Sql Commands for the above code
// CREATE DATABASE reservation_db;
// USE reservation_db;
// CREATE TABLE users (
//     user_id INT AUTO_INCREMENT PRIMARY KEY,
//     login_id VARCHAR(50) UNIQUE NOT NULL,
//     password VARCHAR(50) NOT NULL
// );

// INSERT INTO users (login_id, password) VALUES ('admin', 'admin123'), ('user1', 'password1');

// CREATE TABLE reservations (
//     pnr INT AUTO_INCREMENT PRIMARY KEY,
//     train_no INT NOT NULL,
//     passenger_name VARCHAR(100) NOT NULL,
//     class_type VARCHAR(20) NOT NULL,
//     journey_date DATE NOT NULL,
//     source VARCHAR(50) NOT NULL,
//     destination VARCHAR(50) NOT NULL
// );

// INSERT INTO reservations (train_no, passenger_name, class_type, journey_date, source, destination)
// VALUES (101, 'Kasi', 'Sleeper', '2025-03-20', 'Madurai', 'Chennai');
// select * from user;



