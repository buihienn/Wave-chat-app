package com.wavechat.dao;

import com.wavechat.dto.userDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class userDAO {
    private Connection connection;

    public userDAO() {
        this.connection = DBconnector.getConnection();
        if (this.connection == null) {
            throw new IllegalStateException("Database connection is null. Cannot initialize userDAO.");
        }
    }


    // Phương thức cập nhật thông tin người dùng
    public boolean updateUser(userDTO user) {
        // Chỉ cập nhật các trường: fullName, address, birthDay, gender dựa trên userID
        String query = "UPDATE User SET fullName = ?, address = ?, birthDay = ?, gender = ? WHERE userID = ?";
        try {
            // Kết nối cơ sở dữ liệu và thực hiện truy vấn
            PreparedStatement ps = connection.prepareStatement(query);

            // Gán giá trị cho các tham số
            ps.setString(1, user.getFullName()); // fullName
            ps.setString(2, user.getAddress()); // address
            ps.setDate(3, new java.sql.Date(user.getBirthDay().getTime())); // birthDay
            ps.setString(4, user.getGender()); // gender
            ps.setString(5, user.getUserID()); // userID (điều kiện WHERE)

            // Thực thi truy vấn
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating user: " + e.getMessage());
            return false;
        }
    }
    
    public userDTO getUserByID(String userID) throws Exception {
        String query = "SELECT fullName, address, birthDay, gender, userName, email FROM User WHERE userID = ?";

        try {
            // Kết nối cơ sở dữ liệu và thực hiện truy vấn
            PreparedStatement ps = connection.prepareStatement(query);

            // Gán giá trị cho tham số userID
            ps.setString(1, userID);

            // Thực thi truy vấn
            java.sql.ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Lấy thông tin từ ResultSet
                String fullName = rs.getString("fullName");
                String address = rs.getString("address");
                java.util.Date birthDay = rs.getDate("birthDay");
                String gender = rs.getString("gender");                
                
                String userName = rs.getString("userName");
                String email = rs.getString("email");


                // Trả về đối tượng userDTO
                return new userDTO(userID, userName, fullName, address, birthDay, gender, email);
            }
        } catch (SQLException e) {
            System.out.println("Error getting user infor: " + e.getMessage());
            return null;
        }
        return null; // Trả về null nếu không tìm thấy user
    }
}
