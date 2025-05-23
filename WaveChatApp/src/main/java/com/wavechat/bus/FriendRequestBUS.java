package com.wavechat.bus;

import com.wavechat.GlobalVariable;
import com.wavechat.dao.FriendDAO;
import com.wavechat.dao.FriendRequestDAO;
import com.wavechat.dto.FriendRequestDTO;
import java.util.List;

public class FriendRequestBUS {
    private FriendRequestDAO friendRequestDAO;

    public FriendRequestBUS() {
        this.friendRequestDAO = new FriendRequestDAO();
    }

    // Thêm yêu cầu kết bạn
    public boolean sendFriendRequest(String requestedID) {
        String requesterID = GlobalVariable.getUserID();
        FriendRequestDTO request = new FriendRequestDTO(requesterID, requestedID, "pending", new java.util.Date(), "", "");
        return friendRequestDAO.addFriendRequest(request);
    }
    
    public boolean removeFriendRequest(String friendID) {
        String userID = GlobalVariable.getUserID();  
        return friendRequestDAO.removeFriendRequest(userID, friendID);
    }
    
    // HÀm kiểm tra đã yêu cầu kết bạn chưa
    public boolean isFriendRequestSentTo(String friendID) {
        String requesterID = GlobalVariable.getUserID(); 
        return friendRequestDAO.isFriendRequestSent(requesterID, friendID);
    }

    // Lấy danh sách yêu cầu kết bạn cho người dùng
    public List<FriendRequestDTO> getPendingRequests(String userID) {
        return friendRequestDAO.getFriendRequestsByUserID(userID, "pending");
    }

    // Chấp nhận yêu cầu kết bạn
    public boolean acceptRequest(String userID, String requesterUserID) {
        boolean isRequestUpdated = friendRequestDAO.updateFriendRequestStatus(requesterUserID, userID, "accepted");

        if (isRequestUpdated) {
            boolean isFriendAdded = FriendDAO.addFriend(userID, requesterUserID);

            return isFriendAdded; // Trả về true nếu cả 2 thao tác đều thành công
        }

        return false; // Trả về false nếu việc cập nhật yêu cầu thất bại
    }


    // Xóa yêu cầu kết bạn
    public boolean deleteRequest(String userID, String requesterUserID) {
        return friendRequestDAO.updateFriendRequestStatus(requesterUserID, userID, "rejected");
    }
}
