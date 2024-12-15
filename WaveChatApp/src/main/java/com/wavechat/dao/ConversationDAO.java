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

    public ConversationDTO getOneConversationByID(String userID1, String userID2) {
        String query = "SELECT * FROM Conversations WHERE userID1 = ? AND userID2 = ?";
        
        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();

        if (connection == null) {
            System.out.println("Failed to establish a database connection.");
            return null; 
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, userID1);
            preparedStatement.setString(2, userID2);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                // Trả về ConversationDTO từ kết quả query
                String conversationID = resultSet.getString("conversationID");
                String user1 = resultSet.getString("userID1");
                String user2 = resultSet.getString("userID2");
                String groupID = resultSet.getString("groupID");
                Timestamp lastMessageTime = resultSet.getTimestamp("lastMessageTime");
                String conversationType = resultSet.getString("conversationType");

                return new ConversationDTO(conversationID, user1, user2, groupID, lastMessageTime, conversationType);
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
        return null;
    }
    
    // Hàm generate conversationID
    public static String generateNewConversationID() {
        String newConversationID = null;

        String query = "SELECT MAX(CAST(conversationID AS UNSIGNED)) FROM Conversations";

        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();

        if (connection == null) {
            System.out.println("Failed to establish a database connection.");
            return null; 
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int maxConversationID = resultSet.getInt(1);

                // Nếu không có dữ liệu (rỗng), bắt đầu từ "1"
                if (maxConversationID == 0) {
                    newConversationID = "1";
                } else {
                    newConversationID = String.valueOf(maxConversationID + 1); // Tạo ConversationID tiếp theo
                }
            } else {
                newConversationID = "1"; // Nếu bảng rỗng, bắt đầu từ 1
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

        return newConversationID;
    }

    // Hàm add 1 conversation mới
    public ConversationDTO addConversation(String userID1, String userID2) {
        String conversationID = generateNewConversationID();  
        String conversationType = "friend"; 

        String query = "INSERT INTO Conversations (conversationID, userID1, userID2, conversationType, lastMessageTime, status) VALUES (?, ?, ?, ?, ?, ?)";
        
        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();

        if (connection == null) {
            System.out.println("Failed to establish a database connection.");
            return null; 
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, conversationID);
            preparedStatement.setString(2, userID1);
            preparedStatement.setString(3, userID2);
            preparedStatement.setString(4, conversationType);
            preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setBoolean(6, true);

            preparedStatement.executeUpdate();

            // Trả về đối tượng ConversationDTO
            return new ConversationDTO(conversationID, userID1, userID2, null, new Timestamp(System.currentTimeMillis()), conversationType);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close(); // Đóng kết nối
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}