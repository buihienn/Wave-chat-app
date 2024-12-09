package com.wavechat.bus;

import com.wavechat.dao.*;
import com.wavechat.dto.*;

public class userBUS {
    private userDAO userDAO;

    public userBUS() {
        this.userDAO = new userDAO();
    }

    // Phương thức cập nhật hồ sơ người dùng
    public boolean editUser(userDTO user) {
        return userDAO.updateUser(user); // Gọi phương thức từ DAO
    }
    
    public userDTO getUserByID(String userID) throws Exception {
        return userDAO.getUserByID(userID); // Gọi phương thức từ DAO
    }

}
