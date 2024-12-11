package com.wavechat.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBconnector {
    // Thông tin kết nối được phân chia thành các biến cấu hình
    private String dbms = "mysql";            // Chỉ định hệ quản trị cơ sở dữ liệu
    private String serverName = "localhost";  // Tên server
    private int portNumber = 3306;            // Cổng kết nối đến MySQL
    private String dbName = "CHATAPPLICATION"; // Tên cơ sở dữ liệu
    private String user = "root";             // Tên người dùng MySQL
    private String password = "123456";       // Mật khẩu của người dùng
//    private String password = "147456369";

    // Phương thức để lấy kết nối
    public Connection getConnection() {
        Connection conn = null;
        Properties connectionProps = new Properties();
        
        // Thiết lập các thuộc tính kết nối
        connectionProps.put("user", user);
        connectionProps.put("password", password);

        try {
            // Xây dựng chuỗi kết nối
            String connString = "jdbc:" + dbms + "://" + serverName +
                                ":" + portNumber + "/";
            // Kết nối đến cơ sở dữ liệu
            conn = DriverManager.getConnection(connString, connectionProps);
            conn.setCatalog(dbName);  // Chọn cơ sở dữ liệu
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
            conn = null;
        }
        return conn;
    }
    public static void main (String [] args) {
        DBconnector dbconnection = new DBconnector();
        Connection conn = dbconnection.getConnection();
        if (conn != null) {
            System.out.println("Successfull.");
        } 
        else {
            System.out.println("Failed");
        }
    }
}
