package com.wavechat.dao;

import com.wavechat.dto.ConversationDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConversationDAO {
    
    // Lấy danh sách cuộc trò chuyện của người dùng
    public List<ConversationDTO> getConversationsByUser(String userID) {
        List<ConversationDTO> conversations = new ArrayList<>();
        String query = "SELECT conversationID, userID1, userID2, groupID, lastMessageTime, conversationType " +
                       "FROM Conversations " +
                       "WHERE userID1 = ?" +
                       "ORDER BY lastMessageTime DESC";

        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();

        if (connection == null) {
            System.out.println("Failed to establish a database connection.");
            return conversations; // Trả về false nếu không thể kết nối
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String conversationID = resultSet.getString("conversationID");
                String userID1 = resultSet.getString("userID1");
                String userID2 = resultSet.getString("userID2");
                String groupID = resultSet.getString("groupID");
                Timestamp lastMessageTime = resultSet.getTimestamp("lastMessageTime");
                String conversationType = resultSet.getString("conversationType");

                ConversationDTO conversation = new ConversationDTO(conversationID, userID1, userID2, groupID, lastMessageTime, conversationType);
                conversations.add(conversation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close(); // Đóng kết nối
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conversations;
    }
}