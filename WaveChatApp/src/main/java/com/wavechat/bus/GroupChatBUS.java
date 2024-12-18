package com.wavechat.bus;

import com.wavechat.GlobalVariable;
import com.wavechat.dao.GroupChatDAO;
import com.wavechat.dto.GroupChatDTO;
import com.wavechat.dto.UserDTO;
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
    
    // Hàm kiểm tra người dùng có phải là admin
    public boolean isAdmin(int groupID) {
        String userID = GlobalVariable.getUserID();
        return groupChatDAO.isUserAdmin(userID, groupID);
    }
    
    // Hàm đổi tên group
    public boolean changeGroupName(int groupID, String newGroupName) {
        return groupChatDAO.updateGroupName(groupID, newGroupName);
    }
    
    // Hàm lấy danh sách thành viên trong group
    public List<UserDTO> getMemberOfGroup(int groupID) {
        return groupChatDAO.getMembersByGroupID(groupID);
    }
    
    // Hàm kiểm tra một user có là thành viên của group không
    public boolean isMemberOf(int groupID, String userID) {
        return groupChatDAO.isMemberOf(groupID, userID);
    }
    
    // Hàm thay đổi admin của 1 group
    public boolean changeAdmin(String newAdminID, int groupID) {
        return groupChatDAO.changeAdmin(newAdminID, groupID);
    }

    // Hàm xóa thành viên khỏi nhóm
    public boolean deleteMemberFromGroup(String memberID, int groupID) {
        GroupChatDAO groupChatDAO = new GroupChatDAO();
        return groupChatDAO.deleteMemberFromGroup(memberID, groupID);
    }

    
}