package com.wavechat.dto;

public class BlockDTO {
    private String userID;
    private String blockedUserID;

    // Constructor
    public BlockDTO(String userID, String blockedUserID) {
        this.userID = userID;
        this.blockedUserID = blockedUserID;
    }

    // Getters and Setters
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getBlockedUserID() {
        return blockedUserID;
    }

    public void setBlockedUserID(String blockedUserID) {
        this.blockedUserID = blockedUserID;
    }
}

