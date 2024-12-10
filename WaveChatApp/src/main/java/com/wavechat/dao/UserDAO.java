package com.wavechat.dao;

import java.sql.*;
import com.wavechat.dto.UserDTO;

public class UserDAO {
    
    // Hàm cập nhật thông tin người dùng
    public boolean updateUser(UserDTO user) {
        String query = "UPDATE User SET fullName = ?, address = ?, birthDay = ?, gender = ? WHERE userID = ?";

        // Tạo đối tượng DBconnector và kết nối
        DBconnector dbConnector = new DBconnector();  // Tạo đối tượng DBconnector
        Connection connection = dbConnector.getConnection();  // Gọi phương thức getConnection()
        if (connection == null) {
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Gán giá trị cho các tham số
            preparedStatement.setString(1, user.getFullName()); // fullName
            preparedStatement.setString(2, user.getAddress()); // address
            preparedStatement.setDate(3, new java.sql.Date(user.getBirthDay().getTime())); // birthDay
            preparedStatement.setString(4, user.getGender()); // gender
            preparedStatement.setString(5, user.getUserID()); // userID (điều kiện WHERE)

            // Thực thi truy vấn
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating user: " + e.getMessage());
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

    // Hàm lấy thông tin người dùng
    public UserDTO getUserByID(String userID) throws Exception {
        String query = "SELECT fullName, address, birthDay, gender, userName, email FROM User WHERE userID = ?";
        
        // Tạo đối tượng DBconnector và kết nối
        DBconnector dbConnector = new DBconnector();  // Tạo đối tượng DBconnector
        Connection connection = dbConnector.getConnection();  // Gọi phương thức getConnection()
        if (connection == null) {
            return null;  // Trả về null nếu không có kết nối
        }

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            // Gán giá trị cho tham số userID
            ps.setString(1, userID);

            // Thực thi truy vấn
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Lấy thông tin từ ResultSet
                String fullName = rs.getString("fullName");
                String address = rs.getString("address");
                java.util.Date birthDay = rs.getDate("birthDay");
                String gender = rs.getString("gender");                
                String userName = rs.getString("userName");
                String email = rs.getString("email");

                // Trả về đối tượng userDTO
                return new UserDTO(userID, userName, fullName, address, birthDay, gender, email);
            }
        } catch (SQLException e) {
            System.out.println("Error getting user info: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();  // Đảm bảo đóng kết nối sau khi sử dụng
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null; // Trả về null nếu không tìm thấy user
    }

    // Hàm lấy userID từ username
    public String getUserIDByUsername(String username) {
        String query = "SELECT userID FROM User WHERE userName = ?";

        // Tạo đối tượng DBconnector và kết nối
        DBconnector dbConnector = new DBconnector();  // Tạo đối tượng DBconnector
        Connection connection = dbConnector.getConnection();  // Gọi phương thức getConnection()
        if (connection == null) {
            return null;  // Trả về null nếu không có kết nối
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("userID");
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching userID: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();  // Đảm bảo đóng kết nối sau khi sử dụng
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null; // Trả về null nếu không tìm thấy
    }
}
