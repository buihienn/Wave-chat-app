package com.wavechat.bus;

import com.wavechat.GlobalVariable;
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
    
    public ConversationDTO checkConversationExists(String friendID) {
        String userID = GlobalVariable.getUserID();
        return conversationDAO.getOneConversationByID(userID, friendID);
    }

    public ConversationDTO addConversation(String friendID) {
        String userID = GlobalVariable.getUserID();
        return conversationDAO.addConversation(userID, friendID);
    }
}