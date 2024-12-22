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
    
    public int getNumberLoginsByYearAndMonth(int year, int month) {
        int loginCount = 0;
        String sql = "SELECT COUNT(*) AS loginCount " +
                     "FROM LoginHistory " +
                     "WHERE YEAR(loginTime) = ? AND MONTH(loginTime) = ?";

        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();
        if (connection == null) {
            System.out.println("Failed to establish a database connection.");
            return 0;
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, year);
            preparedStatement.setInt(2, month);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                loginCount = resultSet.getInt("loginCount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close(); 
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return loginCount;
    }
    
    public List<String> getUniqueUserIDsByDateRange(Date dateFrom, Date dateTo) {
        List<String> userIDs = new ArrayList<>();
        String sql = "SELECT DISTINCT userID " +
                     "FROM LoginHistory " +
                     "WHERE loginTime BETWEEN ? AND ?";

        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();

        if (connection == null) {
            System.out.println("Failed to establish a database connection.");
            return userIDs;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setTimestamp(1, new java.sql.Timestamp(dateFrom.getTime()));
            preparedStatement.setTimestamp(2, new java.sql.Timestamp(dateTo.getTime()));
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                userIDs.add(resultSet.getString("userID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close(); 
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return userIDs;
    }
    public int getUniqueChatPartners(String userID, Date dateFrom, Date dateTo) {
        int uniqueReceivers = 0;
        String sql = "SELECT COUNT(DISTINCT receiverID) AS uniqueReceivers " +
                     "FROM Chat " +
                     "WHERE senderID = ? " +
                     "AND timeSend BETWEEN ? AND ?";

        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();

        if (connection == null) {
            System.out.println("Failed to establish a database connection.");
            return 0;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userID);
            preparedStatement.setDate(2, dateFrom);
            preparedStatement.setDate(3, dateTo);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                uniqueReceivers = resultSet.getInt("uniqueReceivers");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return uniqueReceivers;
    }
    
    public int getUniqueChatGroups(String userID, Date dateFrom, Date dateTo) {
        int uniqueGroups = 0;
        String sql = "SELECT COUNT(DISTINCT groupID) AS uniqueGroups " +
                     "FROM Chat " +
                     "WHERE senderID = ? " +
                     "AND timeSend BETWEEN ? AND ?";

        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();

        if (connection == null) {
            System.out.println("Failed to establish a database connection.");
            return 0;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userID);
            preparedStatement.setDate(2, dateFrom);
            preparedStatement.setDate(3, dateTo);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                uniqueGroups = resultSet.getInt("uniqueGroups");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return uniqueGroups;
    }
    
    public List<LoginHistoryDTO> getLatestLoginHistory() {
        List<LoginHistoryDTO> loginHistoryList = new ArrayList<>();

        // Query to get the latest 4 login records, ordered by loginTime
        String query = "SELECT id, userID, loginTime FROM LoginHistory ORDER BY loginTime DESC LIMIT 4";

        try (Connection conn = new DBconnector().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            if (conn == null) {
                System.out.println("Database connection is null. Please check your connection settings.");
                return loginHistoryList;
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LoginHistoryDTO loginHistory = new LoginHistoryDTO();
                    loginHistory.setId(rs.getInt("id"));
                    loginHistory.setUserID(rs.getString("userID"));
                    loginHistory.setLoginTime(rs.getTimestamp("loginTime").toLocalDateTime());

                    loginHistoryList.add(loginHistory);
                }
            }

        } catch (SQLException e) {
            System.err.println("An error occurred while fetching login history: " + e.getMessage());
            e.printStackTrace();
        }

        return loginHistoryList;
    }


    
}
