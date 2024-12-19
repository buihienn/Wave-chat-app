package com.wavechat.bus;

import com.wavechat.dao.ChatMessageDAO;
import com.wavechat.dto.ChatMessageDTO;
import com.wavechat.dto.UserDTO;
import java.util.*;

public class ChatMessageBUS {

    private ChatMessageDAO chatDAO;

    public ChatMessageBUS() {
        chatDAO = new ChatMessageDAO();
    }

    // Hàm lấy tin nhắn giữa hai người dùng
    public List<ChatMessageDTO> getMessagesBetweenUsers(String conversationID, int offset, int limit) {
        return chatDAO.getMessagesByConversation(conversationID, offset, limit);
    }

    // Hàm lấy tin nhắn trong nhóm chat
    public List<ChatMessageDTO> getMessagesInGroup(String conversationID, int offset, int limit) {
        return chatDAO.getMessagesInGroup(conversationID, offset, limit);
    }
    

    // Hàm lấy full name người gửi từ ID
    public String getFullnameSender(String senderID) {
        UserBUS userBUS = new UserBUS();
        try {
            UserDTO sender = userBUS.getUserByID(senderID);
            return sender.getFullName();
        } catch (Exception e) {
            System.out.println("Error getting userID: " + e.getMessage());
        }
        return null;
    }
    
    // Hàm thêm tin nhắn
    public boolean addMessage(String senderID, String receiverID, String messageText, String conservationID) {
        return chatDAO.addMessage(senderID, receiverID, messageText, conservationID);
    }
    
    // Hàm thêm tin nhắn group
    public boolean addMessageGroup(String senderID, int groupID, String messageText, String conservationID) {
        ChatMessageDAO messageDAO = new ChatMessageDAO();
        return messageDAO.addMessageGroup(senderID, groupID, messageText, conservationID);
    }


}
