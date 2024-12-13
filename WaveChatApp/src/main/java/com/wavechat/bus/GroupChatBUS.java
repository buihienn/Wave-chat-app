package com.wavechat.bus;

import com.wavechat.dao.GroupChatDAO;
import com.wavechat.dto.GroupChatDTO;
import java.util.List;

public class GroupChatBUS {
    private GroupChatDAO groupChatDAO;

    public GroupChatBUS() {
        this.groupChatDAO = new GroupChatDAO();
    }
    
    // Hàm lấy list group chat
    public List<GroupChatDTO> getGroupChats(String userID) {
        return groupChatDAO.getGroupChats(userID);
    }
}