package com.wavechat.dto;

import java.sql.Timestamp;

public class ConversationDTO {
    private String conversationID;
    private String userID1;
    private String userID2;
    private String groupID;
    private Timestamp lastMessageTime;
    private String conversationType;  // 'friend', 'group', 'stranger'

    // Constructor
    public ConversationDTO(String conversationID, String userID1, String userID2, String groupID,
                           Timestamp lastMessageTime, String conversationType) {
        this.conversationID = conversationID;
        this.userID1 = userID1;
        this.userID2 = userID2;
        this.groupID = groupID;
        this.lastMessageTime = lastMessageTime;
        this.conversationType = conversationType;
    }

    // Getters and Setters
    public String getConversationID() {
        return conversationID;
    }

    public void setConversationID(String conversationID) {
        this.conversationID = conversationID;
    }

    public String getUserID1() {
        return userID1;
    }

    public void setUserID1(String userID1) {
        this.userID1 = userID1;
    }

    public String getUserID2() {
        return userID2;
    }

    public void setUserID2(String userID2) {
        this.userID2 = userID2;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public Timestamp getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(Timestamp lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }

    public String getConversationType() {
        return conversationType;
    }

    public void setConversationType(String conversationType) {
        this.conversationType = conversationType;
    }
}