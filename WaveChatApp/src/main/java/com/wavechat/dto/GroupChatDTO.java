package com.wavechat.dto;

public class GroupChatDTO {
    private int groupID;
    private String groupName;
    private String createdBy;
    private boolean onlineStatus;

    public GroupChatDTO(int groupID, String groupName, String createdBy, Boolean onlineStatus) {
        this.groupID = groupID;
        this.groupName = groupName;
        this.createdBy = createdBy;
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
}
