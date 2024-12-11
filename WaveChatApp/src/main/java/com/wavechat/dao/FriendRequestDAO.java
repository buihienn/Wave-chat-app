package com.wavechat.dao;

import com.wavechat.dto.FriendRequestDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FriendRequestDAO {

    // Thêm yêu cầu kết bạn mới
    public boolean addFriendRequest(FriendRequestDTO request) {
        String checkRejectedQuery = "SELECT * FROM Friend_requests WHERE requester_userID = ? AND requested_userID = ? AND status = 'rejected'";
        String deleteRejectedQuery = "DELETE FROM Friend_requests WHERE requester_userID = ? AND requested_userID = ? AND status = 'rejected'";
        String insertRequestQuery = "INSERT INTO Friend_requests (requester_userID, requested_userID, status, createdAt) VALUES (?, ?, ?, ?)";

        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();
        if (connection == null) {
            return false;
        }

        try {
            // Kiểm tra xem có yêu cầu kết bạn bị rejected hay không
            try (PreparedStatement checkStatement = connection.prepareStatement(checkRejectedQuery)) {
                checkStatement.setString(1, request.getRequesterUserID());
                checkStatement.setString(2, request.getRequestedUserID());
                ResultSet resultSet = checkStatement.executeQuery();

                // Nếu có yêu cầu bị rejected, xóa dòng đó
                if (resultSet.next()) {
                    try (PreparedStatement deleteStatement = connection.prepareStatement(deleteRejectedQuery)) {
                        deleteStatement.setString(1, request.getRequesterUserID());
                        deleteStatement.setString(2, request.getRequestedUserID());
                        deleteStatement.executeUpdate();
                    }
                }
            }

            // Thêm yêu cầu kết bạn mới với trạng thái 'pending'
            try (PreparedStatement insertStatement = connection.prepareStatement(insertRequestQuery)) {
                insertStatement.setString(1, request.getRequesterUserID());
                insertStatement.setString(2, request.getRequestedUserID());
                insertStatement.setString(3, request.getStatus()); // Trạng thái 'pending'
                insertStatement.setDate(4, new java.sql.Date(request.getCreatedAt().getTime()));
                int rowsAffected = insertStatement.executeUpdate();
                return rowsAffected > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
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


    // Lấy danh sách yêu cầu kết bạn của người dùng (dựa trên status)
    public List<FriendRequestDTO> getFriendRequestsByUserID(String userID, String status) {
        List<FriendRequestDTO> requests = new ArrayList<>();
        String query = "SELECT fr.requester_userID, fr.requested_userID, fr.status, fr.createdAt, u.fullName, u.userName " +
                     "FROM Friend_requests fr " +
                     "JOIN User u ON fr.requester_userID = u.userID " +
                     "WHERE fr.requested_userID = ? AND fr.status = ?";
        
        DBconnector dbConnector = new DBconnector();  
        Connection connection = dbConnector.getConnection();  
        if (connection == null) {
            return requests;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userID);
            preparedStatement.setString(2, status);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                FriendRequestDTO request = new FriendRequestDTO(
                        rs.getString("requester_userID"),
                        rs.getString("requested_userID"),
                        rs.getString("status"),
                        rs.getDate("createdAt"),
                        rs.getString("fullName"),
                        rs.getString("userName")
                );
                requests.add(request);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();  // Đảm bảo đóng kết nối sau khi sử dụng
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return requests;
    }

    // Cập nhật trạng thái yêu cầu kết bạn
    public boolean updateFriendRequestStatus(String requesterUserID, String requestedUserID, String status) {
        String query = "UPDATE Friend_requests SET status = ? WHERE requester_userID = ? AND requested_userID = ?";
        
        DBconnector dbConnector = new DBconnector();  
        Connection connection = dbConnector.getConnection();  
        if (connection == null) {
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, status);
            preparedStatement.setString(2, requesterUserID);
            preparedStatement.setString(3, requestedUserID);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
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