package com.wavechat.dto;
import java.util.Date;

public class UserDTO {
    private String userID;
    private String userName;
    private String password;
    private String fullName;
    private String address;
    private Date birthDay;
    private String gender;
    private String email;
    private Date createdDate;
    private boolean status;
    private boolean onlineStatus;

    // Constructor
    public UserDTO(String userID, String userName, String fullName, String address, Date birthDay, String gender, String email) {
        this.userID = userID;
        this.userName = userName;
        this.fullName = fullName;
        this.address = address;
        this.birthDay = birthDay;
        this.gender = gender;
        this.email = email;
    }
    
    // Constructor chỉ cho các trường cần cập nhật
    public UserDTO(String userID, String fullName, String address, java.util.Date birthDay, String gender) {
        this.userID = userID;
        this.fullName = fullName;
        this.address = address;
        this.birthDay = birthDay;
        this.gender = gender;
    }

    // Getters và Setters
    public String getUserID() { return userID; }
    public void setUserID(String userID) { this.userID = userID; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Date getBirthDay() { return birthDay; }
    public void setBirthDay(Date birthDay) { this.birthDay = birthDay; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Date getCreatedDate() { return createdDate; }
    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }

    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }

    public boolean isOnlineStatus() { return onlineStatus; }
    public void setOnlineStatus(boolean onlineStatus) { this.onlineStatus = onlineStatus; }
}
