package com.wavechat.dto;

import java.sql.Timestamp;
import java.util.*;

public class ChatMessageDTO {
    private int chatID;
    private String senderID;
    private String receiverID;
    private String message;
    private Timestamp timeSend;
    private boolean isRead;

    public ChatMessageDTO(int chatID, String senderID, String receiverID, String message, Timestamp timeSend, boolean isRead) {
        this.chatID = chatID;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.message = message;
        this.timeSend = timeSend;
        this.isRead = isRead;
    }

    public int getChatID() {
        return chatID;
    }

    public void setChatID(int chatID) {
        this.chatID = chatID;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTimeSend() {
        return timeSend;
    }

    public void setTimeSend(Timestamp timeSend) {
        this.timeSend = timeSend;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
    
}