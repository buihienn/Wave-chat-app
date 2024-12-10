package com.wavechat.dao;

import java.sql.*;
import com.wavechat.dto.UserDTO;

public class UserDAO {
    
    // Hàm update thông tin user
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

    // Hàm lấy thông tin user
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
    
    // Hàm tạo userID
    public String generateUserID() {
    String query = "SELECT MAX(CAST(SUBSTRING(userID, 2, LENGTH(userID)-1) AS UNSIGNED)) FROM User";

    // Tạo đối tượng DBconnector và kết nối
    DBconnector dbConnector = new DBconnector();
    Connection connection = dbConnector.getConnection();
    if (connection == null) {
        return null;
    }

    try (Statement statement = connection.createStatement()) {
        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            int maxID = resultSet.getInt(1);
            // Tạo userID mới dựa trên giá trị maxID
            maxID++;
            // Đảm bảo định dạng userID là Uxxx (3 chữ số)
            return "U" + String.format("%03d", maxID); 
        }
    } catch (SQLException e) {
        System.out.println("Error generating userID: " + e.getMessage());
    } finally {
        try {
            if (connection != null) {
                connection.close();  // Đảm bảo đóng kết nối sau khi sử dụng
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return null;  // Trả về null nếu không lấy được giá trị
}

    // Kiểm tra xem username có tồn tại trong cơ sở dữ liệu không
    public boolean checkUserNameExist(String userName) {
        String query = "SELECT COUNT(*) FROM User WHERE userName = ?";
        
        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();
        if (connection == null) {
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;  // Nếu số lượng trả về lớn hơn 0, tức là tên người dùng đã tồn tại
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Kiểm tra xem email có tồn tại trong cơ sở dữ liệu không
    public boolean checkEmailExist(String email) {
        String query = "SELECT COUNT(*) FROM User WHERE email = ?";
        
        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();
        if (connection == null) {
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;  // Nếu số lượng trả về lớn hơn 0, tức là email đã tồn tại
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Hàm add user mới
    public boolean addNewUser(String userName, String email, String password) {
        String query = "INSERT INTO User (userID, userName, passWord, fullName, address, birthDay, gender, email, createdDate, status, onlineStatus, totalFriend) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();
        if (connection == null) {
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Gán giá trị cho các tham số
            preparedStatement.setString(1, generateUserID());
            preparedStatement.setString(2, userName);
            preparedStatement.setString(3, password); 
            preparedStatement.setString(4, "");
            preparedStatement.setString(5, "");
            preparedStatement.setDate(6, new java.sql.Date(new java.util.Date().getTime()));
            preparedStatement.setString(7, ""); 
            preparedStatement.setString(8, email); 
            preparedStatement.setDate(9, new java.sql.Date(new java.util.Date().getTime())); 
            preparedStatement.setBoolean(10, true); 
            preparedStatement.setBoolean(11, false); 
            preparedStatement.setInt(12, 0); 

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error adding new user: " + e.getMessage());
            return false;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}