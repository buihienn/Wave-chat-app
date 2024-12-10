package com.wavechat.bus;

import com.wavechat.dao.UserDAO;
import com.wavechat.dto.UserDTO;
import org.mindrot.jbcrypt.BCrypt;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserBUS {
    private UserDAO userDAO;

    public UserBUS() {
        this.userDAO = new UserDAO();
    }

    // Hàm cập nhật thông tin người dùng
    public boolean editUser(UserDTO user) {
        return userDAO.updateUser(user); // Gọi phương thức từ DAO
    }
    
    // Hàm lấy thông tin người dùng
    public UserDTO getUserByID(String userID) throws Exception {
        return userDAO.getUserByID(userID); // Gọi phương thức từ DAO
    }
    
    // Hash mật khẩu bằng BCrypt
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));  // Sử dụng cost factor là 12
    }

    // Kiểm tra tên người dùng có hợp lệ không
    public boolean isUserNameExist(String userName) {
        // Kiểm tra trùng lặp tên người dùng trong cơ sở dữ liệu
        UserDAO userDAO = new UserDAO();
        return userDAO.checkUserNameExist(userName);  // Phương thức kiểm tra tồn tại của tên người dùng
    }

    // Kiểm tra email có hợp lệ không
    public boolean isEmailValid(String email) {
        // Kiểm tra email hợp lệ bằng regex
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches(); // Trả về true nếu email hợp lệ, ngược lại false
    }
    
    // Kiểm tra email có tồn tại trong cơ sở dữ liệu không
    public boolean isEmailExist(String email) {
        UserDAO userDAO = new UserDAO();
        return userDAO.checkEmailExist(email);  // Phương thức kiểm tra tồn tại của email trong cơ sở dữ liệu
    }

    // Kiểm tra mật khẩu có hợp lệ không
    public boolean isPasswordValid(String password) {
        // Kiểm tra mật khẩu có ít nhất 1 chữ cái viết hoa, 1 chữ cái viết thường và 1 chữ số
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";  // Mật khẩu dài ít nhất 8 ký tự
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    // Hàm thêm người dùng mới vào cơ sở dữ liệu
    public boolean addNewUser(String userName, String email, String password) {
        // Tạo userID mới
        UserDAO userDAO = new UserDAO();
        String userID = userDAO.generateUserID();
        if (userID == null) {
            return false; // Nếu không thể tạo userID thì trả về false
        }

        // Mã hóa mật khẩu trước khi thêm
        String hashedPassword = hashPassword(password);
        if (hashedPassword == null) {
            return false; // Nếu không thể hash password thì trả về false
        }

        // Thêm người dùng vào cơ sở dữ liệu
        return userDAO.addNewUser(userName, email, hashedPassword);
    }

}