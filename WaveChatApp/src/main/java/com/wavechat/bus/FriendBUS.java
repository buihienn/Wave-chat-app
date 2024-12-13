package com.wavechat.bus;

import com.wavechat.dao.FriendDAO;
import com.wavechat.dto.FriendAdminUserDTO;
import com.wavechat.dto.FriendDTO;
import java.util.List;

public class FriendBUS {
    private FriendDAO friendDAO;

    public FriendBUS() {
        this.friendDAO = new FriendDAO();
    }

    // Lấy danh sách bạn bè của userID
    public List<FriendDTO> getFriends(String userID) {
        return friendDAO.getFriendsByUserID(userID);
    }
    
    // Xử lý Unfriend
    public boolean unfriend(String userID1, String userID2) {
        return friendDAO.unfriend(userID1, userID2);
    }
    
    public List<FriendAdminUserDTO> getFriendForAdmin(){
        FriendDAO FriendDAO = new FriendDAO();
        return FriendDAO.getFriendForAdmin();
    }
}