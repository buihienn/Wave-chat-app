package com.wavechat.dto;

import java.util.Date;

public class GroupChatDTO {
    private int groupID;
    private String groupName;
    private String createdBy;
    private Date createdAt;
    private boolean onlineStatus;

    public GroupChatDTO(int groupID, String groupName, String createdBy, Boolean onlineStatus) {
        this.groupID = groupID;
        this.groupName = groupName;
        this.createdBy = createdBy;
        this.onlineStatus = onlineStatus;
    }
    
    public GroupChatDTO(int groupID, String groupName, String createdBy, Date createdAt, Boolean onlineStatus) {
        this.groupID = groupID;
        this.groupName = groupName;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.onlineStatus = onlineStatus;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(boolean onlineStatus) {
        this.onlineStatus = onlineStatus;
    }
    
    public Date getCreatedDate() { return createdAt; }
    public void setCreatedDate(Date createdAt) { this.createdAt = createdAt; }
}
