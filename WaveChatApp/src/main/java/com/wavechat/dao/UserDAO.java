package com.wavechat.dao;

import java.sql.*;
import com.wavechat.dto.UserDTO;
import com.wavechat.GlobalVariable;
import org.mindrot.jbcrypt.BCrypt;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;

public class UserDAO {
    
    // Hàm getAll()
    public List<UserDTO> getAll(){
        List<UserDTO> list = new ArrayList<>();
        DBconnector dbConnector = new DBconnector();
        Connection conn = dbConnector.getConnection();
        
        if (conn == null){
            return list;
        }
        
        String query = "SELECT * FROM User";
        
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)){
            while(rs.next()){
                String userID = rs.getString("userID");
                String userName = rs.getString("userName");
                String passWord = rs.getString("passWord");
                String fullName = rs.getString("fullName");
                String address = rs.getString("address");
                Date birthDay = rs.getDate("birthDay");
                String gender = rs.getString("gender");
                String email = rs.getString("email");
                Date createdDate = rs.getDate("createdDate");
                boolean status = rs.getBoolean("status");
                boolean onlineStatus = rs.getBoolean("onlineStatus");
                int totalFriend = rs.getInt("totalFriend");
                
               UserDTO user = new UserDTO(userID, userName, passWord, fullName, address, birthDay, gender, email, createdDate, status, onlineStatus, totalFriend);
               list.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.close(); // Đóng kết nối khi hoàn thành
            } catch (SQLException ex) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }
    
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


    // ---------------REGISTER---------------
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

    // Hàm kiểm tra xem username có tồn tại trong db
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

    // Hàm kiểm tra xem email có tồn tại trong db
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
    
    // ---------------LOGIN---------------
    // Kiểm tra login
    public boolean validateUser(String emailOrUsername, String password) {
        String query = "SELECT userID, passWord FROM User WHERE email = ? OR userName = ?"; // Sửa lại tên bảng nếu cần

        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();
        if (connection == null) {
            return false; // Không thể kết nối đến cơ sở dữ liệu
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, emailOrUsername);
            preparedStatement.setString(2, emailOrUsername);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                String storedPasswordHash = rs.getString("passWord"); 
                String userID = rs.getString("userID");

                // Kiểm tra mật khẩu đã nhập
                if (BCrypt.checkpw(password, storedPasswordHash)) { 
                    // Lưu userID vào global variable
                    GlobalVariable.setUserID(userID);

                    // Cập nhật onlineStatus luôn luôn là TRUE
                    String updateQuery = "UPDATE User SET onlineStatus = TRUE WHERE userID = ?";
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                        updateStatement.setString(1, userID);
                        updateStatement.executeUpdate(); // Thực hiện cập nhật onlineStatus
                    }

                    return true; // Đăng nhập thành công
                } else {
                    return false; // Mật khẩu không đúng
                }
            } else {
                System.out.println("Không tìm thấy username hoặc email: " + emailOrUsername);
                return false; // Không tìm thấy user
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Lỗi truy vấn SQL
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
    
    // Hàm kiểm tra và trả về vai trò của người dùng dưới dạng boolean
    public boolean getUserRole(String emailOrUsername) {
        String query = "SELECT isAdmin FROM User WHERE email = ? OR username = ?";

        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();
        if (connection == null) {
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, emailOrUsername);
            preparedStatement.setString(2, emailOrUsername);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return rs.getBoolean("isAdmin"); // Trả về true nếu là admin, false nếu là user
            } else {
                return false; 
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

    // Hàm lấy fullname từ ID
    public String getFullNameByID(String userID) {
        String fullName = null;
        String query = "SELECT fullName FROM User WHERE userID = ?"; // Câu truy vấn SQL
        
        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();
        if (connection == null) {
            return null;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Gán giá trị userID vào câu truy vấn
            preparedStatement.setString(1, userID);
            
            // Thực thi câu truy vấn
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                fullName = resultSet.getString("fullName"); // Lấy giá trị fullName từ ResultSet
            }
        } catch (Exception e) {
            e.printStackTrace(); // In ra lỗi nếu có
        } finally {
            try {
                if (connection != null) {
                    connection.close();  // Đảm bảo đóng kết nối sau khi sử dụng
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return fullName; // Trả về fullName, nếu không tìm thấy trả về null
    }
    
    // ---------------PASSWORD---------------
    // Hàm update password
    public boolean updatePassword(String email, String hashedPassword) {
        String query = "UPDATE User SET passWord = ? WHERE email = ?";
        
        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();
        if (connection == null) {
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, hashedPassword);
            preparedStatement.setString(2, email);

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
    
    // Hàm truy vấn password
    public String getStoredPassword(String userID) {
        String query = "SELECT passWord FROM User WHERE userID = ?";

        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();
        if (connection == null) {
            return null;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userID);  
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("passWord");  // Lấy mật khẩu đã hash từ DB
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
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

    public boolean updatePasswordByID(String userID, String hashedNewPassword) {
        String query = "UPDATE User SET passWord = ? WHERE userID = ?";
        
        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();
        if (connection == null) {
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, hashedNewPassword);
            preparedStatement.setString(2, userID);

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;  // Nếu cập nhật thành công, trả về true
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