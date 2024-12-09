package com.wavechat.bus;

import com.wavechat.dao.UserDAO;
import com.wavechat.dto.UserDTO;

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

}