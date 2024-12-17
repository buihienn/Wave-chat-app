package com.wavechat.dao;

import com.wavechat.dto.FriendRequestDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FriendRequestDAO {
    // Hàm thêm yêu cầu kết bạn
    public boolean addFriendRequest(FriendRequestDTO request) {
        // Câu lệnh kiểm tra nếu có yêu cầu kết bạn đã bị từ chối
        String checkRejectedQuery = "SELECT * FROM Friend_requests WHERE requester_userID = ? AND requested_userID = ? AND status = 'rejected'";
        // Câu lệnh xóa yêu cầu kết bạn đã bị từ chối
        String deleteRejectedQuery = "DELETE FROM Friend_requests WHERE requester_userID = ? AND requested_userID = ? AND status = 'rejected'";
        // Câu lệnh thêm yêu cầu kết bạn mới
        String insertRequestQuery = "INSERT INTO Friend_requests (requester_userID, requested_userID, status, createdAt) VALUES (?, ?, ?, NOW())";

        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();
        if (connection == null) {
            return false;  // Nếu không kết nối được DB thì trả về false
        }

        try {
            // Kiểm tra xem có yêu cầu kết bạn nào đã bị từ chối không
            try (PreparedStatement checkStatement = connection.prepareStatement(checkRejectedQuery)) {
                checkStatement.setString(1, request.getRequesterUserID());
                checkStatement.setString(2, request.getRequestedUserID());
                ResultSet resultSet = checkStatement.executeQuery();

                // Nếu có yêu cầu kết bạn bị rejected, xóa nó
                if (resultSet.next()) {
                    try (PreparedStatement deleteStatement = connection.prepareStatement(deleteRejectedQuery)) {
                        deleteStatement.setString(1, request.getRequesterUserID());
                        deleteStatement.setString(2, request.getRequestedUserID());
                        deleteStatement.executeUpdate();  // Xóa yêu cầu đã bị từ chối
                    }
                }
            }

            // Thêm yêu cầu kết bạn mới vào cơ sở dữ liệu
            try (PreparedStatement insertStatement = connection.prepareStatement(insertRequestQuery)) {
                insertStatement.setString(1, request.getRequesterUserID());
                insertStatement.setString(2, request.getRequestedUserID());
                insertStatement.setString(3, "pending");  // Trạng thái ban đầu là "pending"
                insertStatement.executeUpdate();  // Thực thi câu lệnh thêm yêu cầu

                return true;  // Trả về true nếu thêm thành công
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Nếu có lỗi xảy ra trong quá trình thực thi câu lệnh SQL, trả về false
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
    
    // Hàm xóa yêu cầu kết bạn
    public boolean removeFriendRequest(String requesterUserID, String requestedUserID) {
        String deleteRequestQuery = "DELETE FROM Friend_requests WHERE requester_userID = ? AND requested_userID = ? AND status = 'pending'";

        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();

        if (connection == null) {
            System.out.println("Failed to establish a database connection.");
            return false;  // Không thể kết nối DB
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteRequestQuery)) {
            preparedStatement.setString(1, requesterUserID);
            preparedStatement.setString(2, requestedUserID);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;  
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

        return false; 
    }

    // Hàm kiểm tra yêu cầu kết bạn đã tồn tại chưa
    public boolean isFriendRequestSent(String requesterUserID, String requestedUserID) {
        String checkRequestQuery = "SELECT COUNT(*) FROM Friend_requests WHERE requester_userID = ? AND requested_userID = ? AND status = 'pending'";

        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();
        if (connection == null) {
            return false; // Không thể kết nối với DB
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(checkRequestQuery)) {
            preparedStatement.setString(1, requesterUserID); // userID gửi yêu cầu
            preparedStatement.setString(2, requestedUserID); // userID nhận yêu cầu

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
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

        return false; // Không có yêu cầu kết bạn đang chờ
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