package com.wavechat.dto;

public class FriendDTO {
    private String fullName;
    private String userName;
    private boolean onlineStatus;
    private String userID;  

    public FriendDTO(String fullName, String userName, boolean onlineStatus, String userID) {
        this.fullName = fullName;
        this.userName = userName;
        this.onlineStatus = onlineStatus;
        this.userID = userID;  
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isOnlineStatus() {
        return onlineStatus;
    }
    
    public void setOnlineStatus(boolean onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}