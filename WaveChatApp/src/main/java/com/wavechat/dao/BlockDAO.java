package com.wavechat.dao;

import java.sql.*;

public class BlockDAO {

    // Hàm Block: Thêm dữ liệu vào bảng Blocks
    public boolean blockUser(String userID1, String userID2) {
        String query = "INSERT INTO Blocks (userID, blocked_userID) VALUES (?, ?)";
        
        try (Connection connection = DBconnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setString(1, userID1);
            preparedStatement.setString(2, userID2);
            
            int rowsAffected = preparedStatement.executeUpdate();
            
            return rowsAffected > 0; // Trả về true nếu insert thành công
        } catch (SQLException e) {
            System.out.println("Error while blocking user: " + e.getMessage());
            return false;
        }
    }

    // Hàm kiểm tra xem người dùng đã bị block chưa
    public boolean isBlocked(String userID1, String userID2) {
        String query = "SELECT * FROM Blocks WHERE userID = ? AND blocked_userID = ?";
        
        try (Connection connection = DBconnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setString(1, userID1);
            preparedStatement.setString(2, userID2);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Trả về true nếu có kết quả (đã bị block)
        } catch (SQLException e) {
            System.out.println("Error checking if blocked: " + e.getMessage());
            return false;
        }
    }
}

