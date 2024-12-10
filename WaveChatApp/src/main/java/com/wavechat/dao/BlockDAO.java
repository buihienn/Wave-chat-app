package com.wavechat.dao;

import java.sql.*;

public class BlockDAO {

    // Hàm Block: Thêm dữ liệu vào bảng Blocks
    public boolean blockUser(String userID1, String userID2) {
        String query = "INSERT INTO Blocks (userID, blocked_userID) VALUES (?, ?)";

        // Tạo đối tượng DBconnector và lấy kết nối
        DBconnector dbConnector = new DBconnector();  // Tạo đối tượng DBconnector
        Connection connection = dbConnector.getConnection();  // Lấy kết nối từ DBconnector
        if (connection == null) {
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userID1);
            preparedStatement.setString(2, userID2);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu insert thành công
        } catch (SQLException e) {
            System.out.println("Error while blocking user: " + e.getMessage());
            return false;
        } finally {
            try {
                if (connection != null) {
                    connection.close();  // Đảm bảo đóng kết nối sau khi sử dụng
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Hàm kiểm tra xem người dùng đã bị block chưa
    public boolean isBlocked(String userID1, String userID2) {
        String query = "SELECT * FROM Blocks WHERE userID = ? AND blocked_userID = ?";

        // Tạo đối tượng DBconnector và lấy kết nối
        DBconnector dbConnector = new DBconnector();  // Tạo đối tượng DBconnector
        Connection connection = dbConnector.getConnection();  // Lấy kết nối từ DBconnector
        if (connection == null) {
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userID1);
            preparedStatement.setString(2, userID2);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Trả về true nếu có kết quả (đã bị block)
        } catch (SQLException e) {
            System.out.println("Error checking if blocked: " + e.getMessage());
            return false;
        } finally {
            try {
                if (connection != null) {
                    connection.close();  // Đảm bảo đóng kết nối sau khi sử dụng
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}