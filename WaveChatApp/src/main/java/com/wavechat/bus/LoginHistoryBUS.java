/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.wavechat.bus;

import com.wavechat.dao.LoginHistoryDAO;
import com.wavechat.dto.LoginHistoryDTO;
import java.util.List;

/**
 *
 * @author buihi
 */
public class LoginHistoryBUS {
    private LoginHistoryDAO loginHistoryDAO;

    // Constructor
    public LoginHistoryBUS() {
        this.loginHistoryDAO = new LoginHistoryDAO();
    }

    // Method to get login history by userID
    public List<LoginHistoryDTO> getLoginHistoryByUserID(String userID) {
        return loginHistoryDAO.getLoginHistoryByUserID(userID);
    }
    
    public boolean addLoginHistory(String userID){
        return loginHistoryDAO.addLoginHistory(userID);
    }
    
    public List<LoginHistoryDTO> getAllLoginHistory() {
        return loginHistoryDAO.getAllLoginHistory();
    }
}
