package com.wavechat.dto;

public class FriendDTO {
    private String userName;
    private boolean onlineStatus;

    // Constructor
    public FriendDTO(String userName, boolean onlineStatus) {
        this.userName = userName;
        this.onlineStatus = onlineStatus;
    }

    // Getters and Setters
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