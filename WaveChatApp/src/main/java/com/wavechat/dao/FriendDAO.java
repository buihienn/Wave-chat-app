package com.wavechat.dao;

import com.wavechat.dto.FriendAdminUserDTO;
import com.wavechat.dto.FriendDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FriendDAO {
    
    // Lấy danh sách bạn bè gồm fullName, userName và status của userID
    public List<FriendDTO> getFriendsByUserID(String userID) {
        List<FriendDTO> friendsList = new ArrayList<>();

        // Câu truy vấn đã sửa để lấy thêm userID
        String query = "SELECT u.userID, u.userName, u.onlineStatus, u.fullName " +
                       "FROM User u " +
                       "JOIN Friends f ON (f.userID1 = u.userID OR f.userID2 = u.userID) " +
                       "WHERE (f.userID1 = ? OR f.userID2 = ?) " +
                       "AND u.userID != ?";

        // Tạo đối tượng DBconnector và kết nối
        DBconnector dbConnector = new DBconnector();  
        Connection connection = dbConnector.getConnection();  // Gọi phương thức getConnection()
        if (connection == null) {
            return friendsList;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userID);
            preparedStatement.setString(2, userID);
            preparedStatement.setString(3, userID);

            ResultSet resultSet = preparedStatement.executeQuery();

            // Đọc dữ liệu từ ResultSet và thêm vào list
            while (resultSet.next()) {
                String userIDFromDb = resultSet.getString("userID");  // Lấy userID
                String userName = resultSet.getString("userName");
                String fullName = resultSet.getString("fullName");  
                boolean onlineStatus = resultSet.getBoolean("onlineStatus");

                // Thêm bạn bè vào list, bao gồm cả userID
                friendsList.add(new FriendDTO(fullName, userName, onlineStatus, userIDFromDb));
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
    
    public static boolean addFriend(String userID, String requesterUserID) {
        String query = "INSERT INTO Friends (userID1, userID2, createdAt) VALUES (?, ?, ?)";
        
        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();
        if (connection == null) {
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, requesterUserID);
            preparedStatement.setString(2, userID);
            preparedStatement.setDate(3, new java.sql.Date(System.currentTimeMillis())); // Ngày hiện tại
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Nếu thêm thành công, trả về true
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
    
    public List<FriendAdminUserDTO> getFriendForAdmin() {
        List<FriendAdminUserDTO> list = new ArrayList<>();
        DBconnector dbConnection = new DBconnector();
        Connection conn = dbConnection.getConnection();

        if (conn == null) {
            return list;
        }

        String query = "SELECT u.userID AS U_ID, u.userName, " +
               "COUNT(DISTINCT CASE " +
               "WHEN f.userID1 = u.userID AND u2.onlineStatus = TRUE THEN f.userID2 " +
               "WHEN f.userID2 = u.userID AND u1.onlineStatus = TRUE THEN f.userID1 " +
               "END) AS OnlineFriends, " +
               "COUNT(DISTINCT CASE " +
               "WHEN f.userID1 = u.userID THEN f.userID2 " +
               "WHEN f.userID2 = u.userID THEN f.userID1 " +
               "END) AS totalFriends " +
               "FROM User u " +
               "LEFT JOIN Friends f ON f.userID1 = u.userID OR f.userID2 = u.userID " +
               "LEFT JOIN User u1 ON f.userID1 = u1.userID " +
               "LEFT JOIN User u2 ON f.userID2 = u2.userID " +
               "GROUP BY u.userID, u.userName";

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String userID = rs.getString("U_ID");
                String userName = rs.getString("userName");
                int onlineFriends = rs.getInt("OnlineFriends");
                int totalFriends = rs.getInt("totalFriends"); // Sửa ánh xạ ở đây

                FriendAdminUserDTO friendDTO = new FriendAdminUserDTO(userID, userName, onlineFriends, totalFriends);
                list.add(friendDTO);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FriendDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        return list;
    }
}