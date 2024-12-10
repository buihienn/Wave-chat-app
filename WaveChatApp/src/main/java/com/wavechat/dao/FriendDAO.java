package com.wavechat.dao;

import com.wavechat.dto.FriendDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FriendDAO {

    // Lấy danh sách bạn bè của userID
    public List<FriendDTO> getFriendsByUserID(String userID) {
        List<FriendDTO> friendsList = new ArrayList<>();
        String query = "SELECT u.userName, u.onlineStatus " +
                       "FROM User u " +
                       "JOIN Friends f ON (f.userID1 = u.userID OR f.userID2 = u.userID) " +
                       "JOIN Friend_requests fr ON (fr.requester_userID = f.userID1 AND fr.requested_userID = f.userID2) " +
                       "WHERE (f.userID1 = ? OR f.userID2 = ?) " +
                       "AND u.userID != ? AND fr.status = 'accepted'";

//<<<<<<< HEAD
        // Tạo đối tượng DBconnector và kết nối
        DBconnector dbConnector = new DBconnector();  // Tạo đối tượng DBconnector
        Connection connection = dbConnector.getConnection();  // Gọi phương thức getConnection()
        if (connection == null) {
            return friendsList;
        }
//=======
//        try (Connection connection = DBconnector.getConnection();
//            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//>>>>>>> main

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userID);
            preparedStatement.setString(2, userID);
            preparedStatement.setString(3, userID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String userName = resultSet.getString("userName");
                boolean onlineStatus = resultSet.getBoolean("onlineStatus");
                friendsList.add(new FriendDTO(userName, onlineStatus));
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching friends: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();  // Đảm bảo đóng kết nối sau khi sử dụng
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return friendsList;
    }

    // Xóa dữ liệu bạn bè trong bảng Friends
    public boolean unfriend(String userID1, String userID2) {
        String query = "DELETE FROM Friends WHERE (userID1 = ? AND userID2 = ?) OR (userID1 = ? AND userID2 = ?)";

        // Tạo đối tượng DBconnector và kết nối
        DBconnector dbConnector = new DBconnector();  // Tạo đối tượng DBconnector
        Connection connection = dbConnector.getConnection();  // Gọi phương thức getConnection()
        if (connection == null) {
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userID1);
            preparedStatement.setString(2, userID2);
            preparedStatement.setString(3, userID2);
            preparedStatement.setString(4, userID1);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0; // Trả về true nếu xóa thành công
        } catch (SQLException e) {
            System.out.println("Error while unfriend: " + e.getMessage());
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