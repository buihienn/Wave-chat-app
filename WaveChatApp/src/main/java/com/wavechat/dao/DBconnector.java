package com.wavechat.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBconnector {
    private String dbms;
    private String serverName;
    private int portNumber;
    private String dbName;
    private String user;
    private String password;

    public DBconnector() {
        loadConfig(); // Đọc cấu hình từ file db.properties
    }

    private void loadConfig() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("db.properties")) {
            properties.load(fis);

            this.dbms = properties.getProperty("dbms", "mysql");
            this.serverName = properties.getProperty("serverName", "localhost");
            this.portNumber = Integer.parseInt(properties.getProperty("portNumber", "3306"));
            this.dbName = properties.getProperty("dbName", "CHATAPPLICATION");
            this.user = properties.getProperty("user", "root");
            this.password = properties.getProperty("password", "111111");

        } catch (IOException e) {
            System.out.println("Error loading database configuration: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        Connection conn = null;
        Properties connectionProps = new Properties();
        
        connectionProps.put("user", user);
        connectionProps.put("password", password);

        try {
            String connString = "jdbc:" + dbms + "://" + serverName + ":" + portNumber + "/";
            conn = DriverManager.getConnection(connString, connectionProps);
            conn.setCatalog(dbName); // Chọn cơ sở dữ liệu
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
            conn = null;
        }
        return conn;
    }

    public static void main(String[] args) {
        DBconnector dbconnection = new DBconnector();
        Connection conn = dbconnection.getConnection();
        if (conn != null) {
            System.out.println("Connection successful.");
        } else {
            System.out.println("Connection failed.");
        }
    }
}