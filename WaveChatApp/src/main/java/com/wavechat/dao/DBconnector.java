package com.wavechat.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnector {
    private static final String URL = "jdbc:mysql://localhost:3306/CHATAPPLICATION"; // tên DB
    private static final String USER = "root"; // username
    private static final String PASSWORD = "147456369"; // password

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
            return null;
        }
    }
}