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

    public ConversationDTO addFriendConversation(String friendID) {
        String userID = GlobalVariable.getUserID();
        return conversationDAO.addFriendConversation(userID, friendID);
    }
    
    public ConversationDTO addGroupConversation(int groupID) {
        String userID = GlobalVariable.getUserID();
        return conversationDAO.addGroupConversation(userID, groupID);
    }
    
    public ConversationDTO addStrangerConversation(String strangerID) {
        String userID = GlobalVariable.getUserID();
        return conversationDAO.addStrangerConversation(userID, strangerID);
    }
    
    // Hàm lấy cuộc trò chuyện nhóm theo groupID
    public ConversationDTO getConversationGroupByID(int groupID) {
        return conversationDAO.getConversationGroupByID(groupID);  // Gọi phương thức DAO để lấy thông tin
    }
}