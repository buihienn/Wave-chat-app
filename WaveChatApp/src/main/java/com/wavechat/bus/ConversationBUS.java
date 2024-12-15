package com.wavechat.bus;

import com.wavechat.dao.ConversationDAO;
import com.wavechat.dto.ConversationDTO;

import java.util.List;

public class ConversationBUS {

    private ConversationDAO conversationDAO;

    public ConversationBUS() {
        conversationDAO = new ConversationDAO();
    }

    public List<ConversationDTO> getConversations(String userID) {
        return conversationDAO.getConversationsByUser(userID);
    }
}