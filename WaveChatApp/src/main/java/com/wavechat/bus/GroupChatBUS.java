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
    
    // Hàm lấy group chat bằng ID
    public GroupChatDTO getGroupChatByID(int groupID) {
        return groupChatDAO.getGroupChatByID(groupID);  // Gọi phương thức trong DAO để truy vấn
    }
    
    // Hàm tạo Group Chat mới
    public GroupChatDTO createGroupChat(String groupName) {
        return groupChatDAO.createGroupChat(groupName);  
    }

    // Hàm thêm thành viên vào group chat
    public void addMember(String memberID, int groupID, boolean isAdmin) {
        groupChatDAO.addMember(memberID, groupID, isAdmin);
    }
}