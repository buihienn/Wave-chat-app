/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.wavechat.dto;

/**
 *
 * @author buihi
 */
public class FriendAdminUserDTO {
    private String userID;
    private String userName;
    private int onlineFriends;
    private int totalFriends;

    public FriendAdminUserDTO(String userID, String userName, int onlineFriends, int totalFriends) {
        this.userID = userID;
        this.userName = userName;
        this.onlineFriends = onlineFriends;
        this.totalFriends = totalFriends;
    }

    // Getters and Setters
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getOnlineFriends() {
        return onlineFriends;
    }

    public void setOnlineFriends(int onlineFriends) {
        this.onlineFriends = onlineFriends;
    }

    public int getTotalFriends() {
        return totalFriends;
    }

    public void setTotalFriends(int totalFriends) {
        this.totalFriends = totalFriends;
    }
}
