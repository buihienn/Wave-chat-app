package com.wavechat.dto;

public class FriendDTO {
    private String fullName;    
    private String userName;
    private boolean onlineStatus;

    // Constructor
    public FriendDTO(String fullName, String userName, boolean onlineStatus) {
        this.fullName = fullName;        
        this.userName = userName;
        this.onlineStatus = onlineStatus;
    }

    // Getters and Setters
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
}