package com.wavechat.bus;

import com.wavechat.dao.BlockDAO;

public class BlockBUS {
    private BlockDAO blockDAO;

    public BlockBUS() {
        blockDAO = new BlockDAO();
    }

    // Xử lý Block
    public boolean blockUser(String userID1, String userID2) {
        return blockDAO.blockUser(userID1, userID2);
    }

    // Kiểm tra xem người dùng đã bị block chưa
    public boolean isBlocked(String userID1, String userID2) {
        return blockDAO.isBlocked(userID1, userID2);
    }
}

