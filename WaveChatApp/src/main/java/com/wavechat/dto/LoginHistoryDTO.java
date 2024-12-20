/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.wavechat.dto;

import java.time.LocalDateTime;

/**
 *
 * @author buihi
 */
public class LoginHistoryDTO {
    private int id;
    private String userID;
    private LocalDateTime loginTime;

    public LoginHistoryDTO(int id, String userID, LocalDateTime loginTime) {
        this.id = id;
        this.userID = userID;
        this.loginTime = loginTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }
}
