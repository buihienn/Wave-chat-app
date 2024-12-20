package com.wavechat.dao;

import com.wavechat.dto.ChatMessageDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChatMessageDAO {

    // Lấy tin nhắn giữa hai người dùng
    public List<ChatMessageDTO> getMessagesByConversation(String conversationID, int offset, int limit) {
        List<ChatMessageDTO> messages = new ArrayList<>();
        String query = "SELECT chatID, senderID, receiverID, message, timeSend, isRead " +
                       "FROM Chat " +
                       "WHERE conversationID = ? " +
                       "ORDER BY timeSend DESC " +
                       "LIMIT ? OFFSET ?";

        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();
        if (connection == null) {
            return messages;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, conversationID);
            preparedStatement.setInt(2, limit);           
            preparedStatement.setInt(3, offset);         

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
            System.out.println("Error while fetching messages by conversationID: " + e.getMessage());
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
    public List<ChatMessageDTO> getMessagesInGroup(String conversationID, int offset, int limit) {
        List<ChatMessageDTO> messages = new ArrayList<>();
        String query = "SELECT chatID, senderID, message, timeSend, isRead " +
                       "FROM Chat " +
                       "WHERE conversationID = ? " + 
                       "ORDER BY timeSend DESC " +  
                       "LIMIT ? OFFSET ?";        

        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();
        if (connection == null) {
            return messages; // Nếu không thể kết nối, trả về danh sách rỗng
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, conversationID);
            preparedStatement.setInt(2, limit);           
            preparedStatement.setInt(3, offset);          

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int chatID = resultSet.getInt("chatID");
                String senderID = resultSet.getString("senderID");
                String message = resultSet.getString("message");
                Timestamp timeSend = resultSet.getTimestamp("timeSend");
                boolean isRead = resultSet.getBoolean("isRead");

                // Với group chat, receiverID không cần thiết
                messages.add(new ChatMessageDTO(chatID, senderID, null, message, timeSend, isRead));
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching messages in group: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close(); // Đảm bảo đóng kết nối
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return messages;
    }


    
    // Hàm thêm tin nhắn
    public boolean addMessage(String senderID, String receiverID, String messageText, String conversationID) {
        String query = "INSERT INTO Chat (chatID, senderID, receiverID, message, timeSend, isRead, conversationID) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?)";

        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();

        if (connection == null) {
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, generateChatID());  // Sử dụng chatID mới (có thể tạo một hàm generateChatID())
            preparedStatement.setString(2, senderID);
            preparedStatement.setString(3, receiverID);
            preparedStatement.setString(4, messageText);
            preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));  // Thời gian gửi tin nhắn
            preparedStatement.setBoolean(6, false);  // Mặc định là chưa đọc
            preparedStatement.setString(7, conversationID);  // Thêm conversationID vào bảng Chat

            int rowsAffected = preparedStatement.executeUpdate();  // Thực thi câu lệnh SQL

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // Hàm thêm tin nhắn group
    public boolean addMessageGroup(String senderID, int groupID, String messageText, String conversationID) {
        String query = "INSERT INTO Chat (chatID, senderID, groupID, message, timeSend, isRead, conversationID) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?)";

        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();

        if (connection == null) {
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, generateChatID());  // Sử dụng chatID mới (có thể tạo một hàm generateChatID())
            preparedStatement.setString(2, senderID);
            preparedStatement.setInt(3, groupID);
            preparedStatement.setString(4, messageText);
            preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));  // Thời gian gửi tin nhắn
            preparedStatement.setBoolean(6, false);  // Mặc định là chưa đọc
            preparedStatement.setString(7, conversationID);  // Thêm conversationID vào bảng Chat

            int rowsAffected = preparedStatement.executeUpdate();  // Thực thi câu lệnh SQL

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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

    // Hàm xóa tin nhắn theo conversationID
    public boolean deleteMessagesByConversationID(String conversationID) {
        String query = "DELETE FROM Chat WHERE conversationID = ?";
        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();

        if (connection == null) {
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, conversationID);
            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0; 
        } catch (SQLException e) {
            System.out.println("Error while deleting messages: " + e.getMessage());
            return false;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
