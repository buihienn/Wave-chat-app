package com.wavechat.dao;

import com.wavechat.dto.GroupChatDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupChatDAO {

    // Hàm lấy list group chat
    public List<GroupChatDTO> getGroupChats(String userID) {
        List<GroupChatDTO> groupChats = new ArrayList<>();

        String query = "SELECT gc.groupID, gc.groupName, gc.createdBy, gc.onlineStatus " +
                       "FROM GroupChat gc " +
                       "JOIN GroupMembers gm ON gm.groupID = gc.groupID " +
                       "WHERE gm.userID = ?";

        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();

        if (connection == null) {
            return groupChats;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int groupID = resultSet.getInt("groupID");
                String groupName = resultSet.getString("groupName");
                String createdBy = resultSet.getString("createdBy");
                boolean onlineStatus = resultSet.getBoolean("onlineStatus");  

                groupChats.add(new GroupChatDTO(groupID, groupName, createdBy, onlineStatus));
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching group chats: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return groupChats;
    }
    
    // Hàm lấy 1 group chat bằng ID
    public GroupChatDTO getGroupChatByID(int groupID) {
        GroupChatDTO groupChat = null;
        String query = "SELECT groupID, groupName, createdBy, onlineStatus " +
                     "FROM GroupChat " +
                     "WHERE groupID = ?";

        DBconnector dbConnector = new DBconnector();  
        Connection connection = dbConnector.getConnection();  
        if (connection == null) {
            return groupChat;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, groupID);  

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int groupIDFromDB = resultSet.getInt("groupID");
                String groupName = resultSet.getString("groupName");
                String createdBy = resultSet.getString("createdBy");
                boolean onlineStatus = resultSet.getBoolean("onlineStatus");

                groupChat = new GroupChatDTO(groupIDFromDB, groupName, createdBy, onlineStatus);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();  
        } finally {
            try {
                if (connection != null) {
                    connection.close();  // Đảm bảo đóng kết nối sau khi sử dụng
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return groupChat;  
    }
}