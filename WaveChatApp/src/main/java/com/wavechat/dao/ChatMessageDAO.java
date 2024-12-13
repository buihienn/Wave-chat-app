package com.wavechat.dao;

import com.wavechat.dto.ChatMessageDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChatMessageDAO {

    // Lấy tin nhắn giữa hai người dùng
    public List<ChatMessageDTO> getMessagesBetweenUsers(String userID, String friendID) {
        List<ChatMessageDTO> messages = new ArrayList<>();
        String query = "SELECT c.chatID, c.senderID, c.receiverID, c.message, c.timeSend, c.isRead " +
                       "FROM Chat c " +
                       "WHERE (c.senderID = ? AND c.receiverID = ?) " +
                       "OR (c.senderID = ? AND c.receiverID = ?) " +
                       "ORDER BY c.timeSend";

        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();
        if (connection == null) {
            return messages;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userID);
            preparedStatement.setString(2, friendID);
            preparedStatement.setString(3, friendID);
            preparedStatement.setString(4, userID);

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
            System.out.println("Error while fetching messages: " + e.getMessage());
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
    public List<ChatMessageDTO> getMessagesInGroup(int groupID) {
        List<ChatMessageDTO> messages = new ArrayList<>();
        String query = "SELECT c.chatID, c.senderID, c.message, c.timeSend, c.isRead " +
                       "FROM Chat c " +
                       "WHERE c.groupID = ? " +
                       "ORDER BY c.timeSend";

        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();
        if (connection == null) {
            return messages;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, groupID);

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
            System.out.println("Error while fetching group messages: " + e.getMessage());
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
}
