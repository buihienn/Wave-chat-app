package com.wavechat.bus;

import com.wavechat.dao.ChatMessageDAO;
import com.wavechat.dto.ChatMessageDTO;

import java.util.List;

public class ChatMessageBUS {

    private ChatMessageDAO chatDAO;

    public ChatMessageBUS() {
        chatDAO = new ChatMessageDAO();
    }

    // Lấy tin nhắn giữa hai người dùng
    public List<ChatMessageDTO> getMessagesBetweenUsers(String userID, String friendID) {
        return chatDAO.getMessagesBetweenUsers(userID, friendID);
    }

    // Lấy tin nhắn trong nhóm chat
    public List<ChatMessageDTO> getMessagesInGroup(int groupID) {
        return chatDAO.getMessagesInGroup(groupID);
    }
}
