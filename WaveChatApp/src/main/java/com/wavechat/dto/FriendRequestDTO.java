package com.wavechat.dto;

import java.util.Date;

public class FriendRequestDTO {
    private String requesterUserID;   // Người gửi yêu cầu kết bạn
    private String requestedUserID;   // Người nhận yêu cầu kết bạn
    private String status;            // Trạng thái yêu cầu (pending, accepted, rejected)
    private Date createdAt;           // Thời gian gửi yêu cầu
    private String fullName;          // Tên đầy đủ của người gửi yêu cầu
    private String userName;          // Tên đăng nhập của người gửi yêu cầu

    // Constructor
    public FriendRequestDTO(String requesterUserID, String requestedUserID, String status, Date createdAt, String fullName, String userName) {
        this.requesterUserID = requesterUserID;
        this.requestedUserID = requestedUserID;
        this.status = status;
        this.createdAt = createdAt;
        this.fullName = fullName;
        this.userName = userName;
    }

    // Getters and Setters
    public String getRequesterUserID() {
        return requesterUserID;
    }

    public void setRequesterUserID(String requesterUserID) {
        this.requesterUserID = requesterUserID;
    }

    public String getRequestedUserID() {
        return requestedUserID;
    }

    public void setRequestedUserID(String requestedUserID) {
        this.requestedUserID = requestedUserID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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
}
