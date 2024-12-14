package com.wavechat.dao;

import com.wavechat.dto.ChatMessageDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChatMessageDAO {

    // Lấy tin nhắn giữa hai người dùng
    public List<ChatMessageDTO> getMessagesBetweenUsers(String userID1, String userID2, int offset, int limit) {
        List<ChatMessageDTO> messages = new ArrayList<>();
        String query = "SELECT chatID, senderID, receiverID, message, timeSend, isRead " +
                       "FROM Chat " +
                       "WHERE (senderID = ? AND receiverID = ?) OR (senderID = ? AND receiverID = ?) " +
                       "ORDER BY timeSend DESC " +
                       "LIMIT ? OFFSET ?";

        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();
        if (connection == null) {
            return messages;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userID1);
            preparedStatement.setString(2, userID2);
            preparedStatement.setString(3, userID2);
            preparedStatement.setString(4, userID1);
            preparedStatement.setInt(5, limit);  // Số lượng tin nhắn tối đa
            preparedStatement.setInt(6, offset);  // Điểm bắt đầu để lấy tin nhắn

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int chatID = resultSet.getInt("chatID");
                String senderID = resultSet.getString("senderID");
                String receiverID = resultSet.getString("receiverID");
                String message = resultSet.getString("message");
                Timestamp timeSend = resultSet.getTimestamp("timeSend");
                boolean isRead = resultSet.getBoolean("isRead");

                messages.add(new ChatMessageDTO(chatID, senderID, receiverID, message, timeSend, isRead));
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching messages between users: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return messages;
    }

    // Lấy tin nhắn trong nhóm chat 
    public List<ChatMessageDTO> getMessagesInGroup(int groupID, int offset, int limit) {
        List<ChatMessageDTO> messages = new ArrayList<>();
        String query = "SELECT c.chatID, c.senderID, c.message, c.timeSend, c.isRead " +
                       "FROM Chat c " +
                       "WHERE c.groupID = ? " +
                       "ORDER BY c.timeSend DESC " +  // Sắp xếp theo thời gian gửi, mới nhất trước
                       "LIMIT ? OFFSET ?";  // Thêm phần giới hạn và offset

        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();
        if (connection == null) {
            return messages;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, groupID);     
            preparedStatement.setInt(2, limit);        
            preparedStatement.setInt(3, offset);   

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int chatID = resultSet.getInt("chatID");
                String senderID = resultSet.getString("senderID");
                String message = resultSet.getString("message");
                Timestamp timeSend = resultSet.getTimestamp("timeSend");
                boolean isRead = resultSet.getBoolean("isRead");

                messages.add(new ChatMessageDTO(chatID, senderID, null, message, timeSend, isRead));  // receiverID có thể là null đối với group chat
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messages;
    }

    
    // Hàm thêm tin nhắn
    public boolean addMessage(String senderID, String receiverID, String messageText) {
        String query = "INSERT INTO Chat (chatID, senderID, receiverID, message, timeSend, isRead) " +
                       "VALUES (?, ?, ?, ?, ?, ?)";

        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();

        if (connection == null) {
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, generateChatID());  // Sử dụng chatID mới
            preparedStatement.setString(2, senderID);
            preparedStatement.setString(3, receiverID);
            preparedStatement.setString(4, messageText);
            preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setBoolean(6, false);  // mặc định là chưa đọc

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;  // Trả về true nếu thêm thành công
        } catch (SQLException e) {
            System.out.println("Error while adding message: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;  // Trả về false nếu không thành công
    }

    // Hàm thêm tin nhắn group
    public boolean addMessageGroup(String senderID, int groupID, String messageText) {
        String query = "INSERT INTO Chat (chatID, senderID, groupID, message, timeSend, isRead) " +
                       "VALUES (?, ?, ?, ?, ?, ?)";

        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();

        if (connection == null) {
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, generateChatID());  // Sử dụng chatID mới
            preparedStatement.setString(2, senderID);
            preparedStatement.setInt(3, groupID);
            preparedStatement.setString(4, messageText);
            preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setBoolean(6, false);  // mặc định là chưa đọc

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;  // Trả về true nếu thêm thành công
        } catch (SQLException e) {
            System.out.println("Error while adding group message: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;  // Trả về false nếu không thành công
    }

    // Hàm generate chat ID
    public int generateChatID() {
        String query = "SELECT MAX(chatID) AS maxChatID FROM Chat";

        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();

        if (connection == null) {
            return -1;  // Trả về -1 nếu có lỗi kết nối hoặc không tìm thấy dữ liệu
        }

        int newChatID = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                newChatID = resultSet.getInt("maxChatID") + 1;  // Lấy chatID cao nhất và cộng thêm 1
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching max chatID: " + e.getMessage());
            return -1;  // Trả về -1 nếu có lỗi
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return newChatID;  // Trả về chatID mới
    }

    
}
