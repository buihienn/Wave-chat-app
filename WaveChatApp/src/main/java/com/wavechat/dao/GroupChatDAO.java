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
}