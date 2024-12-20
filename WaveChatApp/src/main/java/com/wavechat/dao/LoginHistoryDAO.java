/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.wavechat.dao;

import com.wavechat.dto.LoginHistoryDTO;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author buihi
 */
public class LoginHistoryDAO {
    
    public List<LoginHistoryDTO> getLoginHistoryByUserID(String userID) {
        
        List<LoginHistoryDTO> loginHistoryList = new ArrayList<>();
        DBconnector dbConnector = new DBconnector();
        Connection conn = dbConnector.getConnection();
        
        if (conn == null){
            return loginHistoryList;
        }
        
        String query = "SELECT * FROM LoginHistory WHERE userID = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, userID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String userIDResult = rs.getString("userID");

                    // Convert SQL Timestamp to LocalDateTime
                    Timestamp timestamp = rs.getTimestamp("loginTime");
                    LocalDateTime loginTime = timestamp.toLocalDateTime();

                    LoginHistoryDTO history = new LoginHistoryDTO(id, userIDResult, loginTime);
                    loginHistoryList.add(history);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loginHistoryList;
    }
    
    public boolean addLoginHistory(String userID) {
        DBconnector dbConnector = new DBconnector();
        Connection conn = dbConnector.getConnection();

        if (conn == null) {
            return false;
        }

        String query = "INSERT INTO LoginHistory (userID, loginTime) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, userID);
            LocalDateTime now = LocalDateTime.now();
            ps.setTimestamp(2, java.sql.Timestamp.valueOf(now));

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public List<LoginHistoryDTO> getAllLoginHistory() {
        List<LoginHistoryDTO> loginHistoryList = new ArrayList<>();
        DBconnector dbConnector = new DBconnector();
        Connection conn = dbConnector.getConnection();

        if (conn == null){
            return loginHistoryList;
        }

        String query = "SELECT * FROM LoginHistory";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String userIDResult = rs.getString("userID");

                    // Convert SQL Timestamp to LocalDateTime
                    Timestamp timestamp = rs.getTimestamp("loginTime");
                    LocalDateTime loginTime = timestamp.toLocalDateTime();

                    LoginHistoryDTO history = new LoginHistoryDTO(id, userIDResult, loginTime);
                    loginHistoryList.add(history);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loginHistoryList;
    }
    
    public String[] getUserNameAndFullNameByUserID(String userID) {
        String[] result = new String[2]; // index 0: userName, index 1: fullName
        DBconnector dbConnector = new DBconnector();
        Connection conn = dbConnector.getConnection();
        
        if (conn == null) {
            return result;
        }
        
        // Truy vấn SQL lấy userName và fullName từ bảng User theo userID
        String query = "SELECT userName, fullName FROM User WHERE userID = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, userID);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    result[0] = rs.getString("userName");
                    result[1] = rs.getString("fullName");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
}
