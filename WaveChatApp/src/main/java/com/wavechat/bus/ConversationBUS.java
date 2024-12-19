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
    
    public ConversationDTO checkConversationExists(String ownerID, String friendID) {
        return conversationDAO.getOneConversationByID(ownerID, friendID);
    }

    public ConversationDTO addFriendConversation(String ownerID, String friendID) {
        return conversationDAO.addFriendConversation(ownerID, friendID);
    }
    
    public ConversationDTO addGroupConversation(String ownerID, int groupID) {
        return conversationDAO.addGroupConversation(ownerID, groupID);
    }
    
    public ConversationDTO addStrangerConversation(String ownerID, String strangerID) {
        return conversationDAO.addStrangerConversation(ownerID, strangerID);
    }
    
    // Hàm lấy cuộc trò chuyện nhóm theo groupID
    public ConversationDTO getConversationGroupByID(int groupID) {
        return conversationDAO.getConversationGroupByID(groupID);  // Gọi phương thức DAO để lấy thông tin
    }
    
    public ConversationDTO checkConversationGroupExists(String ownerID, int groupID) {
        return conversationDAO.getOneConversationGroupByID(ownerID, groupID);
    }
}